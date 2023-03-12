package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.PointStore;
import com.azp.core.sys.model.PointStorePostMapper;
import com.azp.core.sys.service.PointStoreService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class PointStoreFlowCreateService {
  @Autowired
  private PointStoreService pointStoreService;

  @Flow("FLOW_CODE_POINT_STORE_CREATE")
  public PointStore create(PointStore postEntity) {
    return pointStoreService.post(postEntity);
  }

  @Flow("FLOW_CODE_POINT_STORE_CREATE_LIST")
  public List<PointStore> createList(List<PointStore> postEntities) {
    return pointStoreService.postList(postEntities);
  }

  @Flow("FLOW_CODE_POINT_STORE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<PointStorePostMapper> postMappers) {
    return pointStoreService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_POINT_STORE_CREATE_MAPPING")
  public Map<String, Object> createMapping(PointStorePostMapper postMapper) {
    return pointStoreService.postMapping(postMapper);
  }
}
