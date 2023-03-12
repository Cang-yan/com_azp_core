package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFour;
import com.azp.core.sys.model.ActivityTypeFourUpdateMapper;
import com.azp.core.sys.service.ActivityTypeFourService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourFlowUpdateService {
  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_UPDATE")
  public ActivityTypeFour update(ActivityTypeFour updateEntity) {
    return activityTypeFourService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_UPDATE_LIST")
  public List<ActivityTypeFour> updateList(List<ActivityTypeFour> updateEntities) {
    return activityTypeFourService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourUpdateMapper> updateMappers) {
    return activityTypeFourService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeFourUpdateMapper updateMapper) {
    return activityTypeFourService.updateMapping(updateMapper);
  }
}
