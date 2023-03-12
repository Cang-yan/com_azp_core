package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSkillExcellence;
import com.azp.core.sys.model.AwardSkillExcellenceFilterMapper;
import com.azp.core.sys.service.AwardSkillExcellenceService;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardSkillExcellenceFlowRetrieveService {
  @Autowired
  private AwardSkillExcellenceService awardSkillExcellenceService;

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_RETRIEVE_BY_PK")
  public AwardSkillExcellence retrieveByPK(String key) {
    return awardSkillExcellenceService.getByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return awardSkillExcellenceService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return awardSkillExcellenceService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(AwardSkillExcellenceFilterMapper filterMapper) {
    return awardSkillExcellenceService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_RETRIEVE_LIST_BY_FILTER")
  public List<AwardSkillExcellence> retrieveListByFilter(AwardSkillExcellenceFilterMapper filterMapper) {
    return awardSkillExcellenceService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(AwardSkillExcellenceFilterMapper filterMapper) {
    return awardSkillExcellenceService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SKILL_EXCELLENCE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(AwardSkillExcellenceFilterMapper filterMapper) {
    return awardSkillExcellenceService.getFilterDetailMapList(filterMapper);
  }
}
