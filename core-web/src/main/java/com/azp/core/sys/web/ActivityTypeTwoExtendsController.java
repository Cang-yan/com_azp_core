package com.azp.core.sys.web;

import com.azp.core.sys.service.UserService;
import com.azp.core.sys.web.model.ActivityTypeTwoExtends;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.service.ActivityTypeTwoExtendsService;
import com.azp.core.sys.service.ActivityTypeTwoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

@Api(
        value = "activity_type_two",
        tags = "类型2活动管理自定义"
)
@RestController
@RequestMapping("api/sys/activity/type/two/extends")
public class ActivityTypeTwoExtendsController {
    @Autowired
    private ActivityTypeTwoExtendsService activityTypeTwoExtendsService;
    @Autowired
    private ActivityTypeTwoService activityTypeTwoService;
    @Autowired
    private UserService userService;
    @AuthGroup("admin")
    @ApiOperation(
            value = "按时间顺序获取可见活动二列表",
            notes = "按时间谁许获取可见活动二列表"
    )
    @RequestMapping(
            value = "filter",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getMapList(@RequestParam(required = false) ArrayList<Integer> statusIn,
                                          @RequestParam(value = "userId", required = true) String userId,
                                          @RequestParam(value = "activitySubCategoryId", required = true) String activitySubCategoryId)
    {
        ActivityTypeTwoFilterMapper mapper = new ActivityTypeTwoFilterMapper();
        ArrayList<String> order = new ArrayList<>();
        order.add("begin_date");
        mapper.orderBy = order;
        mapper.statusIn = statusIn;
        mapper.activitySubCategoryId = activitySubCategoryId;
        return Status.successBuilder()
                .addDataValue(activityTypeTwoExtendsService.getFilterMapList(mapper, userId))
                .addDataCount((long) activityTypeTwoExtendsService.getFilterMapList(mapper, userId).size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "下架指定活动",
            notes = "下架指定活动"
    )
    @RequestMapping(
            value = "batch",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteActivity(@RequestParam(value = "activityTypeTwoId", required = true)String activityTypeTwoId)
    {
        return Status.successBuilder()
                .addDataValue(activityTypeTwoExtendsService.deleteActivityTypeTwo(activityTypeTwoId))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "开启心得",
            notes = "开启心得"
    )
    @RequestMapping(
            value = "allow",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> allowStudyNote(@RequestParam(value = "activityTypeTwoId", required = true)String activityTypeTwoId)
    {
        return Status.successBuilder()
                .addDataValue(activityTypeTwoExtendsService.allowStudyNote(activityTypeTwoId))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "活动类型2指定部门",
            notes = "活动类型2指定部门"
    )
    @RequestMapping(
            value = "department",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> post(@RequestBody ActivityTypeTwoExtends activityTypeTwoExtends) {
        return Status.successBuilder()
                .addDataValue(activityTypeTwoExtendsService.post(activityTypeTwoExtends.getDepartments(), activityTypeTwoExtends.getActivityTypeTwoId()))
                .map();
    }
}
