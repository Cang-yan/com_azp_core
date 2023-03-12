package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivityTypeFourUserPostMapper;
import com.azp.core.sys.service.ActivityTypeFourUserExtendsService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(
        value = "activity_type_four_user",
        tags = "类型4活动-人管理自定义"
)
@RestController
@RequestMapping("api/sys/activity/type/four/user/extends")
public class ActivityTypeFourUserExtendsController {
    @Autowired
    private ActivityTypeFourUserExtendsService activityTypeFourUserExtendsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "自主退出队伍",
            notes = "自主退出队伍"
    )
    @RequestMapping(
            value = "quit",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> quitGroup(@RequestParam(value = "activityTypeFourId", required = true)String activityId,
                                         @RequestParam(value = "UserId", required = true)String UserId)
    {
        return Status.successBuilder()
                .addDataValue(activityTypeFourUserExtendsService.quitGroup(activityId, UserId))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "接受邀请",
            notes = "接受邀请"
    )
    @RequestMapping(
            value = "accept",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> acceptInvite(@RequestBody ActivityTypeFourUserPostMapper postMapper)
    {
        postMapper.status = 5;
        return Status.successBuilder()
                .addDataValue(activityTypeFourUserExtendsService.acceptInvite(postMapper))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "拒绝邀请",
            notes = "拒绝邀请"
    )
    @RequestMapping(
            value = "refuse",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> refuseInvite(@RequestParam(value = "activityTypeFourId", required = true)String activityId,
                                            @RequestParam(value = "UserId", required = true)String UserId)
    {
        return Status.successBuilder()
                .addDataValue(activityTypeFourUserExtendsService.refuseInvite(activityId, UserId))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "查看用户当前组队信息",
            notes = "查看用户当前组队信息"
    )
    @RequestMapping(
            value = "getPresentTeam",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getPresentTeamInfo(@RequestParam(value = "userId", required = true)String userId)
    {

        return Status.successBuilder()
                .addDataValue(activityTypeFourUserExtendsService.getPresentTeamInfo(userId))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "查看用户历史组队信息",
            notes = "查看用户历史组队信息"
    )
    @RequestMapping(
            value = "getHistoryTeams",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getAllTeamInfo(@RequestParam(value = "userId", required = true)String userId)
    {

        return Status.successBuilder()
                .addDataValue(activityTypeFourUserExtendsService.getAllTeamInfo(userId))
                .addDataCount((long) activityTypeFourUserExtendsService.getAllTeamInfo(userId).size())
                .map();
    }
}
