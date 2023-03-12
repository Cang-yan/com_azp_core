package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.azp.core.sys.schedule.DateCommonScheduleService;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityTypeFourExtendsService {
    @Autowired
    private ActivityTypeFourUserService activityTypeFourUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityTypeFourUserExtendsService activityTypeFourUserExtendsService;

    @Autowired
    private ActivityTypeFourService activityTypeFourService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

    @Autowired
    private PointExtendsService pointExtendsService;

    private static Integer rewardPoint = 0;

    private static Integer periodCount = 1;

    public static Integer getRewardPoint()
    {
        return rewardPoint;
    }

    public static void setRewardPoint(Integer rewardPoint)
    {
        ActivityTypeFourExtendsService.rewardPoint = rewardPoint;
    }

    public static Integer getPeriodCount() {return periodCount;}

    public static void setPeriodCount(Integer periodCount) {ActivityTypeFourExtendsService.periodCount = periodCount;}

    public List<User> getUsers(ArrayList<String> UserIds)//获取可邀请对象
    {
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        List<User> AllUser = userService.getListByFilter(userFilterMapper);
        List<User> returnUser = new ArrayList<>();//返回的用户
        List<String> userIds = new ArrayList<>();//上一期的队员编号
        for(String UserId : UserIds)
        {
            if(ActivityTypeFourExtendsService.getPeriodCount() == 1)
            {
                break;
            }
            ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
            activityTypeFourUserFilterMapper.userId = UserId;
            activityTypeFourUserFilterMapper.statusIn = Collections.singletonList(6);
            List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
            //上一期的队员
            List<ActivityTypeFourUser> activityTypeFourUserList1 = new ArrayList<>();
            for(ActivityTypeFourUser activityTypeFourUser: activityTypeFourUserList)
            {
                ActivityTypeFour activityTypeFour = activityTypeFourService.getByPK(activityTypeFourUser.getActivityTypeFourId());
                if(activityTypeFour.getPeriodsNumber() == ActivityTypeFourExtendsService.getPeriodCount() - 1)//上一期的队伍
                {
                    ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper3 = new ActivityTypeFourUserFilterMapper();
                    activityTypeFourUserFilterMapper3.activityTypeFourId = activityTypeFour.getId();
                    activityTypeFourUserList1 = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper3);
                }
            }
            //记录下上一期队员的Id
            userIds.addAll(activityTypeFourUserList1.stream().map(activityTypeFourUser -> activityTypeFourUser.getUserId()).collect(Collectors.toList()));
        }
        for(User user: AllUser)//遍历所有的用户
        {
            if(UserIds.contains(user.getId())){continue;}//自己除掉
            boolean test = true;
            ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper1 = new ActivityTypeFourUserFilterMapper();
            activityTypeFourUserFilterMapper1.userId = user.getId();
            activityTypeFourUserFilterMapper1.statusIn = Collections.singletonList(5);
            List<ActivityTypeFourUser> activityTypeFourUserList2 = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper1);
            if(activityTypeFourUserList2.size() != 0 && activityTypeFourService.getByPK(activityTypeFourUserList2.get(0).getActivityTypeFourId()).getStatus() == 3)
            {//把已经组队成功的用户剔除
                test = false;
            }
            if(userIds.contains(user.getId()))
            {//把上一期的队友剔除
                test = false;
            }
            if(Objects.equals(user.getId(), "root"))
            {
                test =false;
            }
            if(test)
            {
                returnUser.add(user);
            }
        }
        return returnUser;
    }

    //teamMateUserId里面第一个必须是发起者的userID
    public Map<String, Object> sendInvite(ActivityTypeFourPostMapper activityTypeFourPostMapper, ArrayList<String> teamMateUserId)
    {
        Date time = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        if(getPeriodCount() == 1)
        {
            if( ! (calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DAY_OF_MONTH) <= 28 && calendar.get(Calendar.DAY_OF_MONTH) >= 22) ) throw  new StatusException("DATE_INCORRECT");
        }
        else
        {
            ActivityTypeFourPeriodsFilterMapper activityTypeFourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
            activityTypeFourPeriodsFilterMapper.periodsNumber = getPeriodCount();
            Calendar begin = Calendar.getInstance();
            begin.setTime(activityTypeFourPeriodsService.getListByFilter(activityTypeFourPeriodsFilterMapper).get(0).getBeginDate());
            if( ! (calendar.get(Calendar.MONTH) == begin.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) <= 10) ) throw new StatusException("DATE_INCORRECT");
        }
        Assert.isTrue(!Objects.equals(teamMateUserId.get(1), teamMateUserId.get(2)), "INVITE_ERROR");//不能邀请两个一样的人
        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
        //获取队长的组队信息
        activityTypeFourUserFilterMapper.userId = teamMateUserId.get(0);
        activityTypeFourUserFilterMapper.statusIn = Collections.singletonList(5);
        List<ActivityTypeFourUser> activityTypeFourUsers = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);

        ActivityTypeFour activityTypeFourPost = new ActivityTypeFour();
        String leaderId = teamMateUserId.get(0);//发起人ID
