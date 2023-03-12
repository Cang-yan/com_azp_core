package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFourUserPoint;
import com.azp.core.sys.model.ActivityTypeFourUserPointPostMapper;
import com.azp.core.sys.service.ActivityTypeFourUserPointService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourUserPointFlowCreateService {
  @Autowired
  private ActivityTypeFourUserPointService activityTypeFourUserPointService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_CREATE")
  public ActivityTypeFourUserPoint create(ActivityTypeFourUserPoint postEntity) {
    return activityTypeFourUserPointService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_CREATE_LIST")
  public List<ActivityTypeFourUserPoint> createList(List<ActivityTypeFourUserPoint> postEntities) {
    return activityTypeFourUserPointService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeFourUserPointPostMapper> postMappers) {
    return activityTypeFourUserPointService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_POINT_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeFourUserPointPostMapper postMapper) {
    return activityTypeFourUserPointService.postMapping(postMapper);
  }
}
