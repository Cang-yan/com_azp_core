package com.azp.core.sys.web;

import com.azp.core.sys.service.TipsExtendsService;
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
        value = "tips",
        tags = "小贴士管理扩展"
)
@RestController
@RequestMapping("api/sys/tips/extends")
public class TipsExtendsController {
    @Autowired
    private TipsExtendsService tipsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "获取小贴士",
            notes = "获取小贴士"
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
                .addDataValue(tipsService.getRandom())
                .map();
    }

}
