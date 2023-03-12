package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeOne;
import com.azp.core.sys.model.ActivityTypeOneUpdateMapper;
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
public class ActivityTypeOneFlowUpdateService {
  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_UPDATE")
  public ActivityTypeOne update(ActivityTypeOne updateEntity) {
    return activityTypeOneService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_UPDATE_LIST")
  public List<ActivityTypeOne> updateList(List<ActivityTypeOne> updateEntities) {
    return activityTypeOneService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeOneUpdateMapper> updateMappers) {
    return activityTypeOneService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeOneUpdateMapper updateMapper) {
    return activityTypeOneService.updateMapping(updateMapper);
  }
}
