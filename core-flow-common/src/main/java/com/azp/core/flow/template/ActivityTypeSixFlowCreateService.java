package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeSix;
import com.azp.core.sys.model.ActivityTypeSixPostMapper;
import com.azp.core.sys.service.ActivityTypeSixService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeSixFlowCreateService {
  @Autowired
  private ActivityTypeSixService activityTypeSixService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_CREATE")
  public ActivityTypeSix create(ActivityTypeSix postEntity) {
    return activityTypeSixService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_CREATE_LIST")
  public List<ActivityTypeSix> createList(List<ActivityTypeSix> postEntities) {
    return activityTypeSixService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeSixPostMapper> postMappers) {
    return activityTypeSixService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeSixPostMapper postMapper) {
    return activityTypeSixService.postMapping(postMapper);
  }
}
