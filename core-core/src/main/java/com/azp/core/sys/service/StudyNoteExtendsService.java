package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class StudyNoteExtendsService {
    @Autowired
    private StudyNoteService studyNoteService;
    @Autowired
    private ActivityTypeTwoUserService activityTypeTwoUserService;
    @Autowired
    private PointService pointService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationUserService notificationUserService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ActivityTypeTwoService activityTypeTwoService;

    public Map<String, Object> postTypeTwo(StudyNotePostMapper postMapper) {
        ActivityTypeTwoUserUpdateMapper activityTypeTwoUserUpdateMapper = new ActivityTypeTwoUserUpdateMapper();
        //更新用户状态
        activityTypeTwoUserUpdateMapper.status = 3;
        activityTypeTwoUserUpdateMapper.id = postMapper.activityTypeTwoUserId;
        activityTypeTwoUserUpdateMapper.endDate = new Date().getTime();
        activityTypeTwoUserService.updateMapping(activityTypeTwoUserUpdateMapper);

        Notification notification = new Notification();
        notification.setType(11);
        notification.setRelationId(postMapper.activityTypeTwoUserId);
        notification.setTitle("心得审核");
        notification.setContent("员工" + userService.getByPK(postMapper.userId).getName() + "提交的心得待审核");
        Notification notification1 = notificationService.post(notification);

        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setUserId("root");
        notificationUser.setNotificationId(notification1.getId());
        notificationUser.setStatus(0);
        notificationUser.setType(1);
        notificationUserService.post(notificationUser);
        return studyNoteService.postMapping(postMapper);
    }

    public ActivityTypeTwoUser checkTypeTwo(String studyNotesId) {
        StudyNote studyNote = studyNoteService.getByPK(studyNotesId);
        //更新用户状态
        ActivityTypeTwoUser activityTypeTwoUser = activityTypeTwoUserService.getByPK(studyNote.getActivityTypeTwoUserId());
        if (activityTypeTwoUser == null) throw new StatusException("ACTIVITY_NOT_EXIST");
        if (activityTypeTwoUser.getStatus() == 7) throw new StatusException("STUDY_NOTE_CHECKING");
        activityTypeTwoUser.setReviewDate(new Date());
        activityTypeTwoUser.setStatus(7);
        //添加积分记录
        PointPostMapper pointPostMapper = new PointPostMapper();
        pointPostMapper.userId = studyNote.getUserId();
        pointPostMapper.getTime = new Date().getTime();
        pointPostMapper.relationId = activityTypeTwoUser.getId();
        if (Objects.equals(activityTypeTwoService.getByPK(activityTypeTwoUserService.getByPK(studyNote.getActivityTypeTwoUserId()).getActivityTypeTwoId()).getActivitySubCategoryId(), "3")) {
            pointPostMapper.title = "线下培训";
            pointPostMapper.type = 4;
        } else if (Objects.equals(activityTypeTwoService.getByPK(activityTypeTwoUserService.getByPK(studyNote.getActivityTypeTwoUserId()).getActivityTypeTwoId()).getActivitySubCategoryId(), "4")) {
            pointPostMapper.title = "活动竞赛";
            pointPostMapper.type = 5;
        }
        pointPostMapper.pointNumber = activityTypeTwoService.getByPK(activityTypeTwoUser.getActivityTypeTwoId()).getPoint();
        pointService.postMapping(pointPostMapper);
        activityTypeTwoUser.setPoint(pointPostMapper.pointNumber);
        return activityTypeTwoUserService.update(activityTypeTwoUser);
    }
}
