package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivityTypeThree;
import com.azp.core.sys.service.*;
import com.azp.core.sys.web.model.ActivityTypeThreeExtends;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/1/14 19:23
 */
@Api(
        value = "activity_type_three_extends",
        tags = "类型3活动管理扩展版"
)
@RestController
@RequestMapping("api/sys/activity/type/three/extends")
public class ActivityTypeThreeExtendsController {
    @Autowired
    private ActivityTypeThreeService activityTypeThreeService;
    @Autowired
    private ActivityTypeThreeExtendsService activityTypeThreeExtendsService;
    @Autowired
    private ActivityTypeThreeImageService activityTypeThreeImageService;
    @Autowired
    private ActivitySubCategoryService activitySubCategoryService;
    @Autowired
    private ActivityDepartmentSelectService activityDepartmentSelectService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;


    @AuthGroup("admin")
    @ApiOperation(
            value = "删除自定义的小类",
            notes = "删除自定义的小类"
    )
    @RequestMapping(
            value = "delete/custom",
            method = RequestMethod.DELETE
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteCustom(@RequestParam(required = true) List<String> id,
                                            @RequestParam(required = true) List<String> subKeyList) {

        return Status.successBuilder()
                .addDataValue(activityTypeThreeExtendsService.deleteSubCategory(id, subKeyList))
                .map();

    }


    @AuthGroup("admin")
    @ApiOperation(
            value = "活动类型3指定部门",
            notes = "活动类型3指定部门"
    )
    @RequestMapping(
            value = "department",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> post(@RequestBody ActivityTypeThreeExtends activityTypeThreeExtends) {
        ArrayList<String> departments = activityTypeThreeExtends.getDepartments();
        if (CollectionUtils.isEmpty(departments) || activityTypeThreeExtends.getActivitySubCategoryId() == null)
            return Status.failedBuilder().map();
        List<Map<String,Object>> resultList = activityTypeThreeExtendsService.changeDepartment(departments, activityTypeThreeExtends.getActivitySubCategoryId(), activityTypeThreeExtends.getType());
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }


    @AuthGroup("admin")
    @ApiOperation(
            value = "审核案例",
            notes = "审核案例"
    )
    @RequestMapping(
            value = "update/single",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> updateSingleActivity(@RequestBody(required = true) List<ActivityTypeThree> updateEntities) {
        return Status.successBuilder()
                .addDataValue(activityTypeThreeExtendsService.examineActivityThree(updateEntities))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "获取过滤列表/含可见性",
            notes = "获取过滤列表/含可见性"
    )
    @RequestMapping(
            value = "filter",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getMapList(@RequestParam(value = "userId", required = true) String userId,
                                          @RequestParam(required = false) List<Integer> statusIn,
                                          @RequestParam(value = "activityCategoryIdIn") ArrayList<String> activityCategoryIdIn) {

      List<ActivitySubCategory> subCategories= activityTypeThreeExtendsService.getSubCategory(userId,statusIn,activityCategoryIdIn);

        return Status.successBuilder()
                .addDataValue(subCategories)
                .addDataCount((long) subCategories.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "评为优秀及其通知",
            notes = "评为优秀及其通知"
    )
    @RequestMapping(
            value = "is/outstanding",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> NotifyGreat(@RequestBody(required = true) List<ActivityTypeThree> updateEntities) {
        return Status.successBuilder()
                .addDataValue(activityTypeThreeExtendsService.makeItGreat(updateEntities))
                .map();


    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "创建活动三及图片条目",
            notes = "创建活动三及图片条目"
    )
    @RequestMapping(
            value = "create/activityThree",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> creatActivityThree(@RequestPart MultipartFile[] files,
                                                  @RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "beginDate", required = false) Date beginDate,
                                                  @RequestParam(value = "endDate", required = false) Date endDate,
                                                  @RequestParam(value = "serial", required = false) String serial,
                                                  @RequestParam(value = "brand", required = false) String brand,
                                                  @RequestParam(value = "description", required = false) String description,
                                                  @RequestParam(value = "point", required = false) Integer point,
                                                  @RequestParam(value = "activitySubCategoryId", required = true) String activitySubCategoryId,
                                                  @RequestParam(value = "status", required = false) Integer status,
                                                  @RequestParam(value = "isOutstanding", required = false) Integer isOutstanding,
                                                  @RequestParam(value = "createUserId", required = true) String createUserId,
                                                  @RequestParam(value = "reviewIdea", required = false) String reviewIdea
    ) {

        ActivityTypeThree activityTypeThree = new ActivityTypeThree();
        activityTypeThree.setName(name);
        activityTypeThree.setBeginDate(beginDate);
        activityTypeThree.setEndDate(endDate);
        activityTypeThree.setSerial(serial);
        activityTypeThree.setBrand(brand);
        activityTypeThree.setDescription(description);
        activityTypeThree.setPoint(point);
        activityTypeThree.setActivitySubCategoryId(activitySubCategoryId);
        activityTypeThree.setStatus(status);
        activityTypeThree.setIsOutstanding(isOutstanding);
        activityTypeThree.setCreateUserId(createUserId);
        activityTypeThree.setReviewIdea(reviewIdea);

        return Status.successBuilder()
                .addDataValue(activityTypeThreeExtendsService.creatActivity(activityTypeThree, files))
                .map();

    }


}
