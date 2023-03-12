package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.PointStore;
import com.azp.core.sys.model.PointStoreUpdateMapper;
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
public class PointStoreFlowUpdateService {
  @Autowired
  private PointStoreService pointStoreService;

  @Flow("FLOW_CODE_POINT_STORE_UPDATE")
  public PointStore update(PointStore updateEntity) {
    return pointStoreService.update(updateEntity);
  }

  @Flow("FLOW_CODE_POINT_STORE_UPDATE_LIST")
  public List<PointStore> updateList(List<PointStore> updateEntities) {
    return pointStoreService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_POINT_STORE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<PointStoreUpdateMapper> updateMappers) {
    return pointStoreService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_POINT_STORE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(PointStoreUpdateMapper updateMapper) {
    return pointStoreService.updateMapping(updateMapper);
  }
}
