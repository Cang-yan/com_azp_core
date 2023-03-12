package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeOne;
import com.azp.core.sys.model.ActivityTypeOnePostMapper;
import com.azp.core.sys.service.ActivityTypeOneService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeOneFlowCreateService {
  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_CREATE")
  public ActivityTypeOne create(ActivityTypeOne postEntity) {
    return activityTypeOneService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_CREATE_LIST")
  public List<ActivityTypeOne> createList(List<ActivityTypeOne> postEntities) {
    return activityTypeOneService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeOnePostMapper> postMappers) {
    return activityTypeOneService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeOnePostMapper postMapper) {
    return activityTypeOneService.postMapping(postMapper);
  }
}
