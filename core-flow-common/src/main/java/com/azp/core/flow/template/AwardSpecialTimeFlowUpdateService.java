package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSpecialTime;
import com.azp.core.sys.model.AwardSpecialTimeUpdateMapper;
import com.azp.core.sys.service.AwardSpecialTimeService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardSpecialTimeFlowUpdateService {
  @Autowired
  private AwardSpecialTimeService awardSpecialTimeService;

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_UPDATE")
  public AwardSpecialTime update(AwardSpecialTime updateEntity) {
    return awardSpecialTimeService.update(updateEntity);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_UPDATE_LIST")
  public List<AwardSpecialTime> updateList(List<AwardSpecialTime> updateEntities) {
    return awardSpecialTimeService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<AwardSpecialTimeUpdateMapper> updateMappers) {
    return awardSpecialTimeService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(AwardSpecialTimeUpdateMapper updateMapper) {
    return awardSpecialTimeService.updateMapping(updateMapper);
  }
}
