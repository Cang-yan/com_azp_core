package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.MonthExcellent;
import com.azp.core.sys.model.MonthExcellentFilterMapper;
import com.azp.core.sys.service.MonthExcellentService;
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
public class MonthExcellentFlowRetrieveService {
  @Autowired
  private MonthExcellentService monthExcellentService;

  @Flow("FLOW_CODE_MONTH_EXCELLENT_RETRIEVE_BY_PK")
  public MonthExcellent retrieveByPK(String key) {
    return monthExcellentService.getByPK(key);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return monthExcellentService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return monthExcellentService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(MonthExcellentFilterMapper filterMapper) {
    return monthExcellentService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_RETRIEVE_LIST_BY_FILTER")
  public List<MonthExcellent> retrieveListByFilter(MonthExcellentFilterMapper filterMapper) {
    return monthExcellentService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(MonthExcellentFilterMapper filterMapper) {
    return monthExcellentService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(MonthExcellentFilterMapper filterMapper) {
    return monthExcellentService.getFilterDetailMapList(filterMapper);
  }
}
