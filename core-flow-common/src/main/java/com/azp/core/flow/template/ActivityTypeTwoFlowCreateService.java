package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoPostMapper;
import com.azp.core.sys.service.ActivityTypeTwoService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeTwoFlowCreateService {
  @Autowired
  private ActivityTypeTwoService activityTypeTwoService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_CREATE")
  public ActivityTypeTwo create(ActivityTypeTwo postEntity) {
    return activityTypeTwoService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_CREATE_LIST")
  public List<ActivityTypeTwo> createList(List<ActivityTypeTwo> postEntities) {
    return activityTypeTwoService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeTwoPostMapper> postMappers) {
    return activityTypeTwoService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeTwoPostMapper postMapper) {
    return activityTypeTwoService.postMapping(postMapper);
  }
}
