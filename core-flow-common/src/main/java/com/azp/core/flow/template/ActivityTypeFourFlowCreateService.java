package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFour;
import com.azp.core.sys.model.ActivityTypeFourPostMapper;
import com.azp.core.sys.service.ActivityTypeFourService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourFlowCreateService {
  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_CREATE")
  public ActivityTypeFour create(ActivityTypeFour postEntity) {
    return activityTypeFourService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_CREATE_LIST")
  public List<ActivityTypeFour> createList(List<ActivityTypeFour> postEntities) {
    return activityTypeFourService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeFourPostMapper> postMappers) {
    return activityTypeFourService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeFourPostMapper postMapper) {
    return activityTypeFourService.postMapping(postMapper);
  }
}
