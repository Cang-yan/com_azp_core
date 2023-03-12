package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentPostMapper;
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
public class DepartmentFlowCreateService {
  @Autowired
  private DepartmentService departmentService;

  @Flow("FLOW_CODE_DEPARTMENT_CREATE")
  public Department create(Department postEntity) {
    return departmentService.post(postEntity);
  }

  @Flow("FLOW_CODE_DEPARTMENT_CREATE_LIST")
  public List<Department> createList(List<Department> postEntities) {
    return departmentService.postList(postEntities);
  }

  @Flow("FLOW_CODE_DEPARTMENT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<DepartmentPostMapper> postMappers) {
    return departmentService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_DEPARTMENT_CREATE_MAPPING")
  public Map<String, Object> createMapping(DepartmentPostMapper postMapper) {
    return departmentService.postMapping(postMapper);
  }
}
