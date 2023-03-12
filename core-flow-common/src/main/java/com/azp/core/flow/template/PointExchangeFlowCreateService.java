package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.PointExchange;
import com.azp.core.sys.model.PointExchangePostMapper;
import com.azp.core.sys.service.PointExchangeService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class PointExchangeFlowCreateService {
  @Autowired
  private PointExchangeService pointExchangeService;

  @Flow("FLOW_CODE_POINT_EXCHANGE_CREATE")
  public PointExchange create(PointExchange postEntity) {
    return pointExchangeService.post(postEntity);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_CREATE_LIST")
  public List<PointExchange> createList(List<PointExchange> postEntities) {
    return pointExchangeService.postList(postEntities);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<PointExchangePostMapper> postMappers) {
    return pointExchangeService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_CREATE_MAPPING")
  public Map<String, Object> createMapping(PointExchangePostMapper postMapper) {
    return pointExchangeService.postMapping(postMapper);
  }
}
