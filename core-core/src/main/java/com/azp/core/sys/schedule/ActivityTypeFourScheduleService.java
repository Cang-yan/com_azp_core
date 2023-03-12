package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import com.azp.core.sys.model.*;
import com.azp.core.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@TaskScheduleComponent
public class ActivityTypeFourScheduleService {
    private static long current = 0;
    @Autowired
    private ActivityTypeFourService activityTypeFourService;

    @Autowired
    private ActivityTypeFourUserService activityTypeFourUserService;

    @Autowired
    private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

    @Autowired
    private ActivityTypeFourUserExtendsService activityTypeFourUserExtendsService;

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PointService pointService;

    @Autowired
    private GroupPointStatisticsService groupPointStatisticsService;
    @Task(cron = "0 0 0 1 1/3 ?")
    @Distributed
    public Runnable refreshOne()
    {
        return ()->{
            //记录这一期所有队伍的排名和积分情况
            List<GroupPointStatistics> groupPointStatisticsList = activityTypeFourUserExtendsService.getRank();
            for(int i = 0; i < groupPointStatisticsList.size(); i++)
            {
                GroupPointStatistics groupPointStatistics = groupPointStatisticsList.get(i);
                ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(groupPointStatistics.getGroupId());
                activityTypeFour.setRank(i + 1);
                activityTypeFour.setGroupPoint(groupPointStatistics.getPointNum());
                activityTypeFourService.update(activityTypeFour);
            }
            //给第一名的队伍添加积分记录
            ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(groupPointStatisticsList.get(0).getGroupId());
            ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
            if(activityTypeFour != null)
            {
                activityTypeFour.setPoint(ActivityTypeFourExtendsService.getRewardPoint());
                activityTypeFourUserFilterMapper.activityTypeFourId = activityTypeFour.getId();
                List<ActivityTypeFourUser>activityTypeFourUsers = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
                for(ActivityTypeFourUser activityTypeFourUser1 : activityTypeFourUsers)
                {
                    PointPostMapper pointPostMapper = new PointPostMapper();
                    pointPostMapper.type = 10;
                    pointPostMapper.title = activityTypeFour.getName() + "队伍第一名获取积分";
                    pointPostMapper.userId = activityTypeFourUser1.getUserId();
                    pointPostMapper.getTime = new Date().getTime();
                    pointPostMapper.pointNumber = ActivityTypeFourExtendsService.getRewardPoint() / 3;
                    pointService.postMapping(pointPostMapper);
                }
            }
            //修改所有队伍的信息
            ActivityTypeFourFilterMapper mapper = new ActivityTypeFourFilterMapper();
            ActivityTypeFourUserFilterMapper usermapper = new ActivityTypeFourUserFilterMapper();
            mapper.statusIn = Collections.singletonList(3);
            List<ActivityTypeFour> activityTypeFourEntityList = activityTypeFourService.getListByFilter(mapper);
            for(ActivityTypeFour activityTypeFourEntity: activityTypeFourEntityList)
            {
                //修改之前组队成功的队伍为已完成
                activityTypeFourEntity.setStatus(5);
                usermapper.activityTypeFourId = activityTypeFourEntity.getId();
                List<ActivityTypeFourUser> activityTypeFourUserEntityList = activityTypeFourUserService.getListByFilter(usermapper);
                for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUserEntityList)
                {
                    //修改所有人的状体为6：组队失效
                    if(activityTypeFourUser.getStatus() == 5)
                    {
                        activityTypeFourUser.setStatus(6);
                    }
                }
                activityTypeFourUserService.updateList(activityTypeFourUserEntityList);
            }

            //添加周期信息
            ActivityTypeFourPeriodsPostMapper activityTypeFourPeriodsPostMapper = new ActivityTypeFourPeriodsPostMapper();
            activityTypeFourPeriodsPostMapper.periodsNumber = ActivityTypeFourExtendsService.getPeriodCount() + 1;
            activityTypeFourPeriodsPostMapper.beginDate = new Date().getTime() / 1000 * 1000;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, 3 );
            calendar.add(Calendar.SECOND, -1);
            activityTypeFourPeriodsPostMapper.endDate = calendar.getTimeInMillis() / 1000 * 1000;
            activityTypeFourPeriodsService.postMapping(activityTypeFourPeriodsPostMapper);
            //修改排行榜信息为过期
            GroupPointStatisticsFilterMapper groupPointStatisticsFilterMapper = new GroupPointStatisticsFilterMapper();
            groupPointStatisticsFilterMapper.status = 0;
            List<GroupPointStatistics> groupPointStatistics = groupPointStatisticsService.getListByFilter(groupPointStatisticsFilterMapper);
            for(GroupPointStatistics groupPointStatistics1: groupPointStatistics)
            {
                groupPointStatistics1.setStatus(1);
                groupPointStatistics1.setGmtUpdate(new Date());
            }
            groupPointStatisticsService.updateList(groupPointStatistics);

