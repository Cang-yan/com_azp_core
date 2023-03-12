package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFive;
import com.azp.core.sys.model.ActivityTypeFiveUpdateMapper;
import com.azp.core.sys.service.ActivityTypeFiveService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFiveFlowUpdateService {
  @Autowired
  private ActivityTypeFiveService activityTypeFiveService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_UPDATE")
  public ActivityTypeFive update(ActivityTypeFive updateEntity) {
    return activityTypeFiveService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_UPDATE_LIST")
  public List<ActivityTypeFive> updateList(List<ActivityTypeFive> updateEntities) {
    return activityTypeFiveService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFiveUpdateMapper> updateMappers) {
    return activityTypeFiveService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeFiveUpdateMapper updateMapper) {
    return activityTypeFiveService.updateMapping(updateMapper);
  }
}
