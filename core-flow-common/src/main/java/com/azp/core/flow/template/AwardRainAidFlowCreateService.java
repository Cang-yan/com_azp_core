package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardRainAid;
import com.azp.core.sys.model.AwardRainAidPostMapper;
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
public class AwardRainAidFlowCreateService {
  @Autowired
  private AwardRainAidService awardRainAidService;

  @Flow("FLOW_CODE_AWARD_RAIN_AID_CREATE")
  public AwardRainAid create(AwardRainAid postEntity) {
    return awardRainAidService.post(postEntity);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_CREATE_LIST")
  public List<AwardRainAid> createList(List<AwardRainAid> postEntities) {
    return awardRainAidService.postList(postEntities);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<AwardRainAidPostMapper> postMappers) {
    return awardRainAidService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_CREATE_MAPPING")
  public Map<String, Object> createMapping(AwardRainAidPostMapper postMapper) {
    return awardRainAidService.postMapping(postMapper);
  }
}
