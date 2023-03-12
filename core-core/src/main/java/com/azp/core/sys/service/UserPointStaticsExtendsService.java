package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.UserPointStatisticsFilter;
import com.azp.core.sys.datainterface.UserPointStatisticsDAO;
import com.azp.core.sys.dataobject.UserPointStatisticsDO;
import com.azp.core.sys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/1/26 12:41
 */

@Service
public class UserPointStaticsExtendsService {
    @Autowired
    UserPointStatisticsService userPointStatisticsService;
    @Autowired
    PointService pointService;
    @Autowired
    private UserPointStatisticsDAO userPointStatisticsDAO;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    private UseInfoService useInfoService;


    public Integer pointNumberByType(List<Integer> types, String userId) {
        PointFilterMapper pointFilterMapper = new PointFilterMapper();
        pointFilterMapper.userId = userId;
        pointFilterMapper.typeIn = types;
        return pointService.getListByFilter(pointFilterMapper).stream().mapToInt(Point::getPointNumber).sum();
    }

    //获取部门的下拉列表
    public List<Department> getDepartmentList() {
        List<Department> departmentList = new ArrayList<>();
        for (UserPointStatistics userPointStatistics : userPointStatisticsService.getListByFilter(new UserPointStatisticsFilterMapper())) {
            DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
            departmentFilterMapper.name = userPointStatistics.getDepartmentName();
            for (Department demo : departmentService.getListByFilter(departmentFilterMapper))
                if (!isExit(demo, departmentList) && !"Visitor".equals(demo.getName())) departmentList.add(demo);


        }
        return departmentList;

    }

    public boolean isExit(Department department, List<Department> departments) {
        boolean bo = false;
        for (Department de : departments) {
            if (de.getName().equals(department.getName())) {
                bo = true;
                break;
            }

        }
        return bo;

    }


    //以下都是个人积分排行榜
    public List<Map<String, Object>> getListByFilter(UserPointStatisticsFilterMapper filterMapper) {
        List<UserPointStatistics> toBeSortedList = new ArrayList<>();
        for (UserPointStatisticsDO entity : userPointStatisticsDAO.selectByExample(UserPointStatisticsFilter.initDOQueryFilter(filterMapper.buildMap()))) {
            toBeSortedList.add(UserPointStatisticsData.convert(entity, new UserPointStatistics()));
        }

        if (toBeSortedList.isEmpty()) return new ArrayList<>();
        List<Map<String, Object>> endList = new ArrayList<>();
        for (UserPointStatistics userPointStatistics : collectElement(toBeSortedList)) {
            Map<String, Object> mapList = new HashMap<>();
            String groupName = null;
            try {
                String userId = userPointStatistics.getUserId();
                User user = userService.getByPK(userId);
                if(user.getStatus().equals(1)) continue;
                UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
                useInfoFilterMapper.userId = userId;
                UseInfo useInfo = useInfoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
                if (useInfo == null) {
                    useInfo = new UseInfo();
                    useInfo.setPointNumber(0);
                    useInfo.setLevelId("");

                    useInfo.setUserId(userId);
                    UseInfo post = useInfoService.post(useInfo);
                    user.setUserInfoId(post.getId());
                    user = userService.update(user);
                    mapList.put("head","");
                }else {
                    mapList.put("head",useInfo.getHead());
                }
                mapList.put("user", userService.getDetailMapByPK(userId));
                groupName = groupService.getByPK(
                        user.getGroupId()
                ).getGroupName();
            } catch (Exception e) {
                // no op
            }
            mapList.put("groupName", groupName);
            mapList.putAll(UserPointStatisticsDetailMapper.buildMap(userPointStatistics));
            endList.add(mapList);
        }
        return endList;
    }


