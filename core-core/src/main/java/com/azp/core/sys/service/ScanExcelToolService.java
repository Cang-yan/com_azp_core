package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 8:52
 */
@Service
public class ScanExcelToolService {
    @Autowired
    UserService userService;
    @Autowired
    PointService pointService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationUserService notificationUserService;


    public void postPointAndNotify(String userCode, Integer pointNumber, Integer type, String excelEntityId, ActivityTypeFiveRelationEnum activityTypeFiveRelationEnum) {
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.userCodeIn = Collections.singletonList(userCode);
        List<User> userList = userService.getListByFilter(userFilterMapper);

        if (userList.isEmpty()) return;
        Point point = new Point();
        point.setRelationId(excelEntityId);
        point.setTemplateId(activityTypeFiveRelationEnum.getRelationId());
        point.setType(type);
        point.setUserId(userList.get(0).getId());
        point.setPointNumber(pointNumber);
        point.setGetTime(new Date());//当前的毫秒值创建日期对象
        pointService.post(point);


        NotificationUser notificationUser = new NotificationUser();
        Notification notification = new Notification();
        notification.setTitle("导入结果通知");
        notification.setContent("您的——" + activityTypeFiveRelationEnum.getName() + " 加分项目的积分已成功导入，获得的积分数量为：" + pointNumber);
        notification.setRelationId(excelEntityId);
        notification.setTemplateId(activityTypeFiveRelationEnum.getRelationId());
        notification.setType(type);
        notificationUser.setNotificationId(notificationService.post(notification).getId());
        notificationUser.setType(1);
        UserFilterMapper userFilterMapper2 = new UserFilterMapper();
        userFilterMapper.userCode = userCode;
        List<User> userList2 = userService.getListByFilter(userFilterMapper2);
        if (userList.isEmpty() || userList2.get(0).getId() == null) throw new StatusException("USER_NOT_COMPLETE");
        notificationUser.setUserId(userList.get(0).getId());
        notificationUser.setStatus(0);
        notificationUserService.post(notificationUser);
    }


}
