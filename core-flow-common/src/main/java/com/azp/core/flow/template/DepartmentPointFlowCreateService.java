package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.DepartmentPoint;
import com.azp.core.sys.model.DepartmentPointPostMapper;
import com.azp.core.sys.service.DepartmentPointService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class DepartmentPointFlowCreateService {
  @Autowired
  private DepartmentPointService departmentPointService;

  @Flow("FLOW_CODE_DEPARTMENT_POINT_CREATE")
  public DepartmentPoint create(DepartmentPoint postEntity) {
    return departmentPointService.post(postEntity);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_CREATE_LIST")
  public List<DepartmentPoint> createList(List<DepartmentPoint> postEntities) {
    return departmentPointService.postList(postEntities);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<DepartmentPointPostMapper> postMappers) {
    return departmentPointService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_CREATE_MAPPING")
  public Map<String, Object> createMapping(DepartmentPointPostMapper postMapper) {
    return departmentPointService.postMapping(postMapper);
  }
}
