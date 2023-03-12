package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityCategory;
import com.azp.core.sys.model.ActivityCategoryPostMapper;
import com.azp.core.sys.service.ActivityCategoryService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityCategoryFlowCreateService {
  @Autowired
  private ActivityCategoryService activityCategoryService;

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_CREATE")
  public ActivityCategory create(ActivityCategory postEntity) {
    return activityCategoryService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_CREATE_LIST")
  public List<ActivityCategory> createList(List<ActivityCategory> postEntities) {
    return activityCategoryService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityCategoryPostMapper> postMappers) {
    return activityCategoryService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityCategoryPostMapper postMapper) {
    return activityCategoryService.postMapping(postMapper);
  }
}
