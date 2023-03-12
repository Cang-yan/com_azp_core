package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardRainAid;
import com.azp.core.sys.model.AwardRainAidUpdateMapper;
import com.azp.core.sys.service.AwardRainAidService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardRainAidFlowUpdateService {
  @Autowired
  private AwardRainAidService awardRainAidService;

  @Flow("FLOW_CODE_AWARD_RAIN_AID_UPDATE")
  public AwardRainAid update(AwardRainAid updateEntity) {
    return awardRainAidService.update(updateEntity);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_UPDATE_LIST")
  public List<AwardRainAid> updateList(List<AwardRainAid> updateEntities) {
    return awardRainAidService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<AwardRainAidUpdateMapper> updateMappers) {
    return awardRainAidService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(AwardRainAidUpdateMapper updateMapper) {
    return awardRainAidService.updateMapping(updateMapper);
  }
}