//        ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper1 = new ActivityTypeFourUserFilterMapper();
//        activityTypeFourUserFilterMapper1.activityTypeFourId = activityTypeFourPost.getId();
        List<ActivityTypeFourUser> activityTypeFourUserList = new ArrayList<>();//之前的组队信息
        if(activityTypeFourUsers.size() == 0)//判断组队是否是第一次发出邀请
        {
            activityTypeFourPostMapper.periodsNumber = getPeriodCount();
            //添加活动
            activityTypeFourPost = activityTypeFourService.post(activityTypeFourPostMapper.buildEntity());
            //添加人
            ActivityTypeFourUserPostMapper postMapper = new ActivityTypeFourUserPostMapper();
            postMapper.activityTypeFourId = activityTypeFourPost.getId();
            postMapper.userId = teamMateUserId.get(0);
            postMapper.place = 1;
            postMapper.status = 5;
            activityTypeFourUserService.postMapping(postMapper);
        }
        else
        {
            activityTypeFourPost = activityTypeFourService.getByPK(activityTypeFourUsers.get(0).getActivityTypeFourId());
            activityTypeFourPost.setGmtUpdate(new Date());
            activityTypeFourPost.setName(activityTypeFourPostMapper.name);
            activityTypeFourService.update(activityTypeFourPost);
            ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper1 = new ActivityTypeFourUserFilterMapper();
            activityTypeFourUserFilterMapper1.activityTypeFourId = activityTypeFourPost.getId();
            activityTypeFourUserList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper1);
            //第二次发出邀请时把所有没有同意的人的状态改为6
            for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUserList)
            {
                if(activityTypeFourUser.getStatus() != 5)
                {
                    activityTypeFourUser.setStatus(3);//无效的组队信息
                    activityTypeFourUserService.update(activityTypeFourUser);
                }
            }
        }
        teamMateUserId.remove(0);
        //添加通知，这里涉及到位次的问题
        Integer place_count = 2;
        NotificationPostMapper notificationPostMapper = new NotificationPostMapper();
        NotificationUserPostMapper notificationUserPostMapper = new NotificationUserPostMapper();
        for(String teamMateId:teamMateUserId)
        {
            boolean check = false;
            for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUserList)
            {
                if(Objects.equals(activityTypeFourUser.getUserId(), teamMateId) && activityTypeFourUser.getStatus() == 5 && Objects.equals(activityTypeFourPost.getId(), activityTypeFourUser.getActivityTypeFourId()))
                {
                    check = true;
                    break;
                }
            }
            if(check)
            {
                place_count++;
                continue;
            }
            else {
                ActivityTypeFourUserPostMapper postMapper = new ActivityTypeFourUserPostMapper();
                postMapper.activityTypeFourId = activityTypeFourPost.getId();
                postMapper.userId = teamMateId;
                postMapper.status = 0;
                postMapper.place = place_count;
                place_count++;
                activityTypeFourUserService.postMapping(postMapper);

                notificationPostMapper.title = "组队邀请";//
                notificationPostMapper.type = 10;
                notificationPostMapper.content = activityTypeFourPost.getId();//队伍Id
                notificationPostMapper.relationId = activityTypeFourPost.getId();//content里面的理论上可以去掉了
                Notification notification = notificationService.post(notificationPostMapper.buildEntity());
                notificationUserPostMapper.notificationId = notification.getId();
                notificationUserPostMapper.type = 1;
                notificationUserPostMapper.status = 0;
                notificationUserPostMapper.userId = teamMateId;
                notificationUserPostMapper.senderId = leaderId;
                notificationUserService.postMapping(notificationUserPostMapper);
            }
        }
        return ActivityTypeFourDetailMapper.buildMap(activityTypeFourPost);
    }

    public List<Map<String, Object>> getTeamsInfo(Integer periods, String userId, String departmentId, Long page, Integer rows)
    {
        ActivityTypeFourFilterMapper activityTypeFourFilterMapper = new ActivityTypeFourFilterMapper();
        ActivityTypeFourPeriodsFilterMapper activityTypeFourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
        activityTypeFourPeriodsFilterMapper.periodsNumber = periods;
        List<ActivityTypeFourPeriods> activityTypeFourPeriodsList = activityTypeFourPeriodsService.getListByFilter(activityTypeFourPeriodsFilterMapper);

        activityTypeFourFilterMapper.periodsNumber = periods;
        activityTypeFourFilterMapper.statusIn = new ArrayList<>();
        activityTypeFourFilterMapper.statusIn.add(3);
        activityTypeFourFilterMapper.statusIn.add(5);
        activityTypeFourFilterMapper.orderBy = new ArrayList<>();
        activityTypeFourFilterMapper.orderBy.add("groupPoint desc");

        Long count = (long) -1;//手动分页计数
        if(departmentId == null && userId == null && page != null && rows != null)//意味着不需要手动分页
        {
            activityTypeFourFilterMapper.page = page;
            activityTypeFourFilterMapper.rows = rows;
        }
        else if(page != null && rows != null) {
            count = (page - 1) * rows;
        }
        //所有符合条件的活动4
        List<ActivityTypeFour> activityTypeFourAllList = activityTypeFourService.getListByFilter(activityTypeFourFilterMapper);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for(ActivityTypeFour activityTypeFour:activityTypeFourAllList)
        {
            boolean test = true;
            ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
            activityTypeFourUserFilterMapper.activityTypeFourId = activityTypeFour.getId();
            if(activityTypeFour.getStatus() == 3)
            {
                activityTypeFourUserFilterMapper.statusIn = Collections.singletonList(5);
            }
            else if(activityTypeFour.getStatus() == 5)
            {
                activityTypeFourUserFilterMapper.statusIn = Collections.singletonList(6);
            }
            List<ActivityTypeFourUser> activityTypeFourUsers = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper);
            if(departmentId != null)
            {
                List<String> departmentIds = new ArrayList<>();
                for(ActivityTypeFourUser activityTypeFourUser : activityTypeFourUsers)
                {
                    departmentIds.add(userService.getByPK(activityTypeFourUser.getUserId()).getDepartmentId());
                }
                if(!departmentIds.contains(departmentId))
                {
                    test = false;
                }
            }
            if(userId != null)
            {
                List<String> userIds = activityTypeFourUsers.stream().map(activityTypeFourUser -> activityTypeFourUser.getUserId()).collect(Collectors.toList());
                if(!userIds.contains(userId))
                {
                    test = false;
                }
            }
            if(test)
            {   //count==-1意味着没有分页的要求
                if(count == -1) {
                    ActivityTypeFourTeamMapper activityTypeFourTeamMapper = new ActivityTypeFourTeamMapper();
                    activityTypeFourTeamMapper.userList = new ArrayList<>();
                    activityTypeFourTeamMapper.id = activityTypeFour.getId();
                    activityTypeFourTeamMapper.name = activityTypeFour.getName();
                    activityTypeFourTeamMapper.groupDate = activityTypeFour.getGroupDate();
                    activityTypeFourTeamMapper.groupPoint = activityTypeFour.getGroupPoint();
                    for (ActivityTypeFourUser activityTypeFourUser1 : activityTypeFourUsers) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("user", UserReturnMapper.buildMapper(userService.getByPK(activityTypeFourUser1.getUserId())));
                        List<Point> points = pointExtendsService.getUserTotalPointWithTime(activityTypeFourUser1.getUserId(), activityTypeFourPeriodsList.get(0).getBeginDate(), activityTypeFourPeriodsList.get(0).getEndDate());
                        int totalPoint = 0;
                        for (Point point : points) {
                            totalPoint += point.getPointNumber();
                        }
                        map.put("userTotalPoint", totalPoint);
                        activityTypeFourTeamMapper.userList.add(map);
                    }
                    returnList.add(activityTypeFourTeamMapper.buildMap());
                }
                else
                {
                    if(count > 0)
                    {
                        count--;
                        continue;
                    }
                    else if(returnList.size() < rows)
                    {
                        ActivityTypeFourTeamMapper activityTypeFourTeamMapper = new ActivityTypeFourTeamMapper();
                        activityTypeFourTeamMapper.userList = new ArrayList<>();
                        activityTypeFourTeamMapper.id = activityTypeFour.getId();
                        activityTypeFourTeamMapper.name = activityTypeFour.getName();
                        activityTypeFourTeamMapper.groupDate = activityTypeFour.getGroupDate();
                        activityTypeFourTeamMapper.groupPoint = activityTypeFour.getGroupPoint();
                        for(ActivityTypeFourUser activityTypeFourUser1 : activityTypeFourUsers)
                        {
                            Map<String,Object>map = new HashMap<>();
                            map.put("user", UserReturnMapper.buildMapper(userService.getByPK(activityTypeFourUser1.getUserId())));
                            List<Point> points = pointExtendsService.getUserTotalPointWithTime(activityTypeFourUser1.getUserId(),activityTypeFourPeriodsList.get(0).getBeginDate(), activityTypeFourPeriodsList.get(0).getEndDate());
                            int totalPoint = 0;
                            for(Point point : points)
                            {
                                totalPoint += point.getPointNumber();
                            }
                            map.put("userTotalPoint", totalPoint);
                            activityTypeFourTeamMapper.userList.add(map);
                        }
                        returnList.add(activityTypeFourTeamMapper.buildMap());
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
        return returnList;
    }
}
