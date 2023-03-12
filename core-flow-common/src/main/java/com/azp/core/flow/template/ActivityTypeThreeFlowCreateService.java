package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeThree;
import com.azp.core.sys.model.ActivityTypeThreePostMapper;
import com.azp.core.sys.service.ActivityTypeThreeService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeThreeFlowCreateService {
  @Autowired
  private ActivityTypeThreeService activityTypeThreeService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_CREATE")
  public ActivityTypeThree create(ActivityTypeThree postEntity) {
    return activityTypeThreeService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_CREATE_LIST")
  public List<ActivityTypeThree> createList(List<ActivityTypeThree> postEntities) {
    return activityTypeThreeService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeThreePostMapper> postMappers) {
    return activityTypeThreeService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeThreePostMapper postMapper) {
    return activityTypeThreeService.postMapping(postMapper);
  }
}
