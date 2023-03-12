package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ActivityTypeTwoExtendsService {
    @Autowired
    private ActivityTypeTwoService activityTypeTwoService;

    @Autowired
    private ActivityTypeTwoUserService activityTypeTwoUserService;

    @Autowired
    private ActivityDepartmentSelectService activityDepartmentSelectService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationUserService notificationUserService;
    //返回state

    public ActivityTypeTwo post(List<String> departments, String activityTypeTwoId) {
        ActivityTypeTwo activityTypeTwo = activityTypeTwoService.getByPK(activityTypeTwoId);
        if (activityTypeTwo == null) throw new StatusException("ACTIVITY_NOT_EXIST");
        Notification notification = new Notification();
        notification.setTitle("知识课堂");
        notification.setRelationId(activityTypeTwoId);
        notification.setContent("您有活动" + activityTypeTwo.getName() + "待参加");
        if (Objects.equals(activityTypeTwo.getActivitySubCategoryId(), "3")) {
            notification.setType(4);
        } else if (Objects.equals(activityTypeTwo.getActivitySubCategoryId(), "4")) {
            notification.setType(5);
        }
        Notification notification1 = notificationService.post(notification);
        ActivityDepartmentSelectPostMapper activityDepartmentSelectPostMapper = new ActivityDepartmentSelectPostMapper();
        for (String department : departments) {
            activityDepartmentSelectPostMapper.departmentId = department;
            activityDepartmentSelectPostMapper.relationId = activityTypeTwoId;
            activityDepartmentSelectService.postMapping(activityDepartmentSelectPostMapper);
            UserFilterMapper userFilterMapper = new UserFilterMapper();
            userFilterMapper.departmentId = department;
            List<User> UserList = userService.getListByFilter(userFilterMapper);
            for (User user : UserList) {
                NotificationUser notificationUser = new NotificationUser();
                notificationUser.setUserId(user.getId());
                notificationUser.setNotificationId(notification1.getId());
                notificationUser.setType(1);
                notificationUser.setStatus(0);
                notificationUserService.post(notificationUser);
            }
        }
        return activityTypeTwoService.getByPK(activityTypeTwoId);
    }

    public List<Map<String, Object>> getFilterMapList(ActivityTypeTwoFilterMapper filterMapper, String UserID) {
        String departmentId = userService.getByPK(UserID).getDepartmentId();
        List<Map<String, Object>> entityMapList = new ArrayList<>();
        if (departmentId == null) return entityMapList;
        List<ActivityTypeTwo> ActivityTypeTwoMapList = new ArrayList<>();
        List<ActivityTypeTwo> AllActivityTypeTwoMapList = activityTypeTwoService.getListByFilter(filterMapper);
        ActivityDepartmentSelectFilterMapper activityDepartmentSelectFilterMapper = new ActivityDepartmentSelectFilterMapper();
        activityDepartmentSelectFilterMapper.departmentId = departmentId;
        List<ActivityDepartmentSelect> activityDepartmentSelectList = activityDepartmentSelectService.getListByFilter(activityDepartmentSelectFilterMapper);

        for (ActivityTypeTwo activityTypeTwo : AllActivityTypeTwoMapList) {
            for (ActivityDepartmentSelect activityDepartmentSelect : activityDepartmentSelectList) {
                if (Objects.equals(activityTypeTwo.getId(), activityDepartmentSelect.getRelationId())) {
                    ActivityTypeTwoMapList.add(activityTypeTwo);
                }
            }
        }
        for (ActivityTypeTwo modelEntity : ActivityTypeTwoMapList) {
            entityMapList.add(ActivityTypeTwoDetailMapper.buildMap(modelEntity));
        }
        return entityMapList;
    }

    public Map<String, Object> deleteActivityTypeTwo(String activityTypeTwoId) {
        if (activityTypeTwoService.getByPK(activityTypeTwoId) == null) throw new StatusException("ACTIVITY_NOT_EXIST");
        ActivityTypeTwoUpdateMapper activityTypeTwoUpdateMapper = new ActivityTypeTwoUpdateMapper();
        activityTypeTwoUpdateMapper.id = activityTypeTwoId;
        activityTypeTwoUpdateMapper.status = 2;
        //修改参与人员的状态
        ActivityTypeTwoUserFilterMapper activityTypeTwoUserFilterMapper = new ActivityTypeTwoUserFilterMapper();
        activityTypeTwoUserFilterMapper.activityTypeTwoId = activityTypeTwoId;
        List<ActivityTypeTwoUser> activityTypeTwoUserList = activityTypeTwoUserService.getListByFilter(activityTypeTwoUserFilterMapper);
        for (ActivityTypeTwoUser activityTypeTwoUser : activityTypeTwoUserList) {
            activityTypeTwoUser.setStatus(1);
        }
        activityTypeTwoUserService.updateList(activityTypeTwoUserList);
        return activityTypeTwoService.updateMapping(activityTypeTwoUpdateMapper);
    }

    public ActivityTypeTwo allowStudyNote(String activityTypeTwoId)
    {
        ActivityTypeTwo activityTypeTwo = activityTypeTwoService.getByPK(activityTypeTwoId);
        if(activityTypeTwo == null) throw new StatusException("ACTIVITY_NOT_EXIST");
        if(activityTypeTwo.getStatus() != 2) throw new StatusException("ACTIVITY_NOT_FINISHED");
        activityTypeTwo.setStatus(4);//改变活动的状态

        ActivityTypeTwoUserFilterMapper activityTypeTwoUserFilterMapper = new ActivityTypeTwoUserFilterMapper();
        activityTypeTwoUserFilterMapper.activityTypeTwoId = activityTypeTwoId;
        List<ActivityTypeTwoUser> activityTypeTwoUserList = activityTypeTwoUserService.getListByFilter(activityTypeTwoUserFilterMapper);
        Notification notification = new Notification();
        notification.setTitle("知识课堂");
        notification.setContent("您有活动心得需要填写，请及时提交");
        if (Objects.equals(activityTypeTwo.getActivitySubCategoryId(), "3")) {
            notification.setType(4);
        } else if (Objects.equals(activityTypeTwo.getActivitySubCategoryId(), "4")) {
            notification.setType(5);
        }
        notification.setRelationId(activityTypeTwoId);
        Notification notification1 = notificationService.post(notification);
        for(ActivityTypeTwoUser activityTypeTwoUser : activityTypeTwoUserList)
        {
            if(activityTypeTwoUser.getStatus() == 1)
            {
                NotificationUser notificationUser = new NotificationUser();
                notificationUser.setNotificationId(notification1.getId());
                notificationUser.setUserId(activityTypeTwoUser.getUserId());
                notificationUser.setType(1);
                notificationUser.setStatus(0);
                notificationUserService.post(notificationUser);
            }
        }
        return activityTypeTwoService.update(activityTypeTwo);
    }
}
