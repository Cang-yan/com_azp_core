package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeThree;
import com.azp.core.sys.model.ActivityTypeThreeFilterMapper;
import com.azp.core.sys.service.ActivityTypeThreeService;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeThreeFlowRetrieveService {
  @Autowired
  private ActivityTypeThreeService activityTypeThreeService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_RETRIEVE_BY_PK")
  public ActivityTypeThree retrieveByPK(String key) {
    return activityTypeThreeService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeThreeService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeThreeService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeThreeFilterMapper filterMapper) {
    return activityTypeThreeService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeThree> retrieveListByFilter(ActivityTypeThreeFilterMapper filterMapper) {
    return activityTypeThreeService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeThreeFilterMapper filterMapper) {
    return activityTypeThreeService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeThreeFilterMapper filterMapper) {
    return activityTypeThreeService.getFilterDetailMapList(filterMapper);
  }
}
