package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityCategory;
import com.azp.core.sys.model.ActivityCategoryFilterMapper;
import com.azp.core.sys.service.ActivityCategoryService;
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
public class ActivityCategoryFlowRetrieveService {
  @Autowired
  private ActivityCategoryService activityCategoryService;

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_RETRIEVE_BY_PK")
  public ActivityCategory retrieveByPK(String key) {
    return activityCategoryService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityCategoryService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityCategoryService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityCategoryFilterMapper filterMapper) {
    return activityCategoryService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityCategory> retrieveListByFilter(ActivityCategoryFilterMapper filterMapper) {
    return activityCategoryService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityCategoryFilterMapper filterMapper) {
    return activityCategoryService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityCategoryFilterMapper filterMapper) {
    return activityCategoryService.getFilterDetailMapList(filterMapper);
  }
}
