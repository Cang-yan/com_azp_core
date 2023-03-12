package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivityTypeFourPeriodsService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourPeriodsFlowDeleteService {
  @Autowired
  private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_DELETE")
  public Integer delete(String key) {
    return activityTypeFourPeriodsService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_PERIODS_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activityTypeFourPeriodsService.deleteList(keys);
  }
}
