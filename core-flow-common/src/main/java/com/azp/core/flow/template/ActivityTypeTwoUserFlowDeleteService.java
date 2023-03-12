package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivityTypeTwoUserService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeTwoUserFlowDeleteService {
  @Autowired
  private ActivityTypeTwoUserService activityTypeTwoUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_DELETE")
  public Integer delete(String key) {
    return activityTypeTwoUserService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_TWO_USER_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activityTypeTwoUserService.deleteList(keys);
  }
}
