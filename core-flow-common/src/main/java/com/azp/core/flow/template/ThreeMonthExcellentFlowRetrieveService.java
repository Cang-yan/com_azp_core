package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ThreeMonthExcellent;
import com.azp.core.sys.model.ThreeMonthExcellentFilterMapper;
import com.azp.core.sys.service.ThreeMonthExcellentService;
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
public class ThreeMonthExcellentFlowRetrieveService {
  @Autowired
  private ThreeMonthExcellentService threeMonthExcellentService;

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_RETRIEVE_BY_PK")
  public ThreeMonthExcellent retrieveByPK(String key) {
    return threeMonthExcellentService.getByPK(key);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return threeMonthExcellentService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return threeMonthExcellentService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ThreeMonthExcellentFilterMapper filterMapper) {
    return threeMonthExcellentService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_RETRIEVE_LIST_BY_FILTER")
  public List<ThreeMonthExcellent> retrieveListByFilter(ThreeMonthExcellentFilterMapper filterMapper) {
    return threeMonthExcellentService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ThreeMonthExcellentFilterMapper filterMapper) {
    return threeMonthExcellentService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ThreeMonthExcellentFilterMapper filterMapper) {
    return threeMonthExcellentService.getFilterDetailMapList(filterMapper);
  }
}
