package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeSix;
import com.azp.core.sys.model.ActivityTypeSixUpdateMapper;
import com.azp.core.sys.service.ActivityTypeSixService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeSixFlowUpdateService {
  @Autowired
  private ActivityTypeSixService activityTypeSixService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_UPDATE")
  public ActivityTypeSix update(ActivityTypeSix updateEntity) {
    return activityTypeSixService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_UPDATE_LIST")
  public List<ActivityTypeSix> updateList(List<ActivityTypeSix> updateEntities) {
    return activityTypeSixService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeSixUpdateMapper> updateMappers) {
    return activityTypeSixService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeSixUpdateMapper updateMapper) {
    return activityTypeSixService.updateMapping(updateMapper);
  }
}
