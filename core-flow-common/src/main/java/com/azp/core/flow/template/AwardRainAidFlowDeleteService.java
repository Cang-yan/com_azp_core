package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.AwardRainAidService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardRainAidFlowDeleteService {
  @Autowired
  private AwardRainAidService awardRainAidService;

  @Flow("FLOW_CODE_AWARD_RAIN_AID_DELETE")
  public Integer delete(String key) {
    return awardRainAidService.delete(key);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return awardRainAidService.deleteList(keys);
  }
}
