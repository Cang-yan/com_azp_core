package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.UseInfo;
import com.azp.core.sys.model.UseInfoFilterMapper;
import com.azp.core.sys.service.UseInfoService;
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
public class UseInfoFlowRetrieveService {
  @Autowired
  private UseInfoService useInfoService;

  @Flow("FLOW_CODE_USE_INFO_RETRIEVE_BY_PK")
  public UseInfo retrieveByPK(String key) {
    return useInfoService.getByPK(key);
  }

  @Flow("FLOW_CODE_USE_INFO_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return useInfoService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_USE_INFO_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return useInfoService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_USE_INFO_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(UseInfoFilterMapper filterMapper) {
    return useInfoService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_USE_INFO_RETRIEVE_LIST_BY_FILTER")
  public List<UseInfo> retrieveListByFilter(UseInfoFilterMapper filterMapper) {
    return useInfoService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_USE_INFO_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(UseInfoFilterMapper filterMapper) {
    return useInfoService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_USE_INFO_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(UseInfoFilterMapper filterMapper) {
    return useInfoService.getFilterDetailMapList(filterMapper);
  }
}
