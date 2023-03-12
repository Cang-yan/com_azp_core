package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeFourUserFilter;
import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityTypeFourUserExtendsService {
    @Autowired
    private ActivityTypeFourUserService activityTypeFourUserService;

    @Autowired
    private ActivityTypeFourService activityTypeFourService;

    @Autowired
    private PointExtendsService pointExtendsService;

    @Autowired
    private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

    @Autowired
    private GroupPointStatisticsService groupPointStatisticsService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UseInfoService useInfoService;

    public ActivityTypeFour quitGroup(String activityTypeFourId, String UserId)//跟拒绝邀请基本一致
    {
        ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(activityTypeFourId);
        if (activityTypeFour == null) throw new StatusException("ACTIVITY_NOT_EXIST");
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper.activityTypeFourId = activityTypeFour.getId();
        activityTypeFourUserFilterMapper.userId = UserId;
        List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
        ActivityTypeFourUser activityTypeFourUser = activityTypeFourUserList.get(0);//定位到自己的记录

//        NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();
//        notificationUserFilterMapper.userId = UserId;
//        List<NotificationUser> notificationUsers = notificationUserService.getListByFilter(notificationUserFilterMapper);
//        for(NotificationUser notificationUser:notificationUsers)
//        {
//            Notification notification = notificationService.getByPK(notificationUser.getNotificationId());
//            if(Objects.equals(notification.getContent(), activityTypeFourId))
//            {
//                notificationUser.setStatus(8);
//                notificationUser.setGmtUpdate(new Date());
//                notificationUserService.update(notificationUser);
//            }
//        }
        activityTypeFourUser.setStatus(8);
        activityTypeFourUser.setGmtUpdate(new Date());
        activityTypeFourUserService.update(activityTypeFourUser);

        //添加一个拒绝通知
        NotificationPostMapper notificationPostMapper = new NotificationPostMapper();
        NotificationUserPostMapper notificationUserPostMapper = new NotificationUserPostMapper();
        notificationPostMapper.title = "退出队伍";//
        notificationPostMapper.type = 10;
        notificationPostMapper.content = activityTypeFourId;//队伍Id
        notificationPostMapper.relationId = activityTypeFourId;//content里面的理论上可以去掉了
        Notification notification1 = notificationService.post(notificationPostMapper.buildEntity());
        notificationUserPostMapper.notificationId = notification1.getId();
        notificationUserPostMapper.type = 1;
        notificationUserPostMapper.status = 0;//未读
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper2 = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper2.activityTypeFourId = activityTypeFourId;
        activityTypeFourUserFilterMapper2.place = 1;
        //收件人就是发起人
        notificationUserPostMapper.userId = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper2).get(0).getUserId();
        notificationUserPostMapper.senderId = UserId;
        notificationUserService.postMapping(notificationUserPostMapper);

        return activityTypeFour;
    }

    public List<GroupPointStatistics> getRank()
    {
        GroupPointStatisticsFilterMapper groupPointStatisticsFilterMapper = new GroupPointStatisticsFilterMapper();
        ActivityTypeFourPeriodsFilterMapper activityTypeFourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
        activityTypeFourPeriodsFilterMapper.periodsNumber = ActivityTypeFourExtendsService.getPeriodCount();
        groupPointStatisticsFilterMapper.status = 0;
        groupPointStatisticsFilterMapper.orderBy = new ArrayList<>();
        groupPointStatisticsFilterMapper.orderBy.add("pointNum desc");
        return groupPointStatisticsService.getListByFilter(groupPointStatisticsFilterMapper);
    }

    public ActivityTypeFour acceptInvite(ActivityTypeFourUserPostMapper activityTypeFourUserPostMapper)
    {
        ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(activityTypeFourUserPostMapper.activityTypeFourId);
        //检测邀请是否为有效的
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapperBefore = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapperBefore.statusIn = Collections.singletonList(5);
        activityTypeFourUserFilterMapperBefore.userId = activityTypeFourUserPostMapper.userId;
        List<ActivityTypeFourUser> activityTypeFourUserListbefore = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapperBefore);
        if(activityTypeFourUserListbefore.size() != 0)
        {
            ActivityTypeFour activityTypeFourBefore = activityTypeFourService.getByPK(activityTypeFourUserListbefore.get(0).getActivityTypeFourId());
            if (activityTypeFourBefore.getStatus() == 3) throw new StatusException("ALREADY_TEAMED");
        }
        Assert.isTrue(activityTypeFour != null, "ACTIVITY_NOT_EXIST");
        Assert.isTrue(activityTypeFour.getStatus() != 2, "INVITE_NULL");
        /*
         * 如果之前有邀请作废
         * 这里的前提条件是只能接受一个其他人的邀请，所以如果之前有正在组队状态的数据那么意味着这个就是当前用户创建的
         */
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilter = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilter.userId = activityTypeFourUserPostMapper.userId;
        activityTypeFourUserFilter.statusIn = Collections.singletonList(0);//拿到所有待确认的邀请
        List<ActivityTypeFourUser> activityTypeFourusers = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilter);
        for(ActivityTypeFourUser activityTypeFourUser: activityTypeFourusers)
        {
            if(!Objects.equals(activityTypeFourUser.getActivityTypeFourId(), activityTypeFourUserPostMapper.activityTypeFourId))//对剩下的邀请进行处理
            {
                refuseInvite(activityTypeFourUser.getActivityTypeFourId(), activityTypeFourUserPostMapper.userId);
            }
        }

        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper1 = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper1.userId = activityTypeFourUserPostMapper.userId;
        activityTypeFourUserFilterMapper1.statusIn = Collections.singletonList(5);
        List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper1);

        if(activityTypeFourUserList.size() != 0)//说明之前有队伍
        {
            ActivityTypeFour activityTypeFour1 = activityTypeFourService.getByPK(activityTypeFourUserList.get(0).getActivityTypeFourId());
            if(activityTypeFourUserList.get(0).getPlace() == 1)//如果自己是leader的话，要对自己的队伍进行处理
            {
                Assert.isTrue(!Objects.equals(activityTypeFourUserList.get(0).getActivityTypeFourId(), activityTypeFourUserPostMapper.activityTypeFourId), "RE_ENROLL");
                //修改之前邀请的队伍信息
                activityTypeFour1.setStatus(2);
                activityTypeFourService.update(activityTypeFour1);
                //修改之前接受了自己邀请的人员信息包括自己
                ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper2 = new ActivityTypeFourUserFilterMapper();
                activityTypeFourUserFilterMapper2.activityTypeFourId = activityTypeFour1.getId();
                List<ActivityTypeFourUser> activityTypeFourUserList1 = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper2);
                for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUserList1)
                {
                    activityTypeFourUser.setStatus(3);
                    if(activityTypeFourUser.getPlace() != 1)
                    {
                        //给队员添加通知
                        NotificationPostMapper notificationPostMapper = new NotificationPostMapper();
                        NotificationUserPostMapper notificationUserPostMapper = new NotificationUserPostMapper();
                        notificationPostMapper.title = "队伍解散";//
                        notificationPostMapper.type = 10;
                        notificationPostMapper.relationId = activityTypeFour1.getId();//content里面的理论上可以去掉了
                        notificationPostMapper.content = activityTypeFour1.getId();//队伍Id
                        Notification notification1 = notificationService.post(notificationPostMapper.buildEntity());

                        notificationUserPostMapper.notificationId = notification1.getId();
                        notificationUserPostMapper.type = 1;
                        notificationUserPostMapper.status = 0;
                        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
                        activityTypeFourUserFilterMapper.place = 1;
                        activityTypeFourUserFilterMapper.activityTypeFourId = activityTypeFour1.getId();
                        notificationUserPostMapper.userId = activityTypeFourUser.getUserId();//队员
                        notificationUserPostMapper.senderId = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper).get(0).getUserId();//队长
                        notificationUserService.postMapping(notificationUserPostMapper);

                    }
                }
                activityTypeFourUserService.updateList(activityTypeFourUserList1);
                //
            }
            else//相当于拒绝了原来的邀请，接受了现在的邀请
            {
                ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper2 = new ActivityTypeFourUserFilterMapper();
                activityTypeFourUserFilterMapper2.userId = activityTypeFourUserList.get(0).getUserId();
                activityTypeFourUserFilterMapper2.statusIn = Collections.singletonList(5);
                List<ActivityTypeFourUser> activityTypeFourUserList1 = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper2);
                activityTypeFourUserList1.get(0).setStatus(8);
                activityTypeFourUserService.update(activityTypeFourUserList1.get(0));
                //
                //添加一个拒绝通知
                NotificationPostMapper notificationPostMapper = new NotificationPostMapper();
                NotificationUserPostMapper notificationUserPostMapper = new NotificationUserPostMapper();
                notificationPostMapper.title = "退出队伍";//
                notificationPostMapper.type = 10;
                notificationPostMapper.content = activityTypeFour1.getId();//队伍Id
                notificationPostMapper.relationId = activityTypeFour1.getId();//content里面的理论上可以去掉了
                Notification notification1 = notificationService.post(notificationPostMapper.buildEntity());
                notificationUserPostMapper.notificationId = notification1.getId();
                notificationUserPostMapper.type = 1;
                notificationUserPostMapper.status = 0;//未读
                ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
                activityTypeFourUserFilterMapper.activityTypeFourId = activityTypeFour1.getId();
                activityTypeFourUserFilterMapper.place = 1;
                notificationUserPostMapper.userId = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper).get(0).getUserId();
                notificationUserPostMapper.senderId = activityTypeFourUserPostMapper.userId;
                notificationUserService.postMapping(notificationUserPostMapper);
            }
        }

        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper.activityTypeFourId = activityTypeFourUserPostMapper.activityTypeFourId;
        activityTypeFourUserFilterMapper.userId = activityTypeFourUserPostMapper.userId;
        List<ActivityTypeFourUser> activityTypeFourUsers = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
        activityTypeFourUsers.get(0).setStatus(5);
        activityTypeFourUsers.get(0).setGmtUpdate(new Date());
        activityTypeFourUserService.update(activityTypeFourUsers.get(0));

        NotificationPostMapper notificationPostMapper = new NotificationPostMapper();
        NotificationUserPostMapper notificationUserPostMapper = new NotificationUserPostMapper();
        notificationPostMapper.title = "接受邀请";//
        notificationPostMapper.type = 10;
        notificationPostMapper.relationId = activityTypeFourUserPostMapper.activityTypeFourId;//content里面的理论上可以去掉了
        notificationPostMapper.content = activityTypeFourUserPostMapper.activityTypeFourId;//队伍Id
        Notification notification1 = notificationService.post(notificationPostMapper.buildEntity());
        notificationUserPostMapper.notificationId = notification1.getId();
        notificationUserPostMapper.type = 1;
        notificationUserPostMapper.status = 0;
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper2 = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper2.activityTypeFourId = activityTypeFourUserPostMapper.activityTypeFourId;
        activityTypeFourUserFilterMapper2.place = 1;
        notificationUserPostMapper.userId = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper2).get(0).getUserId();
        notificationUserPostMapper.senderId = activityTypeFourUserPostMapper.userId;
        notificationUserService.postMapping(notificationUserPostMapper);

        NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();
        notificationUserFilterMapper.userId = activityTypeFourUsers.get(0).getUserId();
        List<NotificationUser> notificationUsers = notificationUserService.getListByFilter(notificationUserFilterMapper);
        for(NotificationUser notificationUser:notificationUsers)
        {
            Notification notification = notificationService.getByPK(notificationUser.getNotificationId());
            if(Objects.equals(notification.getContent(), activityTypeFourUserPostMapper.activityTypeFourId))
            {
                notificationUser.setStatus(5);
                notificationUser.setGmtUpdate(new Date());
                notificationUserService.update(notificationUser);
            }
        }

        //检测接受之后队伍是不是满三个人了
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper3 = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper3.activityTypeFourId = activityTypeFourUserPostMapper.activityTypeFourId;
        List<ActivityTypeFourUser> activityTypeFourUserList2 = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper3);
        Integer count = 0;
        for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUserList2)
        {
            if(activityTypeFourUser.getStatus() == 5)
            {
                count++;
            }
        }
        if(count == 3)
        {
            activityTypeFour.setStatus(3);
            activityTypeFour.setGroupDate(new Date());
            activityTypeFourService.update(activityTypeFour);
        }
        return activityTypeFour;
    }


    public ActivityTypeFour refuseInvite(String activityTypeFourId, String UserId)
    {
        ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(activityTypeFourId);
        Assert.isTrue(activityTypeFour != null, "ACTIVITY_NOT_EXIST");
        Assert.isTrue(activityTypeFour.getStatus() != 2, "INVITE_NULL");
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper.activityTypeFourId = activityTypeFour.getId();
        activityTypeFourUserFilterMapper.userId = UserId;
        List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
        ActivityTypeFourUser activityTypeFourUser = activityTypeFourUserList.get(0);//定位到自己的记录
        //修改邀请通知的状态
        NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();
        notificationUserFilterMapper.userId = UserId;
        List<NotificationUser> notificationUsers = notificationUserService.getListByFilter(notificationUserFilterMapper);
        for(NotificationUser notificationUser:notificationUsers)
        {
            Notification notification = notificationService.getByPK(notificationUser.getNotificationId());
            if(Objects.equals(notification.getContent(), activityTypeFourId))
            {
                notificationUser.setStatus(8);
                notificationUser.setGmtUpdate(new Date());
                notificationUserService.update(notificationUser);
            }
        }
        activityTypeFourUser.setStatus(8);
        activityTypeFour.setGmtUpdate(new Date());
        activityTypeFourUserService.update(activityTypeFourUser);

        //添加一个拒绝通知
        NotificationPostMapper notificationPostMapper = new NotificationPostMapper();
        NotificationUserPostMapper notificationUserPostMapper = new NotificationUserPostMapper();
        notificationPostMapper.title = "拒绝邀请";//
        notificationPostMapper.type = 10;
        notificationPostMapper.content = activityTypeFourId;//队伍Id
        notificationPostMapper.relationId = activityTypeFourId;//content里面的理论上可以去掉了
        Notification notification1 = notificationService.post(notificationPostMapper.buildEntity());
        notificationUserPostMapper.notificationId = notification1.getId();
        notificationUserPostMapper.type = 1;
        notificationUserPostMapper.status = 0;
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper2 = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper2.activityTypeFourId = activityTypeFourId;
        activityTypeFourUserFilterMapper2.place = 1;
        //收件人就是发起人
        notificationUserPostMapper.userId = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper2).get(0).getUserId();
        notificationUserPostMapper.senderId = UserId;
        notificationUserService.postMapping(notificationUserPostMapper);

        return activityTypeFour;
    }

    public List<ActivityTypeFourUser> getTeamMate(String activityTypeFourId)
    {
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper1 = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper1.activityTypeFourId = activityTypeFourId;
        activityTypeFourUserFilterMapper1.orderBy = new ArrayList<>();
        activityTypeFourUserFilterMapper1.orderBy.add("place asc");
        return activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper1);
    }

    public Map<String, Object> getPresentTeamInfo(String userId)
    {
        ActivityTypeFourTeamMapper activityTypeFourTeamMapper = new ActivityTypeFourTeamMapper();
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper.userId = userId;
        activityTypeFourUserFilterMapper.statusIn = Collections.singletonList(5);
        List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
        Assert.isTrue(activityTypeFourUserList.size()!=0,"TEAM_NULL");//没有组队信息

        ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(activityTypeFourUserList.get(0).getActivityTypeFourId());
        Assert.isTrue(activityTypeFour != null,"TEAM_NULL");//没有组队信息
        List<ActivityTypeFourUser> activityTypeFourUsers = getTeamMate(activityTypeFourUserList.get(0).getActivityTypeFourId());
        //添加积分情况
        activityTypeFourTeamMapper.userList = new ArrayList<>();
        activityTypeFourTeamMapper.id = activityTypeFour.getId();
        activityTypeFourTeamMapper.name = activityTypeFour.getName();
        activityTypeFourTeamMapper.groupDate = activityTypeFour.getGroupDate();
        activityTypeFourTeamMapper.status = activityTypeFour.getStatus();
        activityTypeFourTeamMapper.activitySubCategoryId = activityTypeFour.getActivitySubCategoryId();
        if(activityTypeFour.getStatus() == 3)
        {
            List<GroupPointStatistics> groupPointStatisticsList = getRank();
            for(int i = 0; i < groupPointStatisticsList.size(); i++)
            {
                if(Objects.equals(groupPointStatisticsList.get(i).getGroupId(), activityTypeFour.getId()))
                {
                    activityTypeFourTeamMapper.rank = i + 1;
                    activityTypeFourTeamMapper.groupPoint = groupPointStatisticsList.get(i).getPointNum();
                }
            }
            activityTypeFourTeamMapper.point = activityTypeFour.getPoint();//奖励
            for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUsers)
            {
                if(activityTypeFourUser.getStatus() != 5)
                {
                    continue;
                }
                Map<String,Object>map = new HashMap<>();
                map.put("userInfo", userService.getByPK(activityTypeFourUser.getUserId()));
                ActivityTypeFourPeriodsFilterMapper activityTypeFourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
                activityTypeFourPeriodsFilterMapper.periodsNumber = ActivityTypeFourExtendsService.getPeriodCount();
                ActivityTypeFourPeriods activityTypeFourPeriods = activityTypeFourPeriodsService.getListByFilter(activityTypeFourPeriodsFilterMapper).get(0);
                List<Point> points = pointExtendsService.getUserTotalPointWithTime(activityTypeFourUser.getUserId(), activityTypeFour.getGroupDate(), activityTypeFourPeriods.getEndDate());
                int totalPoint = 0;
                for(Point point : points)
                {
                    totalPoint += point.getPointNumber();
                }
                UseInfo useInfo = useInfoService.getByPK(userService.getByPK(activityTypeFourUser.getUserId()).getUserInfoId());
                if(useInfo != null && useInfo.getHead() != null)
                {
                    map.put("head", useInfo.getHead());
                }
                else
                {
                    map.put("head", null);
                }
                map.put("userPointList", points);
                map.put("userTotalPoint", totalPoint);

                activityTypeFourTeamMapper.userList.add(map);
            }
        }
        else
        {
            for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUsers) {
                if(activityTypeFourUser.getStatus() == 3)
                {
                    continue;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("userInfo", userService.getByPK(activityTypeFourUser.getUserId()));
                map.put("status", activityTypeFourUser.getStatus());

                activityTypeFourTeamMapper.userList.add(map);
            }
        }
        return activityTypeFourTeamMapper.buildMap();
    }

    public List<Map<String, Object>> getAllTeamInfo(String userId)//用户历史组队
    {
        List<Map<String, Object>> TeamInfoMapList = new ArrayList<>();
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
        activityTypeFourUserFilterMapper.userId = userId;
        List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
        for(ActivityTypeFourUser activityTypeFourUser:activityTypeFourUserList)
        {
            ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(activityTypeFourUser.getActivityTypeFourId());
            if(activityTypeFour == null){continue;}
            if(activityTypeFour.getStatus() == 5)
            {
                List<ActivityTypeFourUser> activityTypeFourUsers = getTeamMate(activityTypeFour.getId());
                ActivityTypeFourTeamMapper activityTypeFourTeamMapper = new ActivityTypeFourTeamMapper();
                activityTypeFourTeamMapper.userList = new ArrayList<>();
                activityTypeFourTeamMapper.point = activityTypeFour.getPoint();
                activityTypeFourTeamMapper.id = activityTypeFour.getId();
                if(Objects.equals(activityTypeFour.getPeriodsNumber(), ActivityTypeFourExtendsService.getPeriodCount()))
                {
                    List<GroupPointStatistics> groupPointStatisticsList = getRank();
                    for(int i = 0; i < groupPointStatisticsList.size(); i++)
                    {
                        if(Objects.equals(groupPointStatisticsList.get(i).getGroupId(), activityTypeFour.getId()))
                        {
                            activityTypeFourTeamMapper.rank = i + 1;
                        }
                    }
                }
                else{
                    activityTypeFourTeamMapper.rank = activityTypeFour.getRank();
                }
                activityTypeFourTeamMapper.activitySubCategoryId = activityTypeFour.getActivitySubCategoryId();
                activityTypeFourTeamMapper.status = activityTypeFour.getStatus();
                activityTypeFourTeamMapper.name = activityTypeFour.getName();
                activityTypeFourTeamMapper.periodsNumber = activityTypeFour.getPeriodsNumber();
                activityTypeFourTeamMapper.gmtCreate = activityTypeFour.getGmtCreate();
                activityTypeFourTeamMapper.gmtUpdate = activityTypeFour.getGmtUpdate();
                activityTypeFourTeamMapper.groupDate = activityTypeFour.getGroupDate();
                ActivityTypeFourPeriodsFilterMapper activityTypeFourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
                activityTypeFourPeriodsFilterMapper.periodsNumber = activityTypeFour.getPeriodsNumber();
                List<ActivityTypeFourPeriods> activityTypeFourPeriodsList = activityTypeFourPeriodsService.getListByFilter(activityTypeFourPeriodsFilterMapper);
                int teamTotalPoint = 0;
                for(ActivityTypeFourUser activityTypeFourUser1 : activityTypeFourUsers)
                {
                    if(activityTypeFourUser1.getStatus() == 6)
                    {
                        Map<String,Object>map = new HashMap<>();
                        map.put("userId", activityTypeFourUser1.getUserId());
                        map.put("userInfo", userService.getByPK(activityTypeFourUser1.getUserId()));
                        UseInfo useInfo = useInfoService.getByPK(userService.getByPK(activityTypeFourUser1.getUserId()).getUserInfoId());
                        if(useInfo != null && useInfo.getHead() != null)
                        {
                            map.put("head", useInfo.getHead());
                        }
                        else
                        {
                            map.put("head", null);
                        }
                        List<Point> points = pointExtendsService.getUserTotalPointWithTime(activityTypeFourUser1.getUserId(),activityTypeFour.getGroupDate(), activityTypeFourPeriodsList.get(0).getEndDate());
                        int totalPoint = 0;

                        for(Point point : points)
                        {
                            totalPoint += point.getPointNumber();
                        }
                        map.put("userPointList", points);
                        map.put("userTotalPoint", totalPoint);
                        teamTotalPoint += totalPoint;
                        activityTypeFourTeamMapper.userList.add(map);
                    }
                }
                activityTypeFourTeamMapper.groupPoint = teamTotalPoint;
                TeamInfoMapList.add(activityTypeFourTeamMapper.buildMap());
            }
        }
        return TeamInfoMapList;
    }
}
