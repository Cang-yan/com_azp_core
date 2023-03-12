package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Tips;
import com.azp.core.sys.model.TipsUpdateMapper;
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
public class TipsFlowUpdateService {
  @Autowired
  private TipsService tipsService;

  @Flow("FLOW_CODE_TIPS_UPDATE")
  public Tips update(Tips updateEntity) {
    return tipsService.update(updateEntity);
  }

  @Flow("FLOW_CODE_TIPS_UPDATE_LIST")
  public List<Tips> updateList(List<Tips> updateEntities) {
    return tipsService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_TIPS_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<TipsUpdateMapper> updateMappers) {
    return tipsService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_TIPS_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(TipsUpdateMapper updateMapper) {
    return tipsService.updateMapping(updateMapper);
  }
}
