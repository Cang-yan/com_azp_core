package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.UserPointStatistics;
import com.azp.core.sys.model.UserPointStatisticsFilterMapper;
import com.azp.core.sys.service.UserPointStatisticsService;
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
public class UserPointStatisticsFlowRetrieveService {
  @Autowired
  private UserPointStatisticsService userPointStatisticsService;

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_RETRIEVE_BY_PK")
  public UserPointStatistics retrieveByPK(String key) {
    return userPointStatisticsService.getByPK(key);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return userPointStatisticsService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return userPointStatisticsService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(UserPointStatisticsFilterMapper filterMapper) {
    return userPointStatisticsService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_RETRIEVE_LIST_BY_FILTER")
  public List<UserPointStatistics> retrieveListByFilter(UserPointStatisticsFilterMapper filterMapper) {
    return userPointStatisticsService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(UserPointStatisticsFilterMapper filterMapper) {
    return userPointStatisticsService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(UserPointStatisticsFilterMapper filterMapper) {
    return userPointStatisticsService.getFilterDetailMapList(filterMapper);
  }
}
