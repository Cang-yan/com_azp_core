package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryUpdateMapper;
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
public class ActivitySubCategoryFlowUpdateService {
  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_UPDATE")
  public ActivitySubCategory update(ActivitySubCategory updateEntity) {
    return activitySubCategoryService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_UPDATE_LIST")
  public List<ActivitySubCategory> updateList(List<ActivitySubCategory> updateEntities) {
    return activitySubCategoryService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivitySubCategoryUpdateMapper> updateMappers) {
    return activitySubCategoryService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivitySubCategoryUpdateMapper updateMapper) {
    return activitySubCategoryService.updateMapping(updateMapper);
  }
}
