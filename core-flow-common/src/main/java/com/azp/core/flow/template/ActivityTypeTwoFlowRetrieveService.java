package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.service.ActivityTypeTwoService;
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
public class ActivityTypeTwoFlowRetrieveService {
  @Autowired
  private ActivityTypeTwoService activityTypeTwoService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_RETRIEVE_BY_PK")
  public ActivityTypeTwo retrieveByPK(String key) {
    return activityTypeTwoService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeTwoService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeTwoService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeTwoFilterMapper filterMapper) {
    return activityTypeTwoService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeTwo> retrieveListByFilter(ActivityTypeTwoFilterMapper filterMapper) {
    return activityTypeTwoService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeTwoFilterMapper filterMapper) {
    return activityTypeTwoService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeTwoFilterMapper filterMapper) {
    return activityTypeTwoService.getFilterDetailMapList(filterMapper);
  }
}
