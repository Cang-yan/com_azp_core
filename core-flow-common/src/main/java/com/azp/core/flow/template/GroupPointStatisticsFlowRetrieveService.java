package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.GroupPointStatistics;
import com.azp.core.sys.model.GroupPointStatisticsFilterMapper;
import com.azp.core.sys.service.GroupPointStatisticsService;
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
public class GroupPointStatisticsFlowRetrieveService {
  @Autowired
  private GroupPointStatisticsService groupPointStatisticsService;

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_RETRIEVE_BY_PK")
  public GroupPointStatistics retrieveByPK(String key) {
    return groupPointStatisticsService.getByPK(key);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return groupPointStatisticsService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return groupPointStatisticsService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(GroupPointStatisticsFilterMapper filterMapper) {
    return groupPointStatisticsService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_RETRIEVE_LIST_BY_FILTER")
  public List<GroupPointStatistics> retrieveListByFilter(GroupPointStatisticsFilterMapper filterMapper) {
    return groupPointStatisticsService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(GroupPointStatisticsFilterMapper filterMapper) {
    return groupPointStatisticsService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(GroupPointStatisticsFilterMapper filterMapper) {
    return groupPointStatisticsService.getFilterDetailMapList(filterMapper);
  }
}
