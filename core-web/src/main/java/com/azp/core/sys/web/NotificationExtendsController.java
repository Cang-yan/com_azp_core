package com.azp.core.sys.web;

import com.azp.core.sys.service.NotificationExtendsService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/15 8:57 上午
 **/
@Api(
        value = "notification",
        tags = "通知管理"
)
@RestController
@RequestMapping("api/sys/notification/extends")
public class NotificationExtendsController {

    @Autowired
    private NotificationExtendsService notificationExtendsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "按用户和类型获取通知列表",
            notes = "按用户和类型获取通知列表"
    )
    @RequestMapping(
            value = "filter/detail",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getDetailMapByPK(@RequestParam(value = "userId", required = true) String userId,
                                                @RequestParam(value = "typeIn", required = true) ArrayList<Integer> typeIn,
                                                @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn) {
        return Status.successBuilder()
                .addDataValue(notificationExtendsService.getNotificationListByUserIdAndType(userId, typeIn, statusIn))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "按用户和类型获取通知列表",
            notes = "按用户和类型获取通知列表"
    )
    @RequestMapping(
            value = "filter/detail/type23",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getDetailMapByPKFor2Or3(@RequestParam(value = "userId", required = true) String userId,
                                                @RequestParam(value = "typeIn", required = true) ArrayList<Integer> typeIn,
                                                @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn) {
        return Status.successBuilder()
                .addDataValue(notificationExtendsService.getNotificationListByUserIdAndType2Or3(userId, typeIn, statusIn))
                .map();
    }

}
