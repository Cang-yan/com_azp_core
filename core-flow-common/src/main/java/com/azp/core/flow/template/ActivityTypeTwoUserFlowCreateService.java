package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeTwoUser;
import com.azp.core.sys.model.ActivityTypeTwoUserPostMapper;
import com.azp.core.sys.service.ActivityTypeTwoUserService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeTwoUserFlowCreateService {
  @Autowired
  private ActivityTypeTwoUserService activityTypeTwoUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_CREATE")
  public ActivityTypeTwoUser create(ActivityTypeTwoUser postEntity) {
    return activityTypeTwoUserService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_CREATE_LIST")
  public List<ActivityTypeTwoUser> createList(List<ActivityTypeTwoUser> postEntities) {
    return activityTypeTwoUserService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeTwoUserPostMapper> postMappers) {
    return activityTypeTwoUserService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeTwoUserPostMapper postMapper) {
    return activityTypeTwoUserService.postMapping(postMapper);
  }
}
