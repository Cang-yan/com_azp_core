package com.azp.core.sys.web;

import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.UserPointStatisticsFilterMapper;
import com.azp.core.sys.service.UserPointStaticsExtendsService;
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
 * @description:
 * @author: Yang Xin
 * @time: 2022/1/30 16:07
 */
@Api(
        value = "user_point_statistics_extends",
        tags = "员工积分统计管理-排名"
)
@RestController
@RequestMapping("api/sys/user/point/statistics")
public class UserPointStatisticsExtendsController {
    @Autowired
    UserPointStaticsExtendsService userPointStaticsExtendsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "个人积分排行榜的排名",
            notes = "个人积分排行榜的排名"
    )
    @RequestMapping(
            value = "person/rank",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getRank(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "dateFrom", required = false) Long dateFrom,
            @RequestParam(value = "dateTo", required = false) Long dateTo,
            @RequestParam(value = "departmentName", required = false) String departmentName,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
            @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
            @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
            @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
            @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
            @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
        UserPointStatisticsFilterMapper mapper = new UserPointStatisticsFilterMapper();
        mapper.id = id;
        mapper.dateFrom = dateFrom;
        mapper.dateTo = dateTo;
        mapper.departmentName = departmentName;
        mapper.userId = userId;
        mapper.gmtUpdateFrom = gmtUpdateFrom;
        mapper.gmtUpdateTo = gmtUpdateTo;
        mapper.gmtCreateFrom = gmtCreateFrom;
        mapper.gmtCreateTo = gmtCreateTo;
        mapper.page = page;
        mapper.rows = rows;
        mapper.orderBy = orderBy;
        return Status.successBuilder()
                .addDataValue(userPointStaticsExtendsService.getListByFilter(mapper))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "个人经验排行榜的排名",
            notes = "个人经验排行榜的排名"
    )
    @RequestMapping(
            value = "person/count/rank",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getCountRank(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "dateFrom", required = false) Long dateFrom,
            @RequestParam(value = "dateTo", required = false) Long dateTo,
            @RequestParam(value = "departmentName", required = false) String departmentName,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
            @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
            @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
            @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
            @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows
            ) {
        UserPointStatisticsFilterMapper mapper = new UserPointStatisticsFilterMapper();
        mapper.id = id;
        mapper.dateFrom = dateFrom;
        mapper.dateTo = dateTo;
        mapper.departmentName = departmentName;
        mapper.userId = userId;
        mapper.gmtUpdateFrom = gmtUpdateFrom;
        mapper.gmtUpdateTo = gmtUpdateTo;
        mapper.gmtCreateFrom = gmtCreateFrom;
        mapper.gmtCreateTo = gmtCreateTo;
        mapper.page = page;
        mapper.rows = rows;
        return Status.successBuilder()
                .addDataValue(userPointStaticsExtendsService.getCountRank(mapper))
                .map();
    }



    @AuthGroup("admin")
    @ApiOperation(
            value = "部门筛选的下拉列表",
            notes = "部门筛选的下拉列表"
    )
    @RequestMapping(
            value = "department/dropdown",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getDepartment() {
        List<Department> departmentList = userPointStaticsExtendsService.getDepartmentList();
        return Status.successBuilder()
                .addDataValue(departmentList)
                .addDataCount((long) departmentList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "按积分类型/用户统计",
            notes = "按积分类型/用户统计"
    )
    @RequestMapping(
            value = "user",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getUserPointSumByType(@RequestParam ArrayList<Integer> typeIn,
                                                     @RequestParam String userId) {
        return Status.successBuilder()
                .addDataValue(userPointStaticsExtendsService.pointNumberByType(typeIn, userId))
                .map();
    }


}
