package com.azp.core.flow;

import cc.eamon.open.flow.config.constants.FlowConstants;
import cc.eamon.open.flow.core.FlowEngine;
import cc.eamon.open.flow.remote.FlowRequest;
import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class FlowEngineService {
  public Map<String, Object> runFlowByFlowId(FlowRequest request) throws Exception {
    // 定义引擎;
    FlowEngine flowEngine = FlowEngine.instance();
    // 注入参数;
    if (request.getParams().size() > 0)
        request.getParams().forEach((key, value) -> flowEngine.register(FlowConstants.SCOPE_DEFAULT, key, value));
    // 运行流程;
    flowEngine.run(request.getFlowId());
    // 取出所需结果;
    Map<String, Object> result = flowEngine.retrieveFlowResults(request.getFlowId());
    // 释放流程引擎;
    flowEngine.release();
    return result;
  }
}
