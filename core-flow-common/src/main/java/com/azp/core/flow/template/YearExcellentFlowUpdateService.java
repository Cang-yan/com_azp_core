package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.YearExcellent;
import com.azp.core.sys.model.YearExcellentUpdateMapper;
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
public class YearExcellentFlowUpdateService {
  @Autowired
  private YearExcellentService yearExcellentService;

  @Flow("FLOW_CODE_YEAR_EXCELLENT_UPDATE")
  public YearExcellent update(YearExcellent updateEntity) {
    return yearExcellentService.update(updateEntity);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_UPDATE_LIST")
  public List<YearExcellent> updateList(List<YearExcellent> updateEntities) {
    return yearExcellentService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<YearExcellentUpdateMapper> updateMappers) {
    return yearExcellentService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_YEAR_EXCELLENT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(YearExcellentUpdateMapper updateMapper) {
    return yearExcellentService.updateMapping(updateMapper);
  }
}
