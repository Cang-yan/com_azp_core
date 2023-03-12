package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourPeriods;
import com.azp.core.sys.model.ActivityTypeFourPeriodsUpdateMapper;
import com.azp.core.sys.service.ActivityTypeFourPeriodsService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourPeriodsFlowUpdateService {
  @Autowired
  private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_UPDATE")
  public ActivityTypeFourPeriods update(ActivityTypeFourPeriods updateEntity) {
    return activityTypeFourPeriodsService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_UPDATE_LIST")
  public List<ActivityTypeFourPeriods> updateList(List<ActivityTypeFourPeriods> updateEntities) {
    return activityTypeFourPeriodsService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourPeriodsUpdateMapper> updateMappers) {
    return activityTypeFourPeriodsService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeFourPeriodsUpdateMapper updateMapper) {
    return activityTypeFourPeriodsService.updateMapping(updateMapper);
  }
}
