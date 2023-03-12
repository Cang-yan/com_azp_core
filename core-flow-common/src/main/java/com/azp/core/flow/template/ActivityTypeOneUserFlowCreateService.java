package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeOneUser;
import com.azp.core.sys.model.ActivityTypeOneUserPostMapper;
import com.azp.core.sys.service.ActivityTypeOneUserService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeOneUserFlowCreateService {
  @Autowired
  private ActivityTypeOneUserService activityTypeOneUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_CREATE")
  public ActivityTypeOneUser create(ActivityTypeOneUser postEntity) {
    return activityTypeOneUserService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_CREATE_LIST")
  public List<ActivityTypeOneUser> createList(List<ActivityTypeOneUser> postEntities) {
    return activityTypeOneUserService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeOneUserPostMapper> postMappers) {
    return activityTypeOneUserService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeOneUserPostMapper postMapper) {
    return activityTypeOneUserService.postMapping(postMapper);
  }
}
