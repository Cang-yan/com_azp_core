package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourPeriods;
import com.azp.core.sys.model.ActivityTypeFourPeriodsPostMapper;
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
public class ActivityTypeFourPeriodsFlowCreateService {
  @Autowired
  private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_CREATE")
  public ActivityTypeFourPeriods create(ActivityTypeFourPeriods postEntity) {
    return activityTypeFourPeriodsService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_CREATE_LIST")
  public List<ActivityTypeFourPeriods> createList(List<ActivityTypeFourPeriods> postEntities) {
    return activityTypeFourPeriodsService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeFourPeriodsPostMapper> postMappers) {
    return activityTypeFourPeriodsService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeFourPeriodsPostMapper postMapper) {
    return activityTypeFourPeriodsService.postMapping(postMapper);
  }
}
