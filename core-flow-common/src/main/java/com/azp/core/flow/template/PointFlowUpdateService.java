package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Point;
import com.azp.core.sys.model.PointUpdateMapper;
import com.azp.core.sys.service.PointService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class PointFlowUpdateService {
  @Autowired
  private PointService pointService;

  @Flow("FLOW_CODE_POINT_UPDATE")
  public Point update(Point updateEntity) {
    return pointService.update(updateEntity);
  }

  @Flow("FLOW_CODE_POINT_UPDATE_LIST")
  public List<Point> updateList(List<Point> updateEntities) {
    return pointService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_POINT_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<PointUpdateMapper> updateMappers) {
    return pointService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_POINT_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(PointUpdateMapper updateMapper) {
    return pointService.updateMapping(updateMapper);
  }
}
