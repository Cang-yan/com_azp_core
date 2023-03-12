package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivityTypeThreeService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeThreeFlowDeleteService {
  @Autowired
  private ActivityTypeThreeService activityTypeThreeService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_DELETE")
  public Integer delete(String key) {
    return activityTypeThreeService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activityTypeThreeService.deleteList(keys);
  }
}
