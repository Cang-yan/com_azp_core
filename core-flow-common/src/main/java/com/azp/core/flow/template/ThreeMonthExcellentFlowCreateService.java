package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ThreeMonthExcellent;
import com.azp.core.sys.model.ThreeMonthExcellentPostMapper;
import com.azp.core.sys.service.ThreeMonthExcellentService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ThreeMonthExcellentFlowCreateService {
  @Autowired
  private ThreeMonthExcellentService threeMonthExcellentService;

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_CREATE")
  public ThreeMonthExcellent create(ThreeMonthExcellent postEntity) {
    return threeMonthExcellentService.post(postEntity);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_CREATE_LIST")
  public List<ThreeMonthExcellent> createList(List<ThreeMonthExcellent> postEntities) {
    return threeMonthExcellentService.postList(postEntities);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ThreeMonthExcellentPostMapper> postMappers) {
    return threeMonthExcellentService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_CREATE_MAPPING")
  public Map<String, Object> createMapping(ThreeMonthExcellentPostMapper postMapper) {
    return threeMonthExcellentService.postMapping(postMapper);
  }
}
