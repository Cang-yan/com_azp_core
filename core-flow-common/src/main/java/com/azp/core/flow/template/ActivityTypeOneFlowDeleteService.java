package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivityTypeOneService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeOneFlowDeleteService {
  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_DELETE")
  public Integer delete(String key) {
    return activityTypeOneService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_ONE_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activityTypeOneService.deleteList(keys);
  }
}
