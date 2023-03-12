package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import com.horsecoder.storage.domain.FileUpdateCoreFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class UserExtendsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UseInfoService infoService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private PointExchangeService pointExchangeService;

    @Autowired
    private UserPointStatisticsService userPointStatisticsService;

    @Autowired
    private UserPointStaticsExtendsService userPointStaticsExtendsService;

    @Resource
    private FileUpdateCoreFacade fileUpdateCoreFacade;

    public Map<String, Object> getUserInfoDetail(String id) {
        UserInfoExtends userInfoExtends = new UserInfoExtends();
        // update user point number
        UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
        useInfoFilterMapper.userId = id;
        UseInfo info = infoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
        Assert.notNull(info, "USER_NOT_COMPLETE");
        this.updateUserPoint(id, userInfoExtends, info);
        Map<String, Object> detailMapByPK = userService.getDetailMapByPK(id);
        if (info != null) {
            Integer pointNumber = info.getPointNumber();
            LevelFilterMapper levelFilterMapper = new LevelFilterMapper();
            levelFilterMapper.page = 0L;
            levelFilterMapper.rows = 0;
            Level level = null;
            List<Level> levelServiceListByFilter = levelService.getListByFilter(levelFilterMapper);
            for (Level level1 : levelServiceListByFilter) {
                if (pointNumber >= level1.getMinPoint() && pointNumber <= level1.getMaxPoint()) {
                    level = level1;
                    break;
                }
            }
            if (pointNumber < 0 || (level == null && !CollectionUtils.isEmpty(levelServiceListByFilter))) {
                level = levelServiceListByFilter.stream().filter(level1 -> level1.getIdx() == 1).findFirst().get();
            }
            userInfoExtends.setLevel(level);
            detailMapByPK.put("userInfoExtends", userInfoExtends);
        }
        return detailMapByPK;
    }

    public void updateUserPoint(String id, UserInfoExtends userInfoExtends, UseInfo useInfo) {
        if (useInfo == null) return;
        PointExchangeFilterMapper pointExchangeFilterMapper = new PointExchangeFilterMapper();
        pointExchangeFilterMapper.userId = id;
        List<PointExchange> pointExchangeList = pointExchangeService.getListByFilter(pointExchangeFilterMapper);
        int pointDecrease = 0;
        if (!CollectionUtils.isEmpty(pointExchangeList)) {
            pointDecrease = pointExchangeList.stream().mapToInt(PointExchange::getPointNum).sum();
        }
        UserPointStatisticsFilterMapper userPointStatisticsFilterMapper = new UserPointStatisticsFilterMapper();
        userPointStatisticsFilterMapper.userId = id;
        UserPointStatistics userPointStatistics = userPointStatisticsService.getListByFilter(userPointStatisticsFilterMapper).stream().findFirst().orElse(null);
        int pointNum = 0;
        int pointCount = 0;
        if (userPointStatistics != null) {
            pointNum = userPointStatistics.getNumber() == null ? 0 : userPointStatistics.getNumber();
            pointCount = userPointStatistics.getCount() == null ? 0 : userPointStatistics.getCount();
            // 获得的全部积分
            userInfoExtends.setTotalPoint(pointNum + pointDecrease);
            // 兑换的积分
            userInfoExtends.setExchangePoint(pointDecrease);
            userInfoExtends.setTotalCount(pointCount);
            userInfoExtends.setPointRank(this.getUserPointRank(id));
            userInfoExtends.setCountRank(this.getUserCountRank(id));
        }
        // 现存的积分
        useInfo.setPointNumber(pointNum);
        infoService.update(useInfo);
    }

    public List<Map<String, Object>> postMappingList(List<UserPostMapper> postMappers) {
        List<Map<String, Object>> entityMapList = new ArrayList<>();
        List<String> errorCodes = new ArrayList<>();
        boolean success = true;
        for (UserPostMapper postMapper : postMappers) {
            UserFilterMapper userFilterMapper = new UserFilterMapper();
            userFilterMapper.userCodeIn = Collections.singletonList(postMapper.userCode);
            List<User> userList = userService.getListByFilter(userFilterMapper);
            if (!CollectionUtils.isEmpty(userList)) {
                success = false;
                errorCodes.add(postMapper.userCode);
            } else if (success) {
                entityMapList.add(userService.postMapping(postMapper));
            }
        }
        if (!CollectionUtils.isEmpty(errorCodes)) {
            throw new StatusException(700, "导入失败，以下用户编号重复：" + errorCodes);
        }
        return entityMapList;
    }

    public String head(MultipartFile file, String userId) {
        UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
        useInfoFilterMapper.userId = userId;
        UseInfo useInfo = infoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
        Assert.notNull(useInfo, "USER_NOT_COMPLETE");
        String fileString = fileUpdateCoreFacade.uploadImageFile(file, "azp", null, null);
        useInfo.setHead(fileString);
        infoService.update(useInfo);
        return fileString;
    }

    private Integer getUserPointRank(String id) {
        UserPointStatisticsFilterMapper userPointStatisticsFilterMapper = new UserPointStatisticsFilterMapper();
        userPointStatisticsFilterMapper.userId = id;
        UserPointStatistics userPointStatistics = userPointStatisticsService.getListByFilter(userPointStatisticsFilterMapper).stream().findFirst().orElse(null);
        if (userPointStatistics == null) return null;
        userPointStatisticsFilterMapper.userId = null;
        userPointStatisticsFilterMapper.numberFrom = userPointStatistics.getNumber();
        userPointStatisticsFilterMapper.orderBy = Collections.singletonList("number desc");
        List<UserPointStatistics> userPointStatisticsList = userPointStatisticsService.getListByFilter(userPointStatisticsFilterMapper);
        List<UserPointStatistics> rankList = userPointStaticsExtendsService.collectElement(userPointStatisticsList);
        int i;
        for (i = 0; i < rankList.size(); i++) {
            String userId = rankList.get(i).getUserId();
            if (userService.getByPK(userId).getStatus().equals(1)) {
                rankList.remove(i);
                i = i - 1;
            }

            if (userId.equals(id)) {
                break;
            }
        }
        return i + 1;
        //return userPointStatisticsService.getCountByFilter(userPointStatisticsFilterMapper).intValue();
    }

    public Integer getUserCountRank(String id) {
        UserPointStatisticsFilterMapper userPointStatisticsFilterMapper = new UserPointStatisticsFilterMapper();
        userPointStatisticsFilterMapper.userId = id;
        UserPointStatistics userPointStatistics = userPointStatisticsService.getListByFilter(userPointStatisticsFilterMapper).stream().findFirst().orElse(null);
        if (userPointStatistics == null) return null;
        userPointStatisticsFilterMapper.userId = null;
//        userPointStatisticsFilterMapper.countFrom = userPointStatistics.getCount();
        userPointStatisticsFilterMapper.orderBy = Arrays.asList("count desc", "gmtUpdate asc");
        List<UserPointStatistics> userPointStatisticsList = userPointStatisticsService.getListByFilter(userPointStatisticsFilterMapper);
        int i;
        for (i = 0; i < userPointStatisticsList.size(); i++) {
            String userId = userPointStatisticsList.get(i).getUserId();
            if (userService.getByPK(userId).getStatus().equals(1)) {
                userPointStatisticsList.remove(i);
                i = i - 1;
            }

            if (userId.equals(id)) {
                break;
            }
        }
        return i + 1;
    }
}
