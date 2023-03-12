package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityCategory;
import com.azp.core.sys.model.ActivityCategoryUpdateMapper;
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
public class ActivityCategoryFlowUpdateService {
  @Autowired
  private ActivityCategoryService activityCategoryService;

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_UPDATE")
  public ActivityCategory update(ActivityCategory updateEntity) {
    return activityCategoryService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_UPDATE_LIST")
  public List<ActivityCategory> updateList(List<ActivityCategory> updateEntities) {
    return activityCategoryService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityCategoryUpdateMapper> updateMappers) {
    return activityCategoryService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_CATEGORY_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityCategoryUpdateMapper updateMapper) {
    return activityCategoryService.updateMapping(updateMapper);
  }
}
