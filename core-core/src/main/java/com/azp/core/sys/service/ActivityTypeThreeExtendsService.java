package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.status.StatusException;
import com.horsecoder.storage.domain.FileUpdateCoreFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/1/14 8:25
 */
@Service
public class ActivityTypeThreeExtendsService {
    @Autowired
    ActivityTypeThreeService activityTypeThreeService;
    @Autowired
    ActivitySubCategoryService activitySubCategoryService;
    @Autowired
    ActivityTypeThreeImageService activityTypeThreeImageService;
    @Autowired
    PointService pointService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationUserService notificationUserService;
    @Autowired
    ActivityCategoryService activityCategoryService;
    @Autowired
    ActivityDepartmentSelectService activityDepartmentSelectService;
    @Autowired
    UserService userService;
    @Autowired
    FileUpdateCoreFacade fileUpdateCoreFacade;
    @Autowired
    private DepartmentService departmentService;


    //获取可见性列表
    public List<ActivitySubCategory> getSubCategory(String userId, List<Integer> statusIn, List<String> activityCategoryIdIn) {
        User user = userService.getByPK(userId);
        String departmentId = user.getDepartmentId();
        ActivitySubCategoryFilterMapper activitySubCategoryFilterMapper = new ActivitySubCategoryFilterMapper();
        activitySubCategoryFilterMapper.activityCategoryIdIn = activityCategoryIdIn;
        activitySubCategoryFilterMapper.statusIn = statusIn;
        List<ActivitySubCategory> subCategories = activitySubCategoryService.getListByFilter(activitySubCategoryFilterMapper);
        List<ActivitySubCategory> categoryList = subCategories.stream().filter((s) -> {
            ActivityDepartmentSelectFilterMapper mapper = new ActivityDepartmentSelectFilterMapper();
            mapper.relationId = s.getId();
            List<ActivityDepartmentSelect> departmentSelectServiceListByFilter = activityDepartmentSelectService.getListByFilter(mapper);
            if (CollectionUtils.isEmpty(departmentSelectServiceListByFilter)) return true;
            return departmentSelectServiceListByFilter.stream()
                    .map(ActivityDepartmentSelect::getDepartmentId)
                    .collect(Collectors.toList()).contains(departmentId);
        }).collect(Collectors.toList());

        if (categoryList.isEmpty()) return new ArrayList<>();

        List<ActivitySubCategory> categories = new ArrayList<>();
        List<ActivitySubCategory> tempList = new ArrayList<>();
        for (ActivitySubCategory activitySubCategory : categoryList) {
            //把被设置成置顶的和没有被置顶的分别筛出来，设置成置顶的排个序，最后把没有置顶的加在被置顶的后面
            if (activitySubCategory.getOnTop() == 1) {
                categories.add(activitySubCategory);
                sortOnTop(categories);
            } else {
                tempList.add(activitySubCategory);
            }


        }
        categories.addAll(tempList);
        return categories;
    }

    //显示完已经存在的所有小类后，选择删除，根据传过来的小类id对其进行删除，小类表和活动表的数据都要删，删完以后重新查询
    public List<ActivitySubCategory> deleteSubCategory(List<String> keylist, List<String> subKeytoDeleteList) {
        List<String> keytoDeleteList = new ArrayList<>();
        List<ActivityTypeThree> activityTypeThreeList = new ArrayList<>();
        for (String subKey : subKeytoDeleteList) {
            ActivityTypeThreeFilterMapper activityTypeThreeFilterMapper = new ActivityTypeThreeFilterMapper();
            activityTypeThreeFilterMapper.activitySubCategoryId = subKey;
            activityTypeThreeList.addAll(activityTypeThreeService.getListByFilter(activityTypeThreeFilterMapper));
        }
        if (!activityTypeThreeList.isEmpty()) {
            for (ActivityTypeThree activityTypeThree : activityTypeThreeList) {
                keytoDeleteList.add(activityTypeThree.getId());
            }
            activityTypeThreeService.deleteList(keytoDeleteList);
        }
        activitySubCategoryService.deleteList(subKeytoDeleteList);
        return activitySubCategoryService.getListByRelatedActivityCategoryId(keylist);
    }


    //根据传过来的字段新建小类,写进去以后重新查询，绑定到查询的列表里

    public ActivityTypeThree creatActivity(ActivityTypeThree activityTypeThree, MultipartFile[] files) {
        ActivityTypeThree post = activityTypeThreeService.post(activityTypeThree);
        ActivityTypeThreeImage activityTypeThreeImage = new ActivityTypeThreeImage();
        activityTypeThreeImage.setActivityTypeThreeId(activityTypeThree.getId());
        for (MultipartFile file : files) {
            String fileString = fileUpdateCoreFacade.uploadImageFile(file, "azp", null, null);
            activityTypeThreeImage.setUrl(fileString);
            activityTypeThreeImageService.post(activityTypeThreeImage);
        }

        User user = userService.getByPK(activityTypeThree.getCreateUserId());
        Notification notification = new Notification();
        notification.setTitle("活动三待审核案例通知");
        notification.setContent(user.getName()+"创建的名为《"+activityTypeThree.getName()+"》的案例分享等待您的审核，快去看看吧！");
        notification.setRelationId(activityTypeThree.getId());
        notification.setType(11);

        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setNotificationId(notificationService.post(notification).getId());
        notificationUser.setType(1);
        notificationUser.setUserId("root");
        notificationUser.setStatus(0);
        notificationUserService.post(notificationUser);

        return post;
    }


