package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.UseInfo;
import com.azp.core.sys.model.UseInfoPostMapper;
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
public class UseInfoFlowCreateService {
  @Autowired
  private UseInfoService useInfoService;

  @Flow("FLOW_CODE_USE_INFO_CREATE")
  public UseInfo create(UseInfo postEntity) {
    return useInfoService.post(postEntity);
  }

  @Flow("FLOW_CODE_USE_INFO_CREATE_LIST")
  public List<UseInfo> createList(List<UseInfo> postEntities) {
    return useInfoService.postList(postEntities);
  }

  @Flow("FLOW_CODE_USE_INFO_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<UseInfoPostMapper> postMappers) {
    return useInfoService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_USE_INFO_CREATE_MAPPING")
  public Map<String, Object> createMapping(UseInfoPostMapper postMapper) {
    return useInfoService.postMapping(postMapper);
  }
}
