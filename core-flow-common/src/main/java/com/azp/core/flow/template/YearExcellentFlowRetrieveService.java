package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.YearExcellent;
import com.azp.core.sys.model.YearExcellentFilterMapper;
import com.azp.core.sys.service.YearExcellentService;
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
public class YearExcellentFlowRetrieveService {
  @Autowired
  private YearExcellentService yearExcellentService;

  @Flow("FLOW_CODE_YEAR_EXCELLENT_RETRIEVE_BY_PK")
  public YearExcellent retrieveByPK(String key) {
    return yearExcellentService.getByPK(key);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return yearExcellentService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return yearExcellentService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(YearExcellentFilterMapper filterMapper) {
    return yearExcellentService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_RETRIEVE_LIST_BY_FILTER")
  public List<YearExcellent> retrieveListByFilter(YearExcellentFilterMapper filterMapper) {
    return yearExcellentService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(YearExcellentFilterMapper filterMapper) {
    return yearExcellentService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(YearExcellentFilterMapper filterMapper) {
    return yearExcellentService.getFilterDetailMapList(filterMapper);
  }
}
