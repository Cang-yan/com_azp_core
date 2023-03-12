package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.YearExcellent;
import com.azp.core.sys.model.YearExcellentPostMapper;
import com.azp.core.sys.service.YearExcellentService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class YearExcellentFlowCreateService {
  @Autowired
  private YearExcellentService yearExcellentService;

  @Flow("FLOW_CODE_YEAR_EXCELLENT_CREATE")
  public YearExcellent create(YearExcellent postEntity) {
    return yearExcellentService.post(postEntity);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_CREATE_LIST")
  public List<YearExcellent> createList(List<YearExcellent> postEntities) {
    return yearExcellentService.postList(postEntities);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<YearExcellentPostMapper> postMappers) {
    return yearExcellentService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_CREATE_MAPPING")
  public Map<String, Object> createMapping(YearExcellentPostMapper postMapper) {
    return yearExcellentService.postMapping(postMapper);
  }
}
