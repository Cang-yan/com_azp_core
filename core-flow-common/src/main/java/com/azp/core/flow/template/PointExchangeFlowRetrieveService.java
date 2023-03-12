package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.PointExchange;
import com.azp.core.sys.model.PointExchangeFilterMapper;
import com.azp.core.sys.service.PointExchangeService;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class PointExchangeFlowRetrieveService {
  @Autowired
  private PointExchangeService pointExchangeService;

  @Flow("FLOW_CODE_POINT_EXCHANGE_RETRIEVE_BY_PK")
  public PointExchange retrieveByPK(String key) {
    return pointExchangeService.getByPK(key);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return pointExchangeService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return pointExchangeService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(PointExchangeFilterMapper filterMapper) {
    return pointExchangeService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_RETRIEVE_LIST_BY_FILTER")
  public List<PointExchange> retrieveListByFilter(PointExchangeFilterMapper filterMapper) {
    return pointExchangeService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(PointExchangeFilterMapper filterMapper) {
    return pointExchangeService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_POINT_EXCHANGE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(PointExchangeFilterMapper filterMapper) {
    return pointExchangeService.getFilterDetailMapList(filterMapper);
  }
}
