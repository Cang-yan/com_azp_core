package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSkillExcellence;
import com.azp.core.sys.model.AwardSkillExcellencePostMapper;
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
public class AwardSkillExcellenceFlowCreateService {
  @Autowired
  private AwardSkillExcellenceService awardSkillExcellenceService;

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_CREATE")
  public AwardSkillExcellence create(AwardSkillExcellence postEntity) {
    return awardSkillExcellenceService.post(postEntity);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_CREATE_LIST")
  public List<AwardSkillExcellence> createList(List<AwardSkillExcellence> postEntities) {
    return awardSkillExcellenceService.postList(postEntities);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<AwardSkillExcellencePostMapper> postMappers) {
    return awardSkillExcellenceService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_CREATE_MAPPING")
  public Map<String, Object> createMapping(AwardSkillExcellencePostMapper postMapper) {
    return awardSkillExcellenceService.postMapping(postMapper);
  }
}
