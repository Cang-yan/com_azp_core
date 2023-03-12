package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourUser;
import com.azp.core.sys.model.ActivityTypeFourUserFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourUserService;
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
public class ActivityTypeFourUserFlowRetrieveService {
  @Autowired
  private ActivityTypeFourUserService activityTypeFourUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_RETRIEVE_BY_PK")
  public ActivityTypeFourUser retrieveByPK(String key) {
    return activityTypeFourUserService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeFourUserService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeFourUserService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeFourUserFilterMapper filterMapper) {
    return activityTypeFourUserService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeFourUser> retrieveListByFilter(ActivityTypeFourUserFilterMapper filterMapper) {
    return activityTypeFourUserService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeFourUserFilterMapper filterMapper) {
    return activityTypeFourUserService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeFourUserFilterMapper filterMapper) {
    return activityTypeFourUserService.getFilterDetailMapList(filterMapper);
  }
}
