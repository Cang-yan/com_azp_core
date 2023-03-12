package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityDepartmentSelect;
import com.azp.core.sys.model.ActivityDepartmentSelectUpdateMapper;
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
public class ActivityDepartmentSelectFlowUpdateService {
  @Autowired
  private ActivityDepartmentSelectService activityDepartmentSelectService;

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_UPDATE")
  public ActivityDepartmentSelect update(ActivityDepartmentSelect updateEntity) {
    return activityDepartmentSelectService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_UPDATE_LIST")
  public List<ActivityDepartmentSelect> updateList(List<ActivityDepartmentSelect> updateEntities) {
    return activityDepartmentSelectService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityDepartmentSelectUpdateMapper> updateMappers) {
    return activityDepartmentSelectService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityDepartmentSelectUpdateMapper updateMapper) {
    return activityDepartmentSelectService.updateMapping(updateMapper);
  }
}
