package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.DepartmentPoint;
import com.azp.core.sys.model.DepartmentPointUpdateMapper;
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
public class DepartmentPointFlowUpdateService {
  @Autowired
  private DepartmentPointService departmentPointService;

  @Flow("FLOW_CODE_DEPARTMENT_POINT_UPDATE")
  public DepartmentPoint update(DepartmentPoint updateEntity) {
    return departmentPointService.update(updateEntity);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_UPDATE_LIST")
  public List<DepartmentPoint> updateList(List<DepartmentPoint> updateEntities) {
    return departmentPointService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<DepartmentPointUpdateMapper> updateMappers) {
    return departmentPointService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_DEPARTMENT_POINT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(DepartmentPointUpdateMapper updateMapper) {
    return departmentPointService.updateMapping(updateMapper);
  }
}
