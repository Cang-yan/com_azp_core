package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class NotificationExtendsService {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private ActivityTypeOneService activityTypeOneService;

    @Autowired
    private ActivityTypeOneUserService activityTypeOneUserService;

    public List<Map<String, Object>> getNotificationListByUserIdAndType(String userId, ArrayList<Integer> typeIn, ArrayList<Integer> statusIn) {
        NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();
        notificationUserFilterMapper.userId = userId;
        notificationUserFilterMapper.statusIn = statusIn;
        List<NotificationUser> notificationUserList = notificationUserService.getListByFilter(notificationUserFilterMapper);
        List<NotificationUser> collect = notificationUserList.stream().filter((n) -> {
            Notification notification = notificationService.getByPK(n.getNotificationId());
            if (notification == null) return false;
            return notification.getType() != null && typeIn.contains(notification.getType());
        }).collect(Collectors.toList());
        if (!typeIn.isEmpty() && (typeIn.contains(8) || typeIn.contains(9))) {
            return collect.stream().map(this::getDetailWithRelation).collect(Collectors.toList());
        } else {
            return collect.stream().map((c) -> notificationUserService.getDetailMapByPK(c.getId())).collect(Collectors.toList());
        }
    }

    /**
     * 知识课堂独有的
     *
     * @param userId
     * @param typeIn
     * @param statusIn
     * @return
     */
    public List<Map<String, Object>> getNotificationListByUserIdAndType2Or3(String userId, ArrayList<Integer> typeIn, ArrayList<Integer> statusIn) {
        NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();
        notificationUserFilterMapper.userId = userId;
        notificationUserFilterMapper.statusIn = statusIn;
        List<NotificationUser> notificationUserList = notificationUserService.getListByFilter(notificationUserFilterMapper);
        List<NotificationUser> collect = notificationUserList.stream().filter((n) -> {
            Notification notification = notificationService.getByPK(n.getNotificationId());
            if (notification == null) return false;
            String relationId = notification.getRelationId();
            if (StringUtils.isEmpty(relationId)) return false;
            ActivityTypeOne activityTypeOne = activityTypeOneService.getByPK(relationId);
            if (activityTypeOne == null) return false;
            ActivityTypeOneUserFilterMapper activityTypeOneUserFilterMapper = new ActivityTypeOneUserFilterMapper();
            activityTypeOneUserFilterMapper.activityTypeOneId = activityTypeOne.getId();
            activityTypeOneUserFilterMapper.userId = userId;
            activityTypeOneUserFilterMapper.statusIn = Collections.singletonList(0);
            ActivityTypeOneUser activityTypeOneUser = activityTypeOneUserService.getListByFilter(activityTypeOneUserFilterMapper).stream().findFirst().orElse(null);
            if (activityTypeOneUser == null) return false;
            Date endDate = activityTypeOne.getEndDate();
            if (endDate == null) return true;
            return notification.getType() != null && typeIn.contains(notification.getType()) && new Date().before(endDate);
        }).collect(Collectors.toList());
        return collect.stream().map((c) -> notificationUserService.getDetailMapByPK(c.getId())).collect(Collectors.toList());
    }

    private Map<String, Object> getDetailWithRelation(NotificationUser notificationUser) {
        Map<String, Object> map = notificationUserService.getDetailMapByPK(notificationUser.getId());
        String type = "";
        if (!CollectionUtils.isEmpty(map)) {
            Notification notification = notificationService.getByPK(notificationUser.getNotificationId());
            if (notification != null) {
                type = ActivityTypeFiveRelationEnum.getNameByRelationId(notification.getTemplateId());
            }
        }
        map.put("activity", type);
        return map;
    }

}
