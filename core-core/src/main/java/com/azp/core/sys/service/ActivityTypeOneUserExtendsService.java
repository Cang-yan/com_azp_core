package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class ActivityTypeOneUserExtendsService {

    @Autowired
    private ActivityTypeOneUserService activityTypeOneUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private ActivityTypeOneService typeOneService;

    @Autowired
    private ActivityDepartmentSelectService activityDepartmentSelectService;

    @Autowired
    private ActivityTypeOneService activityTypeOneService;

    /**
     * 批量选部门
     *
     * @param departments
     * @param activityTypeOneId
     * @return
     */
    public List<String> post(List<String> departments, String activityTypeOneId) {
        if (departments.isEmpty()) return new ArrayList<>();
        ActivityTypeOne activityTypeOne = typeOneService.getByPK(activityTypeOneId);
        if (activityTypeOne == null) throw new StatusException("ACTIVITY_NOT_EXIST");
        List<String> userList = new ArrayList<>();
        for (String department : departments) {
            UserFilterMapper userFilterMapper = new UserFilterMapper();
            userFilterMapper.departmentId = department;
            userList.addAll(userService.getListByFilter(userFilterMapper).stream().map(User::getId).collect(Collectors.toList()));
            // 可见性
            ActivityDepartmentSelect activityDepartmentSelect = new ActivityDepartmentSelect();
            activityDepartmentSelect.setDepartmentId(department);
            activityDepartmentSelect.setRelationId(activityTypeOneId);
            activityDepartmentSelectService.post(activityDepartmentSelect);
        }
        Date date = new Date();
        // 通知
        Notification notification = new Notification();
        notification.setTitle("知识课堂");
        notification.setRelationId(activityTypeOneId);
        notification.setContent("您有活动" + activityTypeOne.getName() + "待参加");
        notification.setType("1".equals(activityTypeOne.getActivitySubCategoryId()) ? 2 : 3);
        Notification notification1 = notificationService.post(notification);
        for (String userId : userList) {
            ActivityTypeOneUser activityTypeOneUser = new ActivityTypeOneUser();
            activityTypeOneUser.setUserId(userId);
            activityTypeOneUser.setActivityTypeOneId(activityTypeOneId);
            activityTypeOneUser.setBeginDate(date);
            activityTypeOneUser.setStatus(0);
            activityTypeOneUserService.post(activityTypeOneUser);
            NotificationUser notificationUser = new NotificationUser();
            notificationUser.setUserId(userId);
            notificationUser.setNotificationId(notification1.getId());
            notificationUser.setType(1);
            notificationUser.setStatus(0);
            notificationUserService.post(notificationUser);
        }
        // 更新参加人数
        activityTypeOne.setParticipantsNumber(userList.size());
        activityTypeOneService.update(activityTypeOne);
        return userList;
    }

    public List<Map<String, Object>> getUserActivityList(List<Integer> statusIn, String userId, String activitySubCategoryId) {
        ActivityTypeOneUserFilterMapper activityTypeOneUserFilterMapper = new ActivityTypeOneUserFilterMapper();
        activityTypeOneUserFilterMapper.userId = userId;
        activityTypeOneUserFilterMapper.statusIn = statusIn;
        List<ActivityTypeOneUser> userServiceListByFilter = activityTypeOneUserService.getListByFilter(activityTypeOneUserFilterMapper);
        return userServiceListByFilter.stream().filter((u) -> {
            String activityTypeOneId = u.getActivityTypeOneId();
            ActivityTypeOne activityTypeOne = typeOneService.getByPK(activityTypeOneId);
            return activityTypeOne != null && activityTypeOne.getActivitySubCategoryId().equals(activitySubCategoryId);
        }).map(ActivityTypeOneUser::getId).map(activityTypeOneUserService::getDetailMapByPK).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getFilterMapList(ActivityTypeOneFilterMapper filterMapper, String userId) {
        String departmentId = userService.getByPK(userId).getDepartmentId();
        List<ActivityTypeOne> activityTypeOneList = typeOneService.getListByFilter(filterMapper);
        List<ActivityTypeOne> typeOneList = activityTypeOneList.stream().filter(activityTypeOne -> {
            String activityTypeOneId = activityTypeOne.getId();
            ActivityDepartmentSelectFilterMapper activityDepartmentSelectFilterMapper = new ActivityDepartmentSelectFilterMapper();
            activityDepartmentSelectFilterMapper.departmentId = departmentId;
            activityDepartmentSelectFilterMapper.relationId = activityTypeOneId;
            List<ActivityDepartmentSelect> activityDepartmentSelectList = activityDepartmentSelectService.getListByFilter(activityDepartmentSelectFilterMapper);
            return !CollectionUtils.isEmpty(activityDepartmentSelectList);
        }).collect(Collectors.toList());
        List<Map<String, Object>> entityMapList = new ArrayList<>();
        for (ActivityTypeOne modelEntity : typeOneList) {
            entityMapList.add(ActivityTypeOneDetailMapper.buildMap(modelEntity));
        }
        return entityMapList;
    }

}
