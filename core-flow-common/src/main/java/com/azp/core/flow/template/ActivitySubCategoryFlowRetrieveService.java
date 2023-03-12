package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryFilterMapper;
import com.azp.core.sys.service.ActivitySubCategoryService;
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
public class ActivitySubCategoryFlowRetrieveService {
  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_RETRIEVE_BY_PK")
  public ActivitySubCategory retrieveByPK(String key) {
    return activitySubCategoryService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activitySubCategoryService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activitySubCategoryService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivitySubCategoryFilterMapper filterMapper) {
    return activitySubCategoryService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_RETRIEVE_LIST_BY_FILTER")
  public List<ActivitySubCategory> retrieveListByFilter(ActivitySubCategoryFilterMapper filterMapper) {
    return activitySubCategoryService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivitySubCategoryFilterMapper filterMapper) {
    return activitySubCategoryService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivitySubCategoryFilterMapper filterMapper) {
    return activitySubCategoryService.getFilterDetailMapList(filterMapper);
  }
}
