package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivityTypeFourPostMapper;
import com.azp.core.sys.service.ActivityTypeFourExtendsService;
import com.azp.core.sys.service.ActivityTypeFourService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Api(
        value = "activity_type_four_user",
        tags = "类型4活动管理自定义"
)
@RestController
@RequestMapping("api/sys/activity/type/four")
public class ActivityTypeFourExtendsController {
    @Autowired
    private ActivityTypeFourService activityTypeFourService;

    @Autowired
    private ActivityTypeFourExtendsService activityTypeFourExtendsService;

    @Value("${activity.four.start}")
    private String startDate;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @AuthGroup("admin")
    @ApiOperation(
            value = "发布邀请",
            notes = "发布邀请"
    )
    @RequestMapping(
            value = "sendInvite",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> sendInvite(@RequestParam(value = "name", required = true)String name,
                                          @RequestParam(value = "teamMatesUserId", required = true)ArrayList<String> teamMateUserId
                                          )
    {
        ActivityTypeFourPostMapper activityTypeFourPostMapper = new ActivityTypeFourPostMapper();
        activityTypeFourPostMapper.name = name;
        activityTypeFourPostMapper.status = 1;
        activityTypeFourPostMapper.periodsNumber = ActivityTypeFourExtendsService.getPeriodCount();
        return Status.successBuilder()
                .addDataValue(activityTypeFourExtendsService.sendInvite(activityTypeFourPostMapper, teamMateUserId))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "查看可邀请人员",
            notes = "查看可邀请人员"
    )
    @RequestMapping(
            value = "getUsers",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> sendInvite(@RequestParam(value = "UserIds", required = true)ArrayList<String> UserIds)
    {
        return Status.successBuilder()
                .addDataValue(activityTypeFourExtendsService.getUsers(UserIds))
                .addDataCount((long)activityTypeFourExtendsService.getUsers(UserIds).size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "按期查询组队信息",
            notes = "按期查询组队信息"
    )
    @RequestMapping(
            value = "getTeamInfo",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getTeamInfo(@RequestParam(value = "periods", required = true)Integer periods,
                                          @RequestParam(value = "page", required = false)Long page,
                                          @RequestParam(value = "rows", required = false)Integer rows,
                                          @RequestParam(value = "userId", required = false)String userId,
                                          @RequestParam(value = "departmentId", required = false)String departmentId
    ) throws ParseException {
        Date start = sdf.parse(startDate);

        return Status.successBuilder()
                .addDataValue(activityTypeFourExtendsService.getTeamsInfo(periods, userId, departmentId,page,rows))
                .addDataCount((long) activityTypeFourExtendsService.getTeamsInfo(periods,userId,departmentId,page,rows).size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "设置奖励分值",
            notes = "设置奖励分值"
    )
    @RequestMapping(
            value = "setReward",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getTeamInfo(@RequestParam(value = "rewardPoint", required = true)Integer rewardPoint
    )
    {
        ActivityTypeFourExtendsService.setRewardPoint(rewardPoint);
        return Status.successBuilder().map();
    }
}
