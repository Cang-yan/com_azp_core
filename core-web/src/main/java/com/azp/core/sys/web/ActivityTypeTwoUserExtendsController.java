package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivityTypeTwoUserFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoUserUpdateMapper;
import com.azp.core.sys.service.ActivityTypeTwoService;
import com.azp.core.sys.service.ActivityTypeTwoUserService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoUserPostMapper;
import com.azp.core.sys.service.ActivityTypeTwoExtendsService;
import com.azp.core.sys.service.ActivityTypeTwoUserExtendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Api(
        value = "activity_type_two_user",
        tags = "类型2活动-用户管理自定义"
)
@RestController
@RequestMapping("api/sys/activity/type/two/user/extends")
public class ActivityTypeTwoUserExtendsController {
    @Autowired
    private ActivityTypeTwoUserExtendsService activityTypeTwoUserExtendsService;

    @Autowired
    private ActivityTypeTwoUserService activityTypeTwoUserService;
    @AuthGroup("admin")
    @ApiOperation(
            value = "报名活动二",
            notes = "报名活动二"
    )
    @RequestMapping(
            value = "enroll",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> enroll(@RequestBody ActivityTypeTwoUserPostMapper postMapper)
    {
        return Status.successBuilder()
                .addDataValue(activityTypeTwoUserExtendsService.enroll(postMapper))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "取消报名活动二",
            notes = "取消报名活动二"
    )
    @RequestMapping(
            value = "unenroll",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> unenroll(@RequestBody ActivityTypeTwoUserUpdateMapper updateMapper)
    {
        return Status.successBuilder()
                .addDataValue(activityTypeTwoUserExtendsService.unenroll(updateMapper))
                .map();
    }
//修改模型后应该不需要了
    @AuthGroup("admin")
    @ApiOperation(
            value = "获取用户报名信息",
            notes = "获取用户报名信息"
    )
    @RequestMapping(
            value = "enrollInfo",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getEnrollInfo(@RequestParam(value = "userId", required = true)String userId,
                                             @RequestParam(value = "activitySubCategoryId", required = false)String subCategoryId,
                                             @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn)
    {
        ActivityTypeTwoUserFilterMapper activityTypeTwoUserFilterMapper = new ActivityTypeTwoUserFilterMapper();
        activityTypeTwoUserFilterMapper.userId = userId;
        activityTypeTwoUserFilterMapper.statusIn = statusIn;
        return Status.successBuilder()
                .addDataValue(activityTypeTwoUserExtendsService.getEnrollInfo(activityTypeTwoUserFilterMapper, subCategoryId))
                .addDataCount((long) activityTypeTwoUserExtendsService.getEnrollInfo(activityTypeTwoUserFilterMapper, subCategoryId).size())
                .map();
    }
}
