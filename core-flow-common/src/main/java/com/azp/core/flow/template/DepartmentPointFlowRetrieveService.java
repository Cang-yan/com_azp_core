package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.DepartmentPoint;
import com.azp.core.sys.model.DepartmentPointFilterMapper;
import com.azp.core.sys.service.DepartmentPointService;
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
public class DepartmentPointFlowRetrieveService {
  @Autowired
  private DepartmentPointService departmentPointService;

  @Flow("FLOW_CODE_DEPARTMENT_POINT_RETRIEVE_BY_PK")
  public DepartmentPoint retrieveByPK(String key) {
    return departmentPointService.getByPK(key);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return departmentPointService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return departmentPointService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(DepartmentPointFilterMapper filterMapper) {
    return departmentPointService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_RETRIEVE_LIST_BY_FILTER")
  public List<DepartmentPoint> retrieveListByFilter(DepartmentPointFilterMapper filterMapper) {
    return departmentPointService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(DepartmentPointFilterMapper filterMapper) {
    return departmentPointService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(DepartmentPointFilterMapper filterMapper) {
    return departmentPointService.getFilterDetailMapList(filterMapper);
  }
}
