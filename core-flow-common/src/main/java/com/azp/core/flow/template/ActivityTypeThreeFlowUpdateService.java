package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeThree;
import com.azp.core.sys.model.ActivityTypeThreeUpdateMapper;
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
public class ActivityTypeThreeFlowUpdateService {
  @Autowired
  private ActivityTypeThreeService activityTypeThreeService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_UPDATE")
  public ActivityTypeThree update(ActivityTypeThree updateEntity) {
    return activityTypeThreeService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_UPDATE_LIST")
  public List<ActivityTypeThree> updateList(List<ActivityTypeThree> updateEntities) {
    return activityTypeThreeService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeThreeUpdateMapper> updateMappers) {
    return activityTypeThreeService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeThreeUpdateMapper updateMapper) {
    return activityTypeThreeService.updateMapping(updateMapper);
  }
}
