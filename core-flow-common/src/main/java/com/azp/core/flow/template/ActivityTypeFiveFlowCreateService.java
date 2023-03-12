package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeFive;
import com.azp.core.sys.model.ActivityTypeFivePostMapper;
import com.azp.core.sys.service.ActivityTypeFiveService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFiveFlowCreateService {
  @Autowired
  private ActivityTypeFiveService activityTypeFiveService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_CREATE")
  public ActivityTypeFive create(ActivityTypeFive postEntity) {
    return activityTypeFiveService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_CREATE_LIST")
  public List<ActivityTypeFive> createList(List<ActivityTypeFive> postEntities) {
    return activityTypeFiveService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeFivePostMapper> postMappers) {
    return activityTypeFiveService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FIVE_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeFivePostMapper postMapper) {
    return activityTypeFiveService.postMapping(postMapper);
  }
}
