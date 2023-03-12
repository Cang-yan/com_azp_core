package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.PointStore;
import com.azp.core.sys.model.PointStoreFilterMapper;
import com.azp.core.sys.service.PointStoreService;
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
public class PointStoreFlowRetrieveService {
  @Autowired
  private PointStoreService pointStoreService;

  @Flow("FLOW_CODE_POINT_STORE_RETRIEVE_BY_PK")
  public PointStore retrieveByPK(String key) {
    return pointStoreService.getByPK(key);
  }

  @Flow("FLOW_CODE_POINT_STORE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return pointStoreService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_POINT_STORE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return pointStoreService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_POINT_STORE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(PointStoreFilterMapper filterMapper) {
    return pointStoreService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_POINT_STORE_RETRIEVE_LIST_BY_FILTER")
  public List<PointStore> retrieveListByFilter(PointStoreFilterMapper filterMapper) {
    return pointStoreService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_POINT_STORE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(PointStoreFilterMapper filterMapper) {
    return pointStoreService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_POINT_STORE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(PointStoreFilterMapper filterMapper) {
    return pointStoreService.getFilterDetailMapList(filterMapper);
  }
}
