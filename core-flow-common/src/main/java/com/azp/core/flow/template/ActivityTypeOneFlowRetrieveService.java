package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeOne;
import com.azp.core.sys.model.ActivityTypeOneFilterMapper;
import com.azp.core.sys.service.ActivityTypeOneService;
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
public class ActivityTypeOneFlowRetrieveService {
  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_RETRIEVE_BY_PK")
  public ActivityTypeOne retrieveByPK(String key) {
    return activityTypeOneService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeOneService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeOneService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeOneFilterMapper filterMapper) {
    return activityTypeOneService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeOne> retrieveListByFilter(ActivityTypeOneFilterMapper filterMapper) {
    return activityTypeOneService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeOneFilterMapper filterMapper) {
    return activityTypeOneService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeOneFilterMapper filterMapper) {
    return activityTypeOneService.getFilterDetailMapList(filterMapper);
  }
}
