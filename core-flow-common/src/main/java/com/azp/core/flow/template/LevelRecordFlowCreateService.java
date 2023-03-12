package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.LevelRecord;
import com.azp.core.sys.model.LevelRecordPostMapper;
import com.azp.core.sys.service.LevelRecordService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class LevelRecordFlowCreateService {
  @Autowired
  private LevelRecordService levelRecordService;

  @Flow("FLOW_CODE_LEVEL_RECORD_CREATE")
  public LevelRecord create(LevelRecord postEntity) {
    return levelRecordService.post(postEntity);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_CREATE_LIST")
  public List<LevelRecord> createList(List<LevelRecord> postEntities) {
    return levelRecordService.postList(postEntities);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<LevelRecordPostMapper> postMappers) {
    return levelRecordService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_CREATE_MAPPING")
  public Map<String, Object> createMapping(LevelRecordPostMapper postMapper) {
    return levelRecordService.postMapping(postMapper);
  }
}
