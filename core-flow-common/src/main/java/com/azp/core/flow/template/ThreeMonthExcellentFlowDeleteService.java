package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ThreeMonthExcellentService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ThreeMonthExcellentFlowDeleteService {
  @Autowired
  private ThreeMonthExcellentService threeMonthExcellentService;

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_DELETE")
  public Integer delete(String key) {
    return threeMonthExcellentService.delete(key);
  }

  @Flow("FLOW_CODE_THREE_MONTH_EXCELLENT_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return threeMonthExcellentService.deleteList(keys);
  }
}
