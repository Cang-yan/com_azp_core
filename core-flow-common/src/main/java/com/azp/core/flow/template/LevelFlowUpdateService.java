package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Level;
import com.azp.core.sys.model.LevelUpdateMapper;
import com.azp.core.sys.service.LevelService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class LevelFlowUpdateService {
  @Autowired
  private LevelService levelService;

  @Flow("FLOW_CODE_LEVEL_UPDATE")
  public Level update(Level updateEntity) {
    return levelService.update(updateEntity);
  }

  @Flow("FLOW_CODE_LEVEL_UPDATE_LIST")
  public List<Level> updateList(List<Level> updateEntities) {
    return levelService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_LEVEL_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<LevelUpdateMapper> updateMappers) {
    return levelService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_LEVEL_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(LevelUpdateMapper updateMapper) {
    return levelService.updateMapping(updateMapper);
  }
}
