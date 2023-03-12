package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.LevelRecord;
import com.azp.core.sys.model.LevelRecordUpdateMapper;
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
public class LevelRecordFlowUpdateService {
  @Autowired
  private LevelRecordService levelRecordService;

  @Flow("FLOW_CODE_LEVEL_RECORD_UPDATE")
  public LevelRecord update(LevelRecord updateEntity) {
    return levelRecordService.update(updateEntity);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_UPDATE_LIST")
  public List<LevelRecord> updateList(List<LevelRecord> updateEntities) {
    return levelRecordService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<LevelRecordUpdateMapper> updateMappers) {
    return levelRecordService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(LevelRecordUpdateMapper updateMapper) {
    return levelRecordService.updateMapping(updateMapper);
  }
}