    //编辑就调生产的接口就行，审核完成以后再去送积分
    //修改审核状态、积分数量，
    public List<ActivityTypeThree> examineActivityThree(List<ActivityTypeThree> updateEntities) {
        List<Point> pointList = new ArrayList<>();

        //这里，如果已经审核了，那么就得返回提醒，已经审核，无法操作
        for (ActivityTypeThree entity : updateEntities) {
            ActivityTypeThree activityTypeThree = activityTypeThreeService.getByPK(entity.getId());
            if (activityTypeThree.getStatus() == 7) continue;

            String activitySubCategoryId = entity.getActivitySubCategoryId();
            if (activitySubCategoryId == null) throw new StatusException(722, "该活动子类为空");
            //要确定是案例分享还是术业专攻
            ActivitySubCategory activitySubCategory = activitySubCategoryService.getByPK(activitySubCategoryId);
            if (activitySubCategory == null) throw new StatusException(722, "该活动子类为空");

            Point point = new Point();
            point.setPointNumber(activitySubCategory.getPoint());
            point.setRelationId(entity.getId());
            point.setUserId(entity.getCreateUserId());

            NotificationUser notificationUser = new NotificationUser();


            Notification notification = new Notification();
            notification.setTitle("审核结果通知");
            notification.setContent("您的题目为《" + entity.getName() + "》，编号" + entity.getSerial() + "的案例分享已审核，快去看看吧");
            notification.setRelationId(entity.getId());
            if (activitySubCategory.getActivityCategoryId().equals("3")) {
                point.setType(6);
                notification.setType(6);
                notification.setContent("您的题目为《" + entity.getName() + "》，编号" + entity.getSerial() + "的案例分享已审核，快去看看吧");
            } else if (activitySubCategory.getActivityCategoryId().equals("4")) {
                point.setType(7);
                notification.setType(7);
                notification.setContent("您的题目为《" + entity.getName() + "》，编号" + entity.getSerial() + "的术业专攻已审核，快去看看吧");
            } else continue;
            pointList.add(point);

            notificationUser.setNotificationId(notificationService.post(notification).getId());
            notificationUser.setType(1);
            notificationUser.setUserId(entity.getCreateUserId());
            notificationUser.setStatus(0);
            notificationUserService.post(notificationUser);
            entity.setPoint(activitySubCategory.getPoint());
            entity.setEndDate(new Date());
            entity.setStatus(7);//已审核

        }
        pointService.postList(pointList);
        return activityTypeThreeService.updateList(updateEntities);

    }

    public List<Map<String, Object>> changeDepartment(List<String> departments, String activitySubCategoryId, Integer type) {
        ActivityDepartmentSelectFilterMapper activityDepartmentSelectFilterMapper = new ActivityDepartmentSelectFilterMapper();
        activityDepartmentSelectFilterMapper.relationId = activitySubCategoryId;
        List<ActivityDepartmentSelect> departmentSelectList = activityDepartmentSelectService.getListByFilter(activityDepartmentSelectFilterMapper);
        if (!CollectionUtils.isEmpty(departmentSelectList)) {
            List<String> ids = departmentSelectList.stream().map(ActivityDepartmentSelect::getId).collect(Collectors.toList());
            activityDepartmentSelectService.deleteList(ids);
        }
        List<Department> departmentList = new ArrayList<>();
        for (String department : departments) {
            ActivityDepartmentSelect activityDepartmentSelect = new ActivityDepartmentSelect();
            activityDepartmentSelect.setRelationId(activitySubCategoryId);
            activityDepartmentSelect.setType(type);
            activityDepartmentSelect.setDepartmentId(department);
            activityDepartmentSelectService.post(activityDepartmentSelect);
            departmentList.add(departmentService.getByPK(department));
        }
        return departmentList.stream().map(DepartmentData::buildMap).collect(Collectors.toList());
    }

    //评为优秀的通知
    public List<ActivityTypeThree> makeItGreat(List<ActivityTypeThree> updateEntities) {

        for (ActivityTypeThree entity : updateEntities) {
            if (entity.getActivitySubCategoryId() == null) throw new StatusException(722, "该活动子类为空");

            ActivitySubCategoryFilterMapper activitySubCategoryFilterMapper = new ActivitySubCategoryFilterMapper();
            activitySubCategoryFilterMapper.activityCategoryId = entity.getActivitySubCategoryId();
            List<ActivitySubCategory> activitySubCategoryList = activitySubCategoryService.getListByFilter(activitySubCategoryFilterMapper);


            Notification notification = new Notification();
            notification.setTitle("优秀结果通知");
            notification.setContent("您的题目为《" + entity.getName() + "》，编号" + entity.getSerial() + "的案例分享被评为优秀案例，快去看看吧");
            notification.setRelationId(entity.getId());


            if (activitySubCategoryList.isEmpty()) continue;
            String subName = activitySubCategoryList.get(0).getName();


            if (subName.equals("案例分享")) {
                notification.setType(6);
            } else {
                notification.setType(7);

            }

//            NotificationUser notificationUser = new NotificationUser();
//            notificationUser.setNotificationId(notificationService.post(notification).getId());
//            notificationUser.setType(1);
//            notificationUser.setUserId(entity.getCreateUserId());
//            notificationUser.setStatus(0);
//            notificationUserService.post(notificationUser);


        }


        return activityTypeThreeService.updateList(updateEntities);
    }

    public void sortOnTop(List<ActivitySubCategory> categories) {
        for (int j = 0; j < categories.size() - 1; j++) {
            for (int i = 0; i < categories.size() - 1 - j; i++) {
                if (categories.get(i).getOnTopDate().before(categories.get(i + 1).getOnTopDate())) {
                    ActivitySubCategory temp = categories.get(i);
                    categories.set(i, categories.get(i + 1));
                    categories.set(i + 1, temp);
                }

            }

        }


    }

}
