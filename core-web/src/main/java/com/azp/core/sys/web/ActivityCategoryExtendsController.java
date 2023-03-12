package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivityCategoryFilterMapper;
import com.azp.core.sys.service.ActivityCategoryService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Api(
        value = "activity_category_extends",
        tags = "活动大类管理扩展"
)
@RestController
@RequestMapping("api/sys/activity/category/extends")
public class ActivityCategoryExtendsController {
    @Autowired
    private ActivityCategoryService activityCategoryService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "获取实体",
            notes = "获取实体"
    )
    @RequestMapping(
            value = "single/simple",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSimpleMapByPK() {
        return Status.successBuilder()
                .addDataValue(activityCategoryService.getListByFilter(new ActivityCategoryFilterMapper()).stream().filter(activityCategory -> !activityCategory.getId().equals("2")).collect(Collectors.toList()))
                .map();
    }

}
