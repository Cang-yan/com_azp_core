package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourUserPoint;
import com.azp.core.sys.model.ActivityTypeFourUserPointFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourUserPointService;
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
public class ActivityTypeFourUserPointFlowRetrieveService {
  @Autowired
  private ActivityTypeFourUserPointService activityTypeFourUserPointService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_RETRIEVE_BY_PK")
  public ActivityTypeFourUserPoint retrieveByPK(String key) {
    return activityTypeFourUserPointService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeFourUserPointService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeFourUserPointService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeFourUserPointFilterMapper filterMapper) {
    return activityTypeFourUserPointService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeFourUserPoint> retrieveListByFilter(ActivityTypeFourUserPointFilterMapper filterMapper) {
    return activityTypeFourUserPointService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeFourUserPointFilterMapper filterMapper) {
    return activityTypeFourUserPointService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeFourUserPointFilterMapper filterMapper) {
    return activityTypeFourUserPointService.getFilterDetailMapList(filterMapper);
  }
}
