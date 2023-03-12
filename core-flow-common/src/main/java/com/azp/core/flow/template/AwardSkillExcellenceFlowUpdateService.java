package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSkillExcellence;
import com.azp.core.sys.model.AwardSkillExcellenceUpdateMapper;
import com.azp.core.sys.service.AwardSkillExcellenceService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardSkillExcellenceFlowUpdateService {
  @Autowired
  private AwardSkillExcellenceService awardSkillExcellenceService;

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_UPDATE")
  public AwardSkillExcellence update(AwardSkillExcellence updateEntity) {
    return awardSkillExcellenceService.update(updateEntity);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_UPDATE_LIST")
  public List<AwardSkillExcellence> updateList(List<AwardSkillExcellence> updateEntities) {
    return awardSkillExcellenceService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<AwardSkillExcellenceUpdateMapper> updateMappers) {
    return awardSkillExcellenceService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(AwardSkillExcellenceUpdateMapper updateMapper) {
    return awardSkillExcellenceService.updateMapping(updateMapper);
  }
}
