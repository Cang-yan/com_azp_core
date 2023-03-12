package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.GroupPointStatistics;
import com.azp.core.sys.model.GroupPointStatisticsUpdateMapper;
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
public class GroupPointStatisticsFlowUpdateService {
  @Autowired
  private GroupPointStatisticsService groupPointStatisticsService;

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_UPDATE")
  public GroupPointStatistics update(GroupPointStatistics updateEntity) {
    return groupPointStatisticsService.update(updateEntity);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_UPDATE_LIST")
  public List<GroupPointStatistics> updateList(List<GroupPointStatistics> updateEntities) {
    return groupPointStatisticsService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<GroupPointStatisticsUpdateMapper> updateMappers) {
    return groupPointStatisticsService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(GroupPointStatisticsUpdateMapper updateMapper) {
    return groupPointStatisticsService.updateMapping(updateMapper);
  }
}
