package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.UserPointStatistics;
import com.azp.core.sys.model.UserPointStatisticsUpdateMapper;
import com.azp.core.sys.service.UserPointStatisticsService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class UserPointStatisticsFlowUpdateService {
  @Autowired
  private UserPointStatisticsService userPointStatisticsService;

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_UPDATE")
  public UserPointStatistics update(UserPointStatistics updateEntity) {
    return userPointStatisticsService.update(updateEntity);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_UPDATE_LIST")
  public List<UserPointStatistics> updateList(List<UserPointStatistics> updateEntities) {
    return userPointStatisticsService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<UserPointStatisticsUpdateMapper> updateMappers) {
    return userPointStatisticsService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(UserPointStatisticsUpdateMapper updateMapper) {
    return userPointStatisticsService.updateMapping(updateMapper);
  }
}
