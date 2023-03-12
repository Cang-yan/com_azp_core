package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardGoodEye;
import com.azp.core.sys.model.AwardGoodEyeUpdateMapper;
import com.azp.core.sys.service.AwardGoodEyeService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardGoodEyeFlowUpdateService {
  @Autowired
  private AwardGoodEyeService awardGoodEyeService;

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_UPDATE")
  public AwardGoodEye update(AwardGoodEye updateEntity) {
    return awardGoodEyeService.update(updateEntity);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_UPDATE_LIST")
  public List<AwardGoodEye> updateList(List<AwardGoodEye> updateEntities) {
    return awardGoodEyeService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<AwardGoodEyeUpdateMapper> updateMappers) {
    return awardGoodEyeService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(AwardGoodEyeUpdateMapper updateMapper) {
    return awardGoodEyeService.updateMapping(updateMapper);
  }
}
