package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentFilterMapper;
import com.azp.core.sys.service.DepartmentService;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class DepartmentFlowRetrieveService {
  @Autowired
  private DepartmentService departmentService;

  @Flow("FLOW_CODE_DEPARTMENT_RETRIEVE_BY_PK")
  public Department retrieveByPK(String key) {
    return departmentService.getByPK(key);
  }

  @Flow("FLOW_CODE_DEPARTMENT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return departmentService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_DEPARTMENT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return departmentService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_DEPARTMENT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(DepartmentFilterMapper filterMapper) {
    return departmentService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_DEPARTMENT_RETRIEVE_LIST_BY_FILTER")
  public List<Department> retrieveListByFilter(DepartmentFilterMapper filterMapper) {
    return departmentService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_DEPARTMENT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(DepartmentFilterMapper filterMapper) {
    return departmentService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_DEPARTMENT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(DepartmentFilterMapper filterMapper) {
    return departmentService.getFilterDetailMapList(filterMapper);
  }
}
