package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeOneUser;
import com.azp.core.sys.model.ActivityTypeOneUserFilterMapper;
import com.azp.core.sys.service.ActivityTypeOneUserService;
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
public class ActivityTypeOneUserFlowRetrieveService {
  @Autowired
  private ActivityTypeOneUserService activityTypeOneUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_RETRIEVE_BY_PK")
  public ActivityTypeOneUser retrieveByPK(String key) {
    return activityTypeOneUserService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeOneUserService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeOneUserService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeOneUserFilterMapper filterMapper) {
    return activityTypeOneUserService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeOneUser> retrieveListByFilter(ActivityTypeOneUserFilterMapper filterMapper) {
    return activityTypeOneUserService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeOneUserFilterMapper filterMapper) {
    return activityTypeOneUserService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_USER_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeOneUserFilterMapper filterMapper) {
    return activityTypeOneUserService.getFilterDetailMapList(filterMapper);
  }
}