    //基本逻辑就是，如果n个人的分数相同，那么就去查他们在积分表里的gmtCreate并比较，越小的越在前面
    //入参是直接查到的原始的排名，返回的是按照一样分数的具体要求（先达到的排前面）细化排序后的排名
    public List<UserPointStatistics> collectElement(List<UserPointStatistics> userPointStatisticsList) {
        List<UserPointStatistics> endList = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < userPointStatisticsList.size(); i++) {
            UserPointStatistics userPointStatistics = userPointStatisticsList.get(i);
            List<UserPointStatistics> tempList = new ArrayList<>();
            //userPointStaticsList的表前面判空了，所以这里的templist不可能是空
            tempList.add(userPointStatistics);
            for (j = i + 1; j < userPointStatisticsList.size(); j++) {
                if (userPointStatisticsList.get(j).getNumber().equals(userPointStatistics.getNumber())) {
                    tempList.add(userPointStatisticsList.get(j));
                } else {
                    i = j - 1;//为了从当前不相等的地方重新进入循环，得到另一组同分的
                    break;
                }
            }
            //到这就拿到了同分的元素
            simpleSort(tempList, endList);
            if (j == userPointStatisticsList.size()) break;
        }
        return endList;
    }

    //对temp进行排序以后，放在endlilst后面,
    public List<UserPointStatistics> simpleSort(List<UserPointStatistics> tempList, List<UserPointStatistics> entityList) {
        //冒泡排序，比较同分用户，最后获取积分时间早的排在前面
        for (int j = 0; j < tempList.size() - 1; j++) {
            for (int i = 0; i < tempList.size() - 1 - j; i++) {
                if (getGmtCreateAcrossData(tempList.get(i)).after(getGmtCreateAcrossData(tempList.get(i + 1)))) {
                    UserPointStatistics temp = tempList.get(i);
                    tempList.set(i, tempList.get(i + 1));
                    tempList.set(i + 1, temp);

                }
            }
        }
        entityList.addAll(tempList);
        return entityList;
    }

    //得出该用户最后获取积分的时间
    public Date getGmtCreateAcrossData(UserPointStatistics userPointStatistics) {
        PointFilterMapper pointFilterMapper = new PointFilterMapper();
        pointFilterMapper.userId = userPointStatistics.getUserId();
        List<Point> pointList = pointService.getListByFilter(pointFilterMapper);
        //如果pointlist为空的话，表示这个人就没有额外的获取积分，给他的排名排在最后去
        if (pointList.isEmpty()) return new Date();
        return pointList.get(0).getGmtCreate();
    }








    //个人经验排行榜
    public List<Map<String,Object>> getCountRank(UserPointStatisticsFilterMapper userPointStatisticsFilterMapper){


        userPointStatisticsFilterMapper.orderBy = Arrays.asList("count desc", "gmtUpdate asc");
        List<UserPointStatistics> userPointStatisticsList = userPointStatisticsService.getListByFilter(userPointStatisticsFilterMapper);

        if (userPointStatisticsList.isEmpty()) return new ArrayList<>();
        List<Map<String, Object>> endList = new ArrayList<>();


        for (UserPointStatistics userPointStatistics : userPointStatisticsList) {
            Map<String, Object> mapList = new HashMap<>();
            String groupName = null;
            try {
                String userId = userPointStatistics.getUserId();
                User user = userService.getByPK(userId);
                UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
                useInfoFilterMapper.userId = userId;
                UseInfo useInfo = useInfoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
                if (useInfo == null) {
                    useInfo = new UseInfo();
                    useInfo.setPointNumber(0);
                    useInfo.setLevelId("");

                    useInfo.setUserId(userId);
                    UseInfo post = useInfoService.post(useInfo);
                    user.setUserInfoId(post.getId());
                    user = userService.update(user);
                    mapList.put("head","");
                }else {
                    mapList.put("head",useInfo.getHead());
                }
                mapList.put("user", userService.getDetailMapByPK(userId));
                groupName = groupService.getByPK(
                        user.getGroupId()
                ).getGroupName();
            } catch (Exception e) {
                // no op
            }
            mapList.put("groupName", groupName);
            mapList.putAll(UserPointStatisticsDetailMapper.buildMap(userPointStatistics));
            endList.add(mapList);
        }
        return endList;


    }


}


