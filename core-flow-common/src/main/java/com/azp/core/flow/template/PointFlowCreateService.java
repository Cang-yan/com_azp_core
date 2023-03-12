package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Point;
import com.azp.core.sys.model.PointPostMapper;
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
public class PointFlowCreateService {
  @Autowired
  private PointService pointService;

  @Flow("FLOW_CODE_POINT_CREATE")
  public Point create(Point postEntity) {
    return pointService.post(postEntity);
  }

  @Flow("FLOW_CODE_POINT_CREATE_LIST")
  public List<Point> createList(List<Point> postEntities) {
    return pointService.postList(postEntities);
  }

  @Flow("FLOW_CODE_POINT_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<PointPostMapper> postMappers) {
    return pointService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_POINT_CREATE_MAPPING")
  public Map<String, Object> createMapping(PointPostMapper postMapper) {
    return pointService.postMapping(postMapper);
  }
}
