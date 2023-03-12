package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeTwoUser;
import com.azp.core.sys.model.ActivityTypeTwoUserFilterMapper;
import com.azp.core.sys.service.ActivityTypeTwoUserService;
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
public class ActivityTypeTwoUserFlowRetrieveService {
  @Autowired
  private ActivityTypeTwoUserService activityTypeTwoUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_RETRIEVE_BY_PK")
  public ActivityTypeTwoUser retrieveByPK(String key) {
    return activityTypeTwoUserService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeTwoUserService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeTwoUserService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeTwoUserFilterMapper filterMapper) {
    return activityTypeTwoUserService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeTwoUser> retrieveListByFilter(ActivityTypeTwoUserFilterMapper filterMapper) {
    return activityTypeTwoUserService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeTwoUserFilterMapper filterMapper) {
    return activityTypeTwoUserService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeTwoUserFilterMapper filterMapper) {
    return activityTypeTwoUserService.getFilterDetailMapList(filterMapper);
  }
}
