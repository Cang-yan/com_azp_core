package com.azp.core.flow;

import cc.eamon.open.flow.remote.FlowRequest;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Api(
    value = "flow",
    tags = "流程入口"
)
@RestController
@RequestMapping("api/engine/flow")
public class FlowEngineController {
  @Autowired
  private FlowEngineService flowEngineService;

  @ApiOperation(
      value = "默认流程入口",
      notes = "默认流程入口"
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> flowEntrance(@RequestBody FlowRequest request) throws Exception {
    return Status.successBuilder()
        .addDataValue(flowEngineService.runFlowByFlowId(request))
        .map();
  }
}
