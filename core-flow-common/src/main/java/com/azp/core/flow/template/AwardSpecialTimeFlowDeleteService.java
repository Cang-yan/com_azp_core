package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.AwardSpecialTimeService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardSpecialTimeFlowDeleteService {
  @Autowired
  private AwardSpecialTimeService awardSpecialTimeService;

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_DELETE")
  public Integer delete(String key) {
    return awardSpecialTimeService.delete(key);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return awardSpecialTimeService.deleteList(keys);
  }
}