            ActivityTypeFourExtendsService.setPeriodCount(ActivityTypeFourExtendsService.getPeriodCount() + 1);
            activityTypeFourService.updateList(activityTypeFourEntityList);
        };
    }

    //针对第一周期这个特殊的情况
    @Task(cron = "59 59 23 28 4 2022")
    public Runnable refreshTwoSpecial()
    {
        return ()->{
            //拿到所有状态为1：已发布的活动,这些活动代表组队没有完成
            ActivityTypeFourFilterMapper mapper = new ActivityTypeFourFilterMapper();
            ActivityTypeFourUserFilterMapper usermapper = new ActivityTypeFourUserFilterMapper();
            mapper.statusIn = Collections.singletonList(1);
            List<ActivityTypeFour> activityTypeFourEntityList = activityTypeFourService.getListByFilter(mapper);
            for(ActivityTypeFour activityTypeFourEntity : activityTypeFourEntityList)
            {
                activityTypeFourEntity.setStatus(2);
                usermapper.activityTypeFourId = activityTypeFourEntity.getId();
                List<ActivityTypeFourUser> activityTypeFourUserEntityList = activityTypeFourUserService.getListByFilter(usermapper);
                for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUserEntityList)
                {
                    activityTypeFourUser.setGmtUpdate(new Date());
                    activityTypeFourUser.setStatus(3);
                }
                activityTypeFourUserService.updateList(activityTypeFourUserEntityList);
            }
            activityTypeFourService.updateList(activityTypeFourEntityList);
        };
    }

    //针对在邀请没过期但是报名时间已经到了的情况?
    @Task(cron = "59 59 23 10 1/3 ?")
    @Distributed
    public Runnable refreshTwo()
    {
        return ()->{
            //拿到所有状态为1：已发布的活动,这些活动代表组队没有完成
            ActivityTypeFourFilterMapper mapper = new ActivityTypeFourFilterMapper();
            ActivityTypeFourUserFilterMapper usermapper = new ActivityTypeFourUserFilterMapper();
            mapper.statusIn = Collections.singletonList(1);
            List<ActivityTypeFour> activityTypeFourEntityList = activityTypeFourService.getListByFilter(mapper);
            for(ActivityTypeFour activityTypeFourEntity : activityTypeFourEntityList)
            {
                activityTypeFourEntity.setStatus(2);
                usermapper.activityTypeFourId = activityTypeFourEntity.getId();
                List<ActivityTypeFourUser> activityTypeFourUserEntityList = activityTypeFourUserService.getListByFilter(usermapper);
                for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUserEntityList)
                {
                    activityTypeFourUser.setGmtUpdate(new Date());
                    activityTypeFourUser.setStatus(3);
                }
                activityTypeFourUserService.updateList(activityTypeFourUserEntityList);
            }
            activityTypeFourService.updateList(activityTypeFourEntityList);
        };
    }
    //把满了24小时没有接受邀请的人变为逾期
    @Task(cron = "0 0/1 * * * ?")
    @Distributed
    public Runnable refreshThree()
    {
        return () ->
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE , -1);
            ActivityTypeFourFilterMapper activityTypeFourFilterMapper = new ActivityTypeFourFilterMapper();
            activityTypeFourFilterMapper.statusIn = Collections.singletonList(1);
            List<ActivityTypeFour> activityTypeFourList = activityTypeFourService.getListByFilter(activityTypeFourFilterMapper);
            for(ActivityTypeFour activityTypeFour:activityTypeFourList)
            {
                if(activityTypeFour.getGmtUpdate().before(calendar.getTime()))
                {
                    ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper1 = new ActivityTypeFourUserFilterMapper();
                    activityTypeFourUserFilterMapper1.activityTypeFourId = activityTypeFour.getId();
                    List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper1);
                    for(ActivityTypeFourUser activityTypeFourUser:activityTypeFourUserList)
                    {
                        if(activityTypeFourUser.getStatus() == 0 && activityTypeFourUser.getGmtCreate().before(calendar.getTime()))
                        {
                            NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();
                            notificationUserFilterMapper.userId = activityTypeFourUser.getUserId();
                            List<NotificationUser> notificationUsers = notificationUserService.getListByFilter(notificationUserFilterMapper);
                            for(NotificationUser notificationUser:notificationUsers)
                            {
                                Notification notification = notificationService.getByPK(notificationUser.getNotificationId());
                                if(Objects.equals(notification.getTitle(), activityTypeFour.getId()))
                                {
                                    notificationUser.setStatus(2);
                                    notificationUser.setGmtUpdate(new Date());
                                    notificationUserService.update(notificationUser);
                                }
                            }
                            activityTypeFourUser.setStatus(2);//逾期
                            activityTypeFourUser.setGmtUpdate(new Date());
                            activityTypeFourUserService.update(activityTypeFourUser);
                        }
                    }
                }
            }
        };
    }
}
