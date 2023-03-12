package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivityDepartmentSelectService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityDepartmentSelectFlowDeleteService {
  @Autowired
  private ActivityDepartmentSelectService activityDepartmentSelectService;

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_DELETE")
  public Integer delete(String key) {
    return activityDepartmentSelectService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_DEPARTMENT_SELECT_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activityDepartmentSelectService.deleteList(keys);
  }
}
