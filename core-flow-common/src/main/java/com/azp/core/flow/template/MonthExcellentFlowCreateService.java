package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.MonthExcellent;
import com.azp.core.sys.model.MonthExcellentPostMapper;
import com.azp.core.sys.service.MonthExcellentService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class MonthExcellentFlowCreateService {
  @Autowired
  private MonthExcellentService monthExcellentService;

  @Flow("FLOW_CODE_MONTH_EXCELLENT_CREATE")
  public MonthExcellent create(MonthExcellent postEntity) {
    return monthExcellentService.post(postEntity);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_CREATE_LIST")
  public List<MonthExcellent> createList(List<MonthExcellent> postEntities) {
    return monthExcellentService.postList(postEntities);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<MonthExcellentPostMapper> postMappers) {
    return monthExcellentService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_CREATE_MAPPING")
  public Map<String, Object> createMapping(MonthExcellentPostMapper postMapper) {
    return monthExcellentService.postMapping(postMapper);
  }
}
