package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivityTypeOneFilterMapper;
import com.azp.core.sys.service.ActivityTypeOneUserExtendsService;
import com.azp.core.sys.web.model.ActivityTypeOneUserExtends;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Api(
        value = "activity_type_one_user_extends",
        tags = "类型1活动-用户管理扩展"
)
@RestController
@RequestMapping("api/sys/activity/type/one/user/extends")
public class ActivityTypeOneUserExtendsController {
    @Autowired
    private ActivityTypeOneUserExtendsService activityTypeOneUserExtendsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "活动类型1按部门指定参加人员",
            notes = "活动类型1按部门指定参加人员"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> post(@RequestBody ActivityTypeOneUserExtends activityTypeOneUserExtends) {
        return Status.successBuilder()
                .addDataValue(activityTypeOneUserExtendsService.post(activityTypeOneUserExtends.getDepartments(), activityTypeOneUserExtends.getActivityTypeOneId()))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "活动类型1查询用户参与活动列表",
            notes = "活动类型1查询用户参与活动列表"
    )
    @RequestMapping(
            value = "user",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getActivityUserList(@RequestParam(required = false) ArrayList<Integer> statusIn,
                                                   @RequestParam String userId,
                                                   @RequestParam String activitySubCategoryId) {
        List<Map<String, Object>> userActivityList = activityTypeOneUserExtendsService.getUserActivityList(statusIn, userId, activitySubCategoryId);
        return Status.successBuilder()
                .addDataValue(userActivityList)
                .addDataCount((long) userActivityList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "活动类型1查询用户活动可见列表",
            notes = "活动类型1查询用户活动可见列表"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getActivityList(@RequestParam(required = false) ArrayList<Integer> statusIn,
                                               @RequestParam String userId, @RequestParam String activitySubCategoryId) {
        ActivityTypeOneFilterMapper mapper = new ActivityTypeOneFilterMapper();
        ArrayList<String> order = new ArrayList<>();
        order.add("begin_date");
        mapper.orderBy = order;
        mapper.activitySubCategoryId = activitySubCategoryId;
        mapper.statusIn = statusIn;
        List<Map<String, Object>> filterMapList = activityTypeOneUserExtendsService.getFilterMapList(mapper, userId);
        return Status.successBuilder()
                .addDataValue(filterMapList)
                .addDataCount((long) filterMapList.size())
                .map();
    }

}
