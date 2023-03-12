package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ThreeMonthExcellent;
import com.azp.core.sys.model.ThreeMonthExcellentUpdateMapper;
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
public class ThreeMonthExcellentFlowUpdateService {
  @Autowired
  private ThreeMonthExcellentService threeMonthExcellentService;

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_UPDATE")
  public ThreeMonthExcellent update(ThreeMonthExcellent updateEntity) {
    return threeMonthExcellentService.update(updateEntity);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_UPDATE_LIST")
  public List<ThreeMonthExcellent> updateList(List<ThreeMonthExcellent> updateEntities) {
    return threeMonthExcellentService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ThreeMonthExcellentUpdateMapper> updateMappers) {
    return threeMonthExcellentService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ThreeMonthExcellentUpdateMapper updateMapper) {
    return threeMonthExcellentService.updateMapping(updateMapper);
  }
}
