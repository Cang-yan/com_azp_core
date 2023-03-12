package com.azp.core.sys.web;

import com.azp.core.sys.model.PointExchangeFilterMapper;
import com.azp.core.sys.service.PointExchangeExtendsService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/26 21:58
 */
@Api(
        value = "point_exchange_extends",
        tags = "积分兑换管理扩展版"
)
@RestController
@RequestMapping("api/sys/point/exchange/extends")
public class PointExchangeExtendsController {
    @Autowired
    PointExchangeExtendsService pointExchangeExtendsService;


    @AuthGroup("admin")
    @ApiOperation(
            value = "获取积分兑换记录",
            notes = "获取积分兑换记录"
    )
    @RequestMapping(
            value = "get/record",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getExchangeRecored(@RequestParam(value = "id", required = false) String id,
          @RequestParam(value = "productId", required = false) String productId,
          @RequestParam(value = "pointNum", required = false) Integer pointNum,
          @RequestParam(value = "userId", required = false) String userId,
          @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
          @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
          @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
          @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
          @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
          @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
          @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
        PointExchangeFilterMapper mapper = new PointExchangeFilterMapper();
        mapper.id = id;
        mapper.productId = productId;
        mapper.pointNum = pointNum;
        mapper.userId = userId;
        mapper.gmtUpdateFrom = gmtUpdateFrom;
        mapper.gmtUpdateTo = gmtUpdateTo;
        mapper.gmtCreateFrom = gmtCreateFrom;
        mapper.gmtCreateTo = gmtCreateTo;
        mapper.page = page;
        mapper.rows = rows;
        mapper.orderBy = orderBy;
        return Status.successBuilder()
                .addDataValue(pointExchangeExtendsService.getPointExchangeRecord(mapper))
                .map();
    }




}
