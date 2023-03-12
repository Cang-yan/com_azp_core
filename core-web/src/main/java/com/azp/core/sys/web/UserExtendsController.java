package com.azp.core.sys.web;

import com.azp.core.sys.model.UserPostMapper;
import com.azp.core.sys.service.UserExtendsService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Api(
        value = "userExtends",
        tags = "用户管理扩展"
)
@RestController
@RequestMapping("api/sys/user/extends")
public class UserExtendsController {
    @Autowired
    private UserExtendsService userService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "获取用户信息detail",
            notes = "获取用户信息detail"
    )
    @RequestMapping(
            value = "info/detail",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getDetailMapByPK(@RequestParam(value = "id", required = true) String id) {
        return Status.successBuilder()
                .addDataValue(userService.getUserInfoDetail(id))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "发布一组实体",
            notes = "发布一组实体"
    )
    @RequestMapping(
            value = "batch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> postBatch(@RequestBody ArrayList<UserPostMapper> postMappers) {
        return Status.successBuilder()
                .addDataValue(userService.postMappingList(postMappers))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "用户上传头像",
            notes = "用户上传头像"
    )
    @RequestMapping(
            value = "head",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> head(@RequestPart MultipartFile file, @RequestParam String userId) {
        return Status.successBuilder()
                .addDataValue(userService.head(file, userId))
                .map();
    }

}