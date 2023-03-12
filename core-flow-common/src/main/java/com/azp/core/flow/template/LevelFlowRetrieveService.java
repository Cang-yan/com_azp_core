package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Level;
import com.azp.core.sys.model.LevelFilterMapper;
import com.azp.core.sys.service.LevelService;
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
public class LevelFlowRetrieveService {
  @Autowired
  private LevelService levelService;

  @Flow("FLOW_CODE_LEVEL_RETRIEVE_BY_PK")
  public Level retrieveByPK(String key) {
    return levelService.getByPK(key);
  }

  @Flow("FLOW_CODE_LEVEL_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return levelService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_LEVEL_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return levelService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_LEVEL_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(LevelFilterMapper filterMapper) {
    return levelService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_LEVEL_RETRIEVE_LIST_BY_FILTER")
  public List<Level> retrieveListByFilter(LevelFilterMapper filterMapper) {
    return levelService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_LEVEL_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(LevelFilterMapper filterMapper) {
    return levelService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_LEVEL_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(LevelFilterMapper filterMapper) {
    return levelService.getFilterDetailMapList(filterMapper);
  }
}
