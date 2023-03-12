package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourUser;
import com.azp.core.sys.model.ActivityTypeFourUserUpdateMapper;
import com.azp.core.sys.service.ActivityTypeFourUserService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourUserFlowUpdateService {
  @Autowired
  private ActivityTypeFourUserService activityTypeFourUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_UPDATE")
  public ActivityTypeFourUser update(ActivityTypeFourUser updateEntity) {
    return activityTypeFourUserService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_UPDATE_LIST")
  public List<ActivityTypeFourUser> updateList(List<ActivityTypeFourUser> updateEntities) {
    return activityTypeFourUserService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourUserUpdateMapper> updateMappers) {
    return activityTypeFourUserService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeFourUserUpdateMapper updateMapper) {
    return activityTypeFourUserService.updateMapping(updateMapper);
  }
}
