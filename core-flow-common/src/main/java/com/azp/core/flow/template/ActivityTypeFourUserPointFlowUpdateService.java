package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourUserPoint;
import com.azp.core.sys.model.ActivityTypeFourUserPointUpdateMapper;
import com.azp.core.sys.service.ActivityTypeFourUserPointService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourUserPointFlowUpdateService {
  @Autowired
  private ActivityTypeFourUserPointService activityTypeFourUserPointService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_UPDATE")
  public ActivityTypeFourUserPoint update(ActivityTypeFourUserPoint updateEntity) {
    return activityTypeFourUserPointService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_UPDATE_LIST")
  public List<ActivityTypeFourUserPoint> updateList(List<ActivityTypeFourUserPoint> updateEntities) {
    return activityTypeFourUserPointService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourUserPointUpdateMapper> updateMappers) {
    return activityTypeFourUserPointService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeFourUserPointUpdateMapper updateMapper) {
    return activityTypeFourUserPointService.updateMapping(updateMapper);
  }
}
