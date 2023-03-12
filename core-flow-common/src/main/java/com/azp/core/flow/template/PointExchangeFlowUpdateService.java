package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.PointExchange;
import com.azp.core.sys.model.PointExchangeUpdateMapper;
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
public class PointExchangeFlowUpdateService {
  @Autowired
  private PointExchangeService pointExchangeService;

  @Flow("FLOW_CODE_POINT_EXCHANGE_UPDATE")
  public PointExchange update(PointExchange updateEntity) {
    return pointExchangeService.update(updateEntity);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_UPDATE_LIST")
  public List<PointExchange> updateList(List<PointExchange> updateEntities) {
    return pointExchangeService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<PointExchangeUpdateMapper> updateMappers) {
    return pointExchangeService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(PointExchangeUpdateMapper updateMapper) {
    return pointExchangeService.updateMapping(updateMapper);
  }
}
