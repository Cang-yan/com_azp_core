package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryPostMapper;
import com.azp.core.sys.service.ActivitySubCategoryService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivitySubCategoryFlowCreateService {
  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_CREATE")
  public ActivitySubCategory create(ActivitySubCategory postEntity) {
    return activitySubCategoryService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_CREATE_LIST")
  public List<ActivitySubCategory> createList(List<ActivitySubCategory> postEntities) {
    return activitySubCategoryService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivitySubCategoryPostMapper> postMappers) {
    return activitySubCategoryService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivitySubCategoryPostMapper postMapper) {
    return activitySubCategoryService.postMapping(postMapper);
  }
}
