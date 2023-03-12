package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSnowHeat;
import com.azp.core.sys.model.AwardSnowHeatUpdateMapper;
import com.azp.core.sys.service.AwardSnowHeatService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardSnowHeatFlowUpdateService {
  @Autowired
  private AwardSnowHeatService awardSnowHeatService;

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_UPDATE")
  public AwardSnowHeat update(AwardSnowHeat updateEntity) {
    return awardSnowHeatService.update(updateEntity);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_UPDATE_LIST")
  public List<AwardSnowHeat> updateList(List<AwardSnowHeat> updateEntities) {
    return awardSnowHeatService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<AwardSnowHeatUpdateMapper> updateMappers) {
    return awardSnowHeatService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(AwardSnowHeatUpdateMapper updateMapper) {
    return awardSnowHeatService.updateMapping(updateMapper);
  }
}
