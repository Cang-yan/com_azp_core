package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSnowHeat;
import com.azp.core.sys.model.AwardSnowHeatPostMapper;
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
public class AwardSnowHeatFlowCreateService {
  @Autowired
  private AwardSnowHeatService awardSnowHeatService;

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_CREATE")
  public AwardSnowHeat create(AwardSnowHeat postEntity) {
    return awardSnowHeatService.post(postEntity);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_CREATE_LIST")
  public List<AwardSnowHeat> createList(List<AwardSnowHeat> postEntities) {
    return awardSnowHeatService.postList(postEntities);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<AwardSnowHeatPostMapper> postMappers) {
    return awardSnowHeatService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_CREATE_MAPPING")
  public Map<String, Object> createMapping(AwardSnowHeatPostMapper postMapper) {
    return awardSnowHeatService.postMapping(postMapper);
  }
}
