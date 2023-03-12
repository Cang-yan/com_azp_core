package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSpecialTime;
import com.azp.core.sys.model.AwardSpecialTimePostMapper;
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
public class AwardSpecialTimeFlowCreateService {
  @Autowired
  private AwardSpecialTimeService awardSpecialTimeService;

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_CREATE")
  public AwardSpecialTime create(AwardSpecialTime postEntity) {
    return awardSpecialTimeService.post(postEntity);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_CREATE_LIST")
  public List<AwardSpecialTime> createList(List<AwardSpecialTime> postEntities) {
    return awardSpecialTimeService.postList(postEntities);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<AwardSpecialTimePostMapper> postMappers) {
    return awardSpecialTimeService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_CREATE_MAPPING")
  public Map<String, Object> createMapping(AwardSpecialTimePostMapper postMapper) {
    return awardSpecialTimeService.postMapping(postMapper);
  }
}
