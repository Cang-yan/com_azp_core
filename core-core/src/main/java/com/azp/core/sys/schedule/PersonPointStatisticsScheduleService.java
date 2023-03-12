package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import com.azp.core.sys.model.*;
import com.azp.core.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/1/25 17:52
 */
@TaskScheduleComponent
public class PersonPointStatisticsScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    UserService userService;
    @Autowired
    UseInfoService useInfoService;//用户信息管理
    @Autowired
    PointService pointService;//积分管理
    @Autowired
    PointExchangeService pointExchangeService;
    @Autowired
    UserPointStatisticsService userPointStatisticsService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    SchedulerTemplate schedulerTemplate;

    @Autowired
    LevelService levelService;

//    @Task(cron = "0 0/5 * * * ?")
    public Runnable refresh() {
        return () -> {
            lastCurrent = schedulerTemplate.getLastScheduleTime();
            current = new Date().getTime();
            List<UserPointStatistics> userPointStatisticsList = userPointStatisticsService.getListByFilter(new UserPointStatisticsFilterMapper());
            List<User> userList = userService.getListByFilter(new UserFilterMapper());
            //如果用户表里没有数据，表示一个人都没有，就有问题了
            if (userList.isEmpty()) return;

            //算的是每个人对应的积分变动并更新
            for (User user : userList) {
                String userId = user.getId();
                //如果用户积分表里0条数据，或者表里没有该用户数据（在getTargetObject方法体里判定了），则需要新增
                UserPointStatistics userPointStatistics = new UserPointStatistics();
                if (!userPointStatisticsList.isEmpty()) {
                    userPointStatistics = getTargetObject(userId, userPointStatisticsList);
                }

                Integer totalNumber = userPointStatistics.getNumber() == null ? 0 : userPointStatistics.getNumber();
                Integer count = userPointStatistics.getCount() == null ? 0 : userPointStatistics.getCount();

                //拿出时间范围内的积分记录
                PointFilterMapper pointFilterMapper = new PointFilterMapper();
                pointFilterMapper.gmtCreateFrom = lastCurrent;
                pointFilterMapper.gmtCreateTo = current;
                pointFilterMapper.userId = userId;
                List<Point> pointList = pointService.getListByFilter(pointFilterMapper);
                for (Point point : pointList) {
                    if (point.getPointNumber() == null) continue;
                    totalNumber += point.getPointNumber();
                    //判定是否为签到
                    if (point.getType() != null && point.getType() != 1) count++;
                }
                PointExchangeFilterMapper pointExchangeFilterMapper = new PointExchangeFilterMapper();
                pointExchangeFilterMapper.gmtCreateFrom = lastCurrent;
                pointExchangeFilterMapper.gmtCreateTo = current;
                pointExchangeFilterMapper.userId = userId;

                //拿出时间范围内的积分交换记录
                List<PointExchange> pointExchangeList = pointExchangeService.getListByFilter(pointExchangeFilterMapper);
                for (PointExchange pointExchange : pointExchangeList) {
                    if (pointExchange.getPointNum() == null) continue;
                    totalNumber -= pointExchange.getPointNum();
                }

                try {
                    userPointStatistics.setDepartmentName(departmentService.getByPK(userService.getByPK(userId).getDepartmentId()).getName());
                } catch (Exception e) {
                    userPointStatistics.setDepartmentName("");
                }
                userPointStatistics.setNumber(totalNumber);
                userPointStatistics.setCount(count);
                //对应上文，如果该条数据是新增的，则需要把其他信息补全
                if (userPointStatistics.getUserId() == null) {
                    userPointStatistics.setUserId(userId);
                    userPointStatistics.setDate(new Date());
                    userPointStatisticsService.post(userPointStatistics);
                } else {
                    userPointStatisticsService.update(userPointStatistics);
                }
                UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
                useInfoFilterMapper.userId = userId;
                UseInfo useInfo = useInfoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
                if (useInfo == null) {
                    useInfo = new UseInfo();
                    useInfo.setPointNumber(userPointStatistics.getNumber());
                    useInfo.setUserId(userId);
                    UseInfo post = useInfoService.post(useInfo);
                    user.setUserInfoId(post.getId());
                    userService.update(user);
                } else {
                    useInfo.setPointNumber(userPointStatistics.getNumber());
                    useInfoService.update(useInfo);
                }

            }


        };
    }

    /**
     * 校正
     *
     * @return
     */
