package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentUpdateMapper;
import com.azp.core.sys.service.DepartmentService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class DepartmentFlowUpdateService {
  @Autowired
  private DepartmentService departmentService;

  @Flow("FLOW_CODE_DEPARTMENT_UPDATE")
  public Department update(Department updateEntity) {
    return departmentService.update(updateEntity);
  }

  @Flow("FLOW_CODE_DEPARTMENT_UPDATE_LIST")
  public List<Department> updateList(List<Department> updateEntities) {
    return departmentService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_DEPARTMENT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<DepartmentUpdateMapper> updateMappers) {
    return departmentService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_DEPARTMENT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(DepartmentUpdateMapper updateMapper) {
    return departmentService.updateMapping(updateMapper);
  }
}
