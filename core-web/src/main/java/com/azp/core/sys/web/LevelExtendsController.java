package com.azp.core.sys.web;

import com.azp.core.sys.service.LevelExtendsService;
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
        value = "level",
        tags = "等级管理extends"
)
@RestController
@RequestMapping("api/sys/level/extends")
public class LevelExtendsController {
    @Autowired
    private LevelExtendsService levelExtendsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "获取指定部门排行榜",
            notes = "获取指定部门排行榜"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getLevelList(@RequestParam(value = "departmentIn") ArrayList<String> departmentIn) {
        List<Map<String, Object>> departmentInLevelRecord = levelExtendsService.getDepartmentInLevelRecord(departmentIn);
        return Status.successBuilder()
                .addDataValue(departmentInLevelRecord)
                .addDataCount((long) departmentInLevelRecord.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "获取指定部门指定等级人员",
            notes = "获取指定部门指定等级人员"
    )
    @RequestMapping(
            value = "user",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getLevelUserList(@RequestParam(value = "department") String department, @RequestParam String levelId) {
        List<Map<String, Object>> departmentInLevelUsers = levelExtendsService.getDepartmentInLevelUser(department, levelId);
        return Status.successBuilder()
                .addDataValue(departmentInLevelUsers)
                .addDataCount((long) departmentInLevelUsers.size())
                .map();
    }

}
