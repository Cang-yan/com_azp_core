package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityDepartmentSelect;
import com.azp.core.sys.model.ActivityDepartmentSelectPostMapper;
import com.azp.core.sys.service.ActivityDepartmentSelectService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityDepartmentSelectFlowCreateService {
  @Autowired
  private ActivityDepartmentSelectService activityDepartmentSelectService;

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_CREATE")
  public ActivityDepartmentSelect create(ActivityDepartmentSelect postEntity) {
    return activityDepartmentSelectService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_CREATE_LIST")
  public List<ActivityDepartmentSelect> createList(List<ActivityDepartmentSelect> postEntities) {
    return activityDepartmentSelectService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityDepartmentSelectPostMapper> postMappers) {
    return activityDepartmentSelectService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityDepartmentSelectPostMapper postMapper) {
    return activityDepartmentSelectService.postMapping(postMapper);
  }
}
