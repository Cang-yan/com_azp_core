package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityDepartmentSelect;
import com.azp.core.sys.model.ActivityDepartmentSelectFilterMapper;
import com.azp.core.sys.service.ActivityDepartmentSelectService;
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
public class ActivityDepartmentSelectFlowRetrieveService {
  @Autowired
  private ActivityDepartmentSelectService activityDepartmentSelectService;

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_RETRIEVE_BY_PK")
  public ActivityDepartmentSelect retrieveByPK(String key) {
    return activityDepartmentSelectService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityDepartmentSelectService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityDepartmentSelectService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityDepartmentSelectFilterMapper filterMapper) {
    return activityDepartmentSelectService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityDepartmentSelect> retrieveListByFilter(ActivityDepartmentSelectFilterMapper filterMapper) {
    return activityDepartmentSelectService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityDepartmentSelectFilterMapper filterMapper) {
    return activityDepartmentSelectService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityDepartmentSelectFilterMapper filterMapper) {
    return activityDepartmentSelectService.getFilterDetailMapList(filterMapper);
  }
}
