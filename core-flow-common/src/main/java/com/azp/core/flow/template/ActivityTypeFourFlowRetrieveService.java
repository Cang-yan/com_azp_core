package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFour;
import com.azp.core.sys.model.ActivityTypeFourFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourService;
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
public class ActivityTypeFourFlowRetrieveService {
  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_RETRIEVE_BY_PK")
  public ActivityTypeFour retrieveByPK(String key) {
    return activityTypeFourService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeFourService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeFourService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeFourFilterMapper filterMapper) {
    return activityTypeFourService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeFour> retrieveListByFilter(ActivityTypeFourFilterMapper filterMapper) {
    return activityTypeFourService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeFourFilterMapper filterMapper) {
    return activityTypeFourService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeFourFilterMapper filterMapper) {
    return activityTypeFourService.getFilterDetailMapList(filterMapper);
  }
}
