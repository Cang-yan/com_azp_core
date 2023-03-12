package com.azp.core.sys.web;

import com.azp.core.sys.model.Department;
import com.azp.core.sys.service.DepartmentExtendsService;
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

import java.util.List;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Api(
        value = "departmentExtends",
        tags = "部门管理extends"
)
@RestController
@RequestMapping("api/sys/department/extends")
public class DepartmentExtendsController {
    @Autowired
    private DepartmentExtendsService departmentService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "获取部门列表",
            notes = "获取部门列表"
    )
    @RequestMapping(
            value = "list",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getList() {
        List<Department> departmentList = departmentService.getList();
        return Status.successBuilder()
                .addDataValue(departmentList)
                .addDataCount((long) departmentList.size())
                .map();
    }

}
