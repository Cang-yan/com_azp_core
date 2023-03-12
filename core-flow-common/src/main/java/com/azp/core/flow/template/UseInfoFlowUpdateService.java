package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.UseInfo;
import com.azp.core.sys.model.UseInfoUpdateMapper;
import com.azp.core.sys.service.UseInfoService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class UseInfoFlowUpdateService {
  @Autowired
  private UseInfoService useInfoService;

  @Flow("FLOW_CODE_USE_INFO_UPDATE")
  public UseInfo update(UseInfo updateEntity) {
    return useInfoService.update(updateEntity);
  }

  @Flow("FLOW_CODE_USE_INFO_UPDATE_LIST")
  public List<UseInfo> updateList(List<UseInfo> updateEntities) {
    return useInfoService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_USE_INFO_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<UseInfoUpdateMapper> updateMappers) {
    return useInfoService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_USE_INFO_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(UseInfoUpdateMapper updateMapper) {
    return useInfoService.updateMapping(updateMapper);
  }
}
