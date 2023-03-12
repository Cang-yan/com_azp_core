package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Tips;
import com.azp.core.sys.model.TipsPostMapper;
import com.azp.core.sys.service.TipsService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class TipsFlowCreateService {
  @Autowired
  private TipsService tipsService;

  @Flow("FLOW_CODE_TIPS_CREATE")
  public Tips create(Tips postEntity) {
    return tipsService.post(postEntity);
  }

  @Flow("FLOW_CODE_TIPS_CREATE_LIST")
  public List<Tips> createList(List<Tips> postEntities) {
    return tipsService.postList(postEntities);
  }

  @Flow("FLOW_CODE_TIPS_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<TipsPostMapper> postMappers) {
    return tipsService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_TIPS_CREATE_MAPPING")
  public Map<String, Object> createMapping(TipsPostMapper postMapper) {
    return tipsService.postMapping(postMapper);
  }
}
