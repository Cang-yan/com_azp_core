package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardGoodEye;
import com.azp.core.sys.model.AwardGoodEyePostMapper;
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
public class AwardGoodEyeFlowCreateService {
  @Autowired
  private AwardGoodEyeService awardGoodEyeService;

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_CREATE")
  public AwardGoodEye create(AwardGoodEye postEntity) {
    return awardGoodEyeService.post(postEntity);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_CREATE_LIST")
  public List<AwardGoodEye> createList(List<AwardGoodEye> postEntities) {
    return awardGoodEyeService.postList(postEntities);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<AwardGoodEyePostMapper> postMappers) {
    return awardGoodEyeService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_CREATE_MAPPING")
  public Map<String, Object> createMapping(AwardGoodEyePostMapper postMapper) {
    return awardGoodEyeService.postMapping(postMapper);
  }
}
