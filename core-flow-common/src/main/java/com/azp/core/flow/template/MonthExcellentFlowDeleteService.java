package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.MonthExcellentService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class MonthExcellentFlowDeleteService {
  @Autowired
  private MonthExcellentService monthExcellentService;

  @Flow("FLOW_CODE_MONTH_EXCELLENT_DELETE")
  public Integer delete(String key) {
    return monthExcellentService.delete(key);
  }

  @Flow("FLOW_CODE_MONTH_EXCELLENT_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return monthExcellentService.deleteList(keys);
  }
}
