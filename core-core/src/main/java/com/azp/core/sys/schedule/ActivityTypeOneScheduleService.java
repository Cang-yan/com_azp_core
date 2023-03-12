package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.azp.core.sys.model.*;
import com.azp.core.sys.service.ActivityTypeOneService;
import com.azp.core.sys.service.ActivityTypeOneUserService;
import com.azp.core.sys.service.PointService;
import com.azp.core.sys.service.UserService;
import com.azp.core.sys.util.ProxyUtils;
import com.azp.data.sys.domain.ActivityThirdSystemTO;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@TaskScheduleComponent
public class ActivityTypeOneScheduleService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ActivityTypeOneScheduleService.class);

    @Autowired
    private ActivityTypeOneService activityTypeOneService;

    @Autowired
    private ActivityTypeOneUserService activityTypeOneUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointService pointService;

    @Task(cron = "0 0/5 * * * ?")
    @Distributed
    public Runnable refresh() {
        return () -> {
            List<User> userList = userService.getListByFilter(new UserFilterMapper());
            for (User user : userList) {
                URL url = null;
                try {
                    Pair<String, String> pair1 = Pair.of("action", "getIntegral");
                    Pair<String, String> pair2 = Pair.of("user_name", URLEncoder.encode(user.getName(), "UTF-8"));
                    Pair<String, String>[] pairs = new Pair[]{pair1, pair2};
                    url = new URL(Url("http://allianz.chinaopenschool.com/Integral.ashx", pairs));
                } catch (Exception e) {
                    LOGGER.error("FAILED to get info from third system");
                    throw new StatusException("THIRD_ERROR");
                }
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                ByteArrayOutputStream baos = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection(ProxyUtils.getProdProxy());
                    urlConnection.setRequestMethod("GET");
                    //设置连接超时时间和读取超时时间
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setReadTimeout(60000);
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = urlConnection.getInputStream();
                        baos = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        String resultString = baos.toString("UTF-8");
                        activityTypeOneSchedule(resultString, user);
                    }

                } catch (Exception e) {
                    LOGGER.error("FAILED to get info from third system");
                    throw new StatusException("THIRD_ERROR");
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (baos != null) {
                        try {
                            baos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
            LOGGER.info("SUCCESS to get info from third system");
        };
    }

    public void activityTypeOneSchedule(String resultString, User user) {
        JSONObject jsonObject = JSON.parseObject(resultString);
        JSONArray list = jsonObject.getJSONArray("list");
        List<ActivityThirdSystemTO> activityOneUserThirdSystemFacadeData = list.toJavaList(ActivityThirdSystemTO.class);
        if (activityOneUserThirdSystemFacadeData.isEmpty()) return;
        // 需要进行操作的活动
        List<ActivityThirdSystemTO> thirdSystemTOListExist = activityOneUserThirdSystemFacadeData
                .stream().filter(activityThirdSystemTO -> activityThirdSystemTO.getIntegralTypeName() != null)
                .filter(activityThirdSystemTO -> user.getUserCode().equals(activityThirdSystemTO.getLoginName()))
                .filter(activityThirdSystemTO -> {
                    if ("在线学习积分".equals(activityThirdSystemTO.getIntegralTypeName())) {
                        return activityThirdSystemTO.getIntegralNum() != null && activityThirdSystemTO.getIntegralNum() > 0;
                    } else if ("考试积分".equals(activityThirdSystemTO.getIntegralTypeName())) {
                        return activityThirdSystemTO.getIntegralNum() != null && activityThirdSystemTO.getIntegralNum() >= 0;
                    } else return false;
                }).collect(Collectors.toList());
        for (ActivityThirdSystemTO activityThirdSystemTO : thirdSystemTOListExist) {
            // 根据活动名称查询活动ID
            ActivityTypeOneFilterMapper activityTypeOneFilterMapper = new ActivityTypeOneFilterMapper();
            activityTypeOneFilterMapper.name = activityThirdSystemTO.getIntegralName();
            activityTypeOneFilterMapper.activitySubCategoryId = activityThirdSystemTO.getIntegralTypeName().equals("在线学习积分") ? "1" : "2";
            Integer pointType = activityThirdSystemTO.getIntegralTypeName().equals("在线学习积分") ? 2 : 3;
            List<ActivityTypeOne> activityTypeOneList = activityTypeOneService.getListByFilter(activityTypeOneFilterMapper);
            if (activityTypeOneList.isEmpty()) continue;
            ActivityTypeOne activityTypeOne = activityTypeOneList.get(0);
            String activityId = activityTypeOne.getId();

            // 查询用户完成情况
            String userId = user.getId();
            ActivityTypeOneUserFilterMapper typeOneUserFilterMapper = new ActivityTypeOneUserFilterMapper();
            typeOneUserFilterMapper.userId = userId;
            typeOneUserFilterMapper.activityTypeOneId = activityId;
            List<ActivityTypeOneUser> activityTypeOneUserList = activityTypeOneUserService.getListByFilter(typeOneUserFilterMapper);
            if (activityTypeOneUserList.isEmpty()) continue;
            ActivityTypeOneUser activityTypeOneUser = activityTypeOneUserList.get(0);
            // 已经计算过积分或者不需要计算积分
            if (activityTypeOneUser.getStatus() != null && (activityTypeOneUser.getStatus() == 1 || activityTypeOneUser.getStatus() == 2 || activityTypeOneUser.getStatus() == 4))
                continue;

            // 比较完成时间
            Point point = new Point();
            point.setUserId(userId);
            point.setGetTime(new Date());
            point.setRelationId(activityTypeOneUser.getId());
            point.setType(pointType);
            int pointChange = 0;
            if (activityTypeOne.getEndDate() == null || activityTypeOne.getEndDate().getTime() >= activityThirdSystemTO.getGetTime().getTime()) {
                // 增加积分
                activityTypeOneUser.setStatus(1);
                pointChange = activityThirdSystemTO.getIntegralNum();
            } else {
                // 扣减积分
                activityTypeOneUser.setStatus(2);
                pointChange = -Math.abs(activityTypeOne.getDePoint());
            }
            activityTypeOneUser.setEndDate(activityThirdSystemTO.getGetTime());
            activityTypeOneUser.setPoint(pointChange);
            activityTypeOneUserService.update(activityTypeOneUser);
            point.setPointNumber(pointChange);
            if (pointChange != 0) {
                pointService.post(point);
            }
        }
    }

    @Task(cron = "0 0/5 * * * ?")
    @Distributed
    public Runnable refreshEnd() {
        return () -> {
            Date date = new Date();
            ActivityTypeOneUserFilterMapper activityTypeOneUserFilterMapper = new ActivityTypeOneUserFilterMapper();
            activityTypeOneUserFilterMapper.statusIn = Collections.singletonList(0);
            List<ActivityTypeOneUser> activityTypeOneUsers = activityTypeOneUserService.getListByFilter(activityTypeOneUserFilterMapper);
            if (CollectionUtils.isEmpty(activityTypeOneUsers)) return;
            for (ActivityTypeOneUser activityTypeOneUser : activityTypeOneUsers) {
                String activityTypeOneId = activityTypeOneUser.getActivityTypeOneId();
                if (activityTypeOneId == null) continue;
                ActivityTypeOne activityTypeOne = activityTypeOneService.getByPK(activityTypeOneId);
                if (activityTypeOne == null) continue;
                if (activityTypeOne.getEndDate() != null && activityTypeOne.getEndDate().before(date)) {
                    Point point = new Point();
                    point.setUserId(activityTypeOneUser.getUserId());
                    point.setGetTime(new Date());
                    point.setRelationId(activityTypeOneUser.getId());
                    point.setType("1".equals(activityTypeOne.getActivitySubCategoryId()) ? 2 : 3);
                    activityTypeOneUser.setStatus(2);
                    activityTypeOneUser.setEndDate(date);
                    activityTypeOneUser.setPoint(-Math.abs(activityTypeOne.getDePoint()));
                    point.setPointNumber(-Math.abs(activityTypeOne.getDePoint()));
                    activityTypeOneUserService.update(activityTypeOneUser);
                    pointService.post(point);
                }
            }
        };
    }

    private String Url(String baseUrl, Pair<String, String>... params) {
        Assert.isTrue(StringUtils.hasLength(baseUrl));
        if (!baseUrl.contains("http://") && !baseUrl.contains("https://")) {
            baseUrl = "http://" + baseUrl;
        }
        StringBuilder sb = new StringBuilder(baseUrl);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                Pair<String, String> param = params[i];
                sb.append(param.getFirst()).append("=").append(param.getSecond());
            }
        }
        return sb.toString();
    }
}
