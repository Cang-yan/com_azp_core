package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/3/19 15:21
 */
@Service
public class ActivityFiveExtendsToolService {

    @Autowired
    private UserService userService;

    @Autowired
    private PointService pointService;

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private NotificationService notificationService;


    public void deletePointAndNotifyUser(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        //判空均在方法里
        List<String> targetIdList = getTargetedIdList(activityTypeFiveExtendsDeteleList);
        List<String> userCodeList = getUserCodeList(activityTypeFiveExtendsDeteleList);

        //把userId和userCode绑在一起
        putUserIdInMapList(activityTypeFiveExtendsDeteleList, userCodeList);


        //删积分表，由于relationId和对应积分表的数据是一一对应的，所以只需要通过relationId去查pointId来删除

        pointService.deleteList(getPointIdList(targetIdList));

        //删通知用户表
        //此时的map里面已经添加了userId
        deleteNotifyUser(activityTypeFiveExtendsDeteleList, targetIdList);


    }


    public List<String> getTargetedIdList(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {
        List<String> keyList = new ArrayList<>();

        for (ActivityTypeFiveExtendsDetele activityTypeFiveExtendsDetele : activityTypeFiveExtendsDeteleList) {
            keyList.add(activityTypeFiveExtendsDetele.getId());
        }

        return keyList;

    }

    public List<String> getUserCodeList(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {
        List<String> userCodeList = new ArrayList<>();

        for (ActivityTypeFiveExtendsDetele activityTypeFiveExtendsDetele : activityTypeFiveExtendsDeteleList) {
            userCodeList.add(activityTypeFiveExtendsDetele.getUserCode());
        }

        if (userCodeList.isEmpty()) throw new StatusException(785, "用户信息不完整");

        return userCodeList;
    }

    public List<String> getUserIdList(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {
        List<String> userIdList = new ArrayList<>();

        for (ActivityTypeFiveExtendsDetele activityTypeFiveExtendsDetele : activityTypeFiveExtendsDeteleList) {
            userIdList.add(activityTypeFiveExtendsDetele.getUserId());
        }

        if (userIdList.isEmpty()) throw new StatusException(785, "用户信息不完整");

        return userIdList;
    }

    //把userId绑定进去
    public List<ActivityTypeFiveExtendsDetele> putUserIdInMapList(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList, List<String> userCodeList) {

        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.userCodeIn = userCodeList;
        List<User> userList = userService.getListByFilter(userFilterMapper);

        if (userList.isEmpty()) throw new StatusException(785, "用户信息不完整");

        for (ActivityTypeFiveExtendsDetele activityTypeFiveExtendsDetele : activityTypeFiveExtendsDeteleList) {
            for (User user : userList) {
                if (user.getUserCode().equals(activityTypeFiveExtendsDetele.getUserCode())) {
                    activityTypeFiveExtendsDetele.setUserId(user.getId());

                }


            }


        }

        return activityTypeFiveExtendsDeteleList;


    }

    public List<String> getPointIdList(List<String> targetIdList) {
        PointFilterMapper pointFilterMapper = new PointFilterMapper();
        pointFilterMapper.relationIdIn = targetIdList;

        List<Point> pointList = pointService.getListByFilter(pointFilterMapper);

        if (pointList.isEmpty()) return new ArrayList<>();

        List<String> pointIdList = pointList.stream().map(Point::getId).collect(Collectors.toList());

        return pointIdList;

    }

    //删通知用户表，先绑定notificationId,再根据notificationId和userId同时筛选通知用户表里的数据，避免拉全表
    public void deleteNotifyUser(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList, List<String> targetIdList) {

        NotificationFilterMapper notificationFilterMapper = new NotificationFilterMapper();
        notificationFilterMapper.relationIdIn = targetIdList;
        List<Notification> notificationList = notificationService.getListByFilter(notificationFilterMapper);

        //此时Map里，id,userCode,userId,notification绑在一起了
        for (ActivityTypeFiveExtendsDetele activityTypeFiveExtendsDetele : activityTypeFiveExtendsDeteleList) {
            for (Notification notification : notificationList) {
                if (notification.getRelationId().equals(activityTypeFiveExtendsDetele.getId())) {
                    activityTypeFiveExtendsDetele.setNotificationId(notification.getId());

                }


            }


        }

        NotificationUserFilterMapper notificationUserFilterMapper = new NotificationUserFilterMapper();

        for (ActivityTypeFiveExtendsDetele activityTypeFiveExtendsDetele : activityTypeFiveExtendsDeteleList) {
            notificationUserFilterMapper.notificationId = activityTypeFiveExtendsDetele.getNotificationId();
            notificationUserFilterMapper.userId = activityTypeFiveExtendsDetele.getUserId();
            NotificationUser notificationUser = notificationUserService.getListByFilter(notificationUserFilterMapper).stream().findFirst().orElse(null);
            if (notificationUser == null) continue;
            notificationUserService.delete(notificationUser.getId());

        }


    }

}