//    @Task(cron = "0 1 0 * * ?")
    @Distributed
    @Task(cron = "0 * * * * ?")
    public Runnable refreshDaily() {
        return () -> {
            List<UserPointStatistics> userPointStatisticsList = userPointStatisticsService.getListByFilter(new UserPointStatisticsFilterMapper());
            List<User> userList = userService.getListByFilter(new UserFilterMapper());
            if (userList.isEmpty()) return;

            //算的是每个人对应的积分变动并更新
            for (User user : userList) {
                String userId = user.getId();
                //如果用户积分表里0条数据，或者表里没有该用户数据（在getTargetObject方法体里判定了），则需要新增
                UserPointStatistics userPointStatistics = new UserPointStatistics();
                if (!userPointStatisticsList.isEmpty()) {
                    userPointStatistics = getTargetObject(userId, userPointStatisticsList);
                }

                Integer totalNumber = 0;
                Integer count = 0;

                //拿出时间范围内的积分记录
                PointFilterMapper pointFilterMapper = new PointFilterMapper();
                pointFilterMapper.userId = userId;
                List<Point> pointList = pointService.getListByFilter(pointFilterMapper);
                for (Point point : pointList) {
                    if (point.getPointNumber() == null) continue;
                    totalNumber += point.getPointNumber();
                    //判定是否为签到
                    if (point.getType() != null && point.getType() != 1) count++;
                }
                PointExchangeFilterMapper pointExchangeFilterMapper = new PointExchangeFilterMapper();
                pointExchangeFilterMapper.userId = userId;

                //拿出时间范围内的积分交换记录
                List<PointExchange> pointExchangeList = pointExchangeService.getListByFilter(pointExchangeFilterMapper);
                for (PointExchange pointExchange : pointExchangeList) {
                    if (pointExchange.getPointNum() == null) continue;
                    totalNumber -= pointExchange.getPointNum();
                }

                try {
                    userPointStatistics.setDepartmentName(departmentService.getByPK(userService.getByPK(userId).getDepartmentId()).getName());
                } catch (Exception e) {
                    userPointStatistics.setDepartmentName("");
                }
                userPointStatistics.setNumber(totalNumber);
                userPointStatistics.setCount(count);
                //对应上文，如果该条数据是新增的，则需要把其他信息补全
                if (userPointStatistics.getUserId() == null) {
                    userPointStatistics.setUserId(userId);
                    userPointStatistics.setDate(new Date());
                    userPointStatisticsService.post(userPointStatistics);
                } else {
                    userPointStatisticsService.update(userPointStatistics);
                }
                UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
                useInfoFilterMapper.userId = userId;
                UseInfo useInfo = useInfoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
                if (useInfo == null) {
                    useInfo = new UseInfo();
                    useInfo.setPointNumber(userPointStatistics.getNumber());
                    // 重新算等级
                    useInfo.setLevelId(getLevelId(useInfo));
                    useInfo.setUserId(userId);
                    UseInfo post = useInfoService.post(useInfo);
                    user.setUserInfoId(post.getId());
                    userService.update(user);
                } else {
                    useInfo.setPointNumber(userPointStatistics.getNumber());
                    // 重新算等级
                    useInfo.setLevelId(getLevelId(useInfo));
                    useInfoService.update(useInfo);
                }
            }
        };

    }


    private UserPointStatistics getTargetObject(String userId, List<UserPointStatistics> userPointStatisticsList) {
        for (UserPointStatistics userPointStatistics : userPointStatisticsList) {
            if (userPointStatistics.getUserId().equals(userId)) return userPointStatistics;
        }
        return new UserPointStatistics();
    }

    private String getLevelId(UseInfo useInfo) {
        List<Level> levelList = getLevelList();
        Integer pointNumber = useInfo.getPointNumber();
        for (Level level : levelList) {
            if (level.getMinPoint() <= pointNumber && level.getMaxPoint() >= pointNumber)
                return level.getId();
        }
        return "1";
    }

    private List<Level> getLevelList() {
        LevelFilterMapper levelFilterMapper = new LevelFilterMapper();
        levelFilterMapper.page = 0L;
        levelFilterMapper.rows = 0;
        return levelService.getListByFilter(levelFilterMapper);
    }
}