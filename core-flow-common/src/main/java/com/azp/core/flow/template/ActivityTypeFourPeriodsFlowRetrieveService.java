package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourPeriods;
import com.azp.core.sys.model.ActivityTypeFourPeriodsFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourPeriodsService;
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
public class ActivityTypeFourPeriodsFlowRetrieveService {
  @Autowired
  private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_RETRIEVE_BY_PK")
  public ActivityTypeFourPeriods retrieveByPK(String key) {
    return activityTypeFourPeriodsService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeFourPeriodsService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeFourPeriodsService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    return activityTypeFourPeriodsService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeFourPeriods> retrieveListByFilter(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    return activityTypeFourPeriodsService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    return activityTypeFourPeriodsService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    return activityTypeFourPeriodsService.getFilterDetailMapList(filterMapper);
  }
}
