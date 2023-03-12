package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.MonthExcellent;
import com.azp.core.sys.model.MonthExcellentUpdateMapper;
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
public class MonthExcellentFlowUpdateService {
  @Autowired
  private MonthExcellentService monthExcellentService;

  @Flow("FLOW_CODE_MONTH_EXCELLENT_UPDATE")
  public MonthExcellent update(MonthExcellent updateEntity) {
    return monthExcellentService.update(updateEntity);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_UPDATE_LIST")
  public List<MonthExcellent> updateList(List<MonthExcellent> updateEntities) {
    return monthExcellentService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<MonthExcellentUpdateMapper> updateMappers) {
    return monthExcellentService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(MonthExcellentUpdateMapper updateMapper) {
    return monthExcellentService.updateMapping(updateMapper);
  }
}
