package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeSix;
import com.azp.core.sys.model.ActivityTypeSixFilterMapper;
import com.azp.core.sys.service.ActivityTypeSixService;
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
public class ActivityTypeSixFlowRetrieveService {
  @Autowired
  private ActivityTypeSixService activityTypeSixService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_RETRIEVE_BY_PK")
  public ActivityTypeSix retrieveByPK(String key) {
    return activityTypeSixService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeSixService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeSixService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeSixFilterMapper filterMapper) {
    return activityTypeSixService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeSix> retrieveListByFilter(ActivityTypeSixFilterMapper filterMapper) {
    return activityTypeSixService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeSixFilterMapper filterMapper) {
    return activityTypeSixService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_SIX_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeSixFilterMapper filterMapper) {
    return activityTypeSixService.getFilterDetailMapList(filterMapper);
  }
}
