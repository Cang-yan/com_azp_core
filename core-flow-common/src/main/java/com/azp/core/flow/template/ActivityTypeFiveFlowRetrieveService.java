package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFive;
import com.azp.core.sys.model.ActivityTypeFiveFilterMapper;
import com.azp.core.sys.service.ActivityTypeFiveService;
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
public class ActivityTypeFiveFlowRetrieveService {
  @Autowired
  private ActivityTypeFiveService activityTypeFiveService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_RETRIEVE_BY_PK")
  public ActivityTypeFive retrieveByPK(String key) {
    return activityTypeFiveService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeFiveService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeFiveService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeFiveFilterMapper filterMapper) {
    return activityTypeFiveService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeFive> retrieveListByFilter(ActivityTypeFiveFilterMapper filterMapper) {
    return activityTypeFiveService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeFiveFilterMapper filterMapper) {
    return activityTypeFiveService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeFiveFilterMapper filterMapper) {
    return activityTypeFiveService.getFilterDetailMapList(filterMapper);
  }
}
