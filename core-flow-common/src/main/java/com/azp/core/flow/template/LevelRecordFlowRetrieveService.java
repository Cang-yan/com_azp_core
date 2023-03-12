package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.LevelRecord;
import com.azp.core.sys.model.LevelRecordFilterMapper;
import com.azp.core.sys.service.LevelRecordService;
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
public class LevelRecordFlowRetrieveService {
  @Autowired
  private LevelRecordService levelRecordService;

  @Flow("FLOW_CODE_LEVEL_RECORD_RETRIEVE_BY_PK")
  public LevelRecord retrieveByPK(String key) {
    return levelRecordService.getByPK(key);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return levelRecordService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return levelRecordService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(LevelRecordFilterMapper filterMapper) {
    return levelRecordService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_RETRIEVE_LIST_BY_FILTER")
  public List<LevelRecord> retrieveListByFilter(LevelRecordFilterMapper filterMapper) {
    return levelRecordService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(LevelRecordFilterMapper filterMapper) {
    return levelRecordService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(LevelRecordFilterMapper filterMapper) {
    return levelRecordService.getFilterDetailMapList(filterMapper);
  }
}
