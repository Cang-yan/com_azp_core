package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.GroupPointStatistics;
import com.azp.core.sys.model.GroupPointStatisticsPostMapper;
import com.azp.core.sys.service.GroupPointStatisticsService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class GroupPointStatisticsFlowCreateService {
  @Autowired
  private GroupPointStatisticsService groupPointStatisticsService;

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_CREATE")
  public GroupPointStatistics create(GroupPointStatistics postEntity) {
    return groupPointStatisticsService.post(postEntity);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_CREATE_LIST")
  public List<GroupPointStatistics> createList(List<GroupPointStatistics> postEntities) {
    return groupPointStatisticsService.postList(postEntities);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<GroupPointStatisticsPostMapper> postMappers) {
    return groupPointStatisticsService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_CREATE_MAPPING")
  public Map<String, Object> createMapping(GroupPointStatisticsPostMapper postMapper) {
    return groupPointStatisticsService.postMapping(postMapper);
  }
}
