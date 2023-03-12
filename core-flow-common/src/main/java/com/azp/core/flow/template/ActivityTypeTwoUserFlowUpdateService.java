package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeTwoUser;
import com.azp.core.sys.model.ActivityTypeTwoUserUpdateMapper;
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
public class ActivityTypeTwoUserFlowUpdateService {
  @Autowired
  private ActivityTypeTwoUserService activityTypeTwoUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_UPDATE")
  public ActivityTypeTwoUser update(ActivityTypeTwoUser updateEntity) {
    return activityTypeTwoUserService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_UPDATE_LIST")
  public List<ActivityTypeTwoUser> updateList(List<ActivityTypeTwoUser> updateEntities) {
    return activityTypeTwoUserService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeTwoUserUpdateMapper> updateMappers) {
    return activityTypeTwoUserService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeTwoUserUpdateMapper updateMapper) {
    return activityTypeTwoUserService.updateMapping(updateMapper);
  }
}
