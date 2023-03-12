package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivityTypeSixPostMapper;
import com.azp.core.sys.service.ActivityTypeSixExtendsService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Api(
        value = "activity_type_six_extends",
        tags = "类型6活动管理扩展"
)
@RestController
@RequestMapping("api/sys/activity/type/six/extends")
public class ActivityTypeSixExtendsController {
    @Autowired
    private ActivityTypeSixExtendsService activityTypeSixExtendsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "签到",
            notes = "签到"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> postWithCalculate(@RequestBody ActivityTypeSixPostMapper postMapper) {
        return Status.successBuilder()
                .addDataValue(activityTypeSixExtendsService.postWithPointCalculate(postMapper))
                .map();
    }

}
