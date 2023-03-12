package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.DepartmentPointService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class DepartmentPointFlowDeleteService {
  @Autowired
  private DepartmentPointService departmentPointService;

  @Flow("FLOW_CODE_DEPARTMENT_POINT_DELETE")
  public Integer delete(String key) {
    return departmentPointService.delete(key);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return departmentPointService.deleteList(keys);
  }
}
