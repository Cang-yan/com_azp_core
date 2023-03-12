package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.AwardSnowHeatService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardSnowHeatFlowDeleteService {
  @Autowired
  private AwardSnowHeatService awardSnowHeatService;

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_DELETE")
  public Integer delete(String key) {
    return awardSnowHeatService.delete(key);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return awardSnowHeatService.deleteList(keys);
  }
}
