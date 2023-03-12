package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.UserPointStatistics;
import com.azp.core.sys.model.UserPointStatisticsPostMapper;
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
public class UserPointStatisticsFlowCreateService {
  @Autowired
  private UserPointStatisticsService userPointStatisticsService;

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_CREATE")
  public UserPointStatistics create(UserPointStatistics postEntity) {
    return userPointStatisticsService.post(postEntity);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_CREATE_LIST")
  public List<UserPointStatistics> createList(List<UserPointStatistics> postEntities) {
    return userPointStatisticsService.postList(postEntities);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<UserPointStatisticsPostMapper> postMappers) {
    return userPointStatisticsService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_USER_POINT_STATISTICS_CREATE_MAPPING")
  public Map<String, Object> createMapping(UserPointStatisticsPostMapper postMapper) {
    return userPointStatisticsService.postMapping(postMapper);
  }
}
