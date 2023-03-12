package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Level;
import com.azp.core.sys.model.LevelPostMapper;
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
public class LevelFlowCreateService {
  @Autowired
  private LevelService levelService;

  @Flow("FLOW_CODE_LEVEL_CREATE")
  public Level create(Level postEntity) {
    return levelService.post(postEntity);
  }

  @Flow("FLOW_CODE_LEVEL_CREATE_LIST")
  public List<Level> createList(List<Level> postEntities) {
    return levelService.postList(postEntities);
  }

  @Flow("FLOW_CODE_LEVEL_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<LevelPostMapper> postMappers) {
    return levelService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_LEVEL_CREATE_MAPPING")
  public Map<String, Object> createMapping(LevelPostMapper postMapper) {
    return levelService.postMapping(postMapper);
  }
}
