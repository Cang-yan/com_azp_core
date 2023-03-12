package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import com.azp.core.sys.model.*;
import com.azp.core.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@TaskScheduleComponent
public class DepartmentScoreLimitSchedule {

    @Autowired
    private PointService pointService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentPointService departmentPointService;

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private NotificationService notificationService;
    @Task(cron = " 0 0/5 * * * ?")
    @Distributed
    public Runnable refresh()
    {
        return () ->{

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE , -1);

            DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
            List<Department> departmentList = departmentService.getListByFilter(departmentFilterMapper);
            for(Department department:departmentList)
            {
                //计算积分之和
                UserFilterMapper userFilterMapper = new UserFilterMapper();
                userFilterMapper.departmentId = department.getId();
                List<User> userList = userService.getListByFilter(userFilterMapper);
                int totalScore = 0;
                for(User user: userList)
                {
                    PointFilterMapper pointFilterMapper = new PointFilterMapper();
                    pointFilterMapper.userId = user.getId();
                    List<Point> pointList = pointService.getListByFilter(pointFilterMapper);
                    for(Point point : pointList)
                    {
                        if(point.getPointNumber() > 0)
                        {
                            totalScore += point.getPointNumber();
                        }
                    }
                }
                DepartmentPoint departmentPoint = new DepartmentPoint();

                if(departmentPointService.getByPK(department.getDepartmentPointId()) == null)
                {
                    DepartmentPointPostMapper departmentPointPostMapper = new DepartmentPointPostMapper();
                    departmentPointPostMapper.departmentId = department.getId();
                    departmentPointPostMapper.pointLimit = 2000;
                    departmentPointPostMapper.pointLegacy = 2000 - totalScore;
                    departmentPoint = departmentPointService.post(departmentPointPostMapper.buildEntity());

                    department.setDepartmentPointId(departmentPoint.getId());
                    departmentService.update(department);
                }
                else
                {//更新
                    departmentPoint = departmentPointService.getByPK(department.getDepartmentPointId());
                    departmentPoint.setPointLegacy(departmentPoint.getPointLimit() - totalScore);
                    departmentPointService.update(departmentPoint);
                }
                if(departmentPoint.getPointLegacy() < 1000)//限额减积分和
                {
                    //检测是否需要发通知
                    NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();
                    notificationUserFilterMapper.userId = "root";
                    notificationUserFilterMapper.statusIn = Collections.singletonList(0);
                    notificationUserFilterMapper.typeIn = Collections.singletonList(11);//通知类型11
                    notificationUserFilterMapper.gmtCreateFrom = calendar.getTimeInMillis();
                    List<NotificationUser> notificationUserList = notificationUserService.getListByFilter(notificationUserFilterMapper);
                    boolean notificationSended = false;
                    for(NotificationUser notificationUser : notificationUserList)
                    {
                        if(Objects.equals(notificationService.getByPK(notificationUser.getNotificationId()).getRelationId(), department.getId()))
                        {
                            notificationSended = true;
                            break;
                        }
                    }
                    if(!notificationSended)//说明近期没有发过这样的通知或者通知已经被阅读
                    {
                        //发出通知
                        Notification notification = new Notification();
                        notification.setType(11);
                        notification.setRelationId(department.getId());//这里把relationId设置为部门的Id
                        notification.setTitle("系统提示");
                        notification.setContent(department.getName() + "员工积分已接近限值");
                        Notification notification1 = notificationService.post(notification);

                        NotificationUser notificationUser = new NotificationUser();
                        notificationUser.setUserId("root");
                        notificationUser.setNotificationId(notification1.getId());
                        notificationUser.setStatus(0);
                        notificationUser.setType(1);
                        notificationUserService.post(notificationUser);
                    }
                }
            }
        };
    }
}
