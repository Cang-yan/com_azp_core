package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeOneUser;
import com.azp.core.sys.model.ActivityTypeOneUserUpdateMapper;
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
public class ActivityTypeOneUserFlowUpdateService {
  @Autowired
  private ActivityTypeOneUserService activityTypeOneUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_UPDATE")
  public ActivityTypeOneUser update(ActivityTypeOneUser updateEntity) {
    return activityTypeOneUserService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_UPDATE_LIST")
  public List<ActivityTypeOneUser> updateList(List<ActivityTypeOneUser> updateEntities) {
    return activityTypeOneUserService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeOneUserUpdateMapper> updateMappers) {
    return activityTypeOneUserService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeOneUserUpdateMapper updateMapper) {
    return activityTypeOneUserService.updateMapping(updateMapper);
  }
}
