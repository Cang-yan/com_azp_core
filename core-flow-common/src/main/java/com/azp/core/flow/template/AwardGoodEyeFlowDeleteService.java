package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.AwardGoodEyeService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardGoodEyeFlowDeleteService {
  @Autowired
  private AwardGoodEyeService awardGoodEyeService;

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_DELETE")
  public Integer delete(String key) {
    return awardGoodEyeService.delete(key);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return awardGoodEyeService.deleteList(keys);
  }
}
