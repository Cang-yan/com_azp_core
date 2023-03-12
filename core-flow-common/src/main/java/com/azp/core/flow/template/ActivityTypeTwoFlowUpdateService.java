package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoUpdateMapper;
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
public class ActivityTypeTwoFlowUpdateService {
  @Autowired
  private ActivityTypeTwoService activityTypeTwoService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_UPDATE")
  public ActivityTypeTwo update(ActivityTypeTwo updateEntity) {
    return activityTypeTwoService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_UPDATE_LIST")
  public List<ActivityTypeTwo> updateList(List<ActivityTypeTwo> updateEntities) {
    return activityTypeTwoService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeTwoUpdateMapper> updateMappers) {
    return activityTypeTwoService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeTwoUpdateMapper updateMapper) {
    return activityTypeTwoService.updateMapping(updateMapper);
  }
}
