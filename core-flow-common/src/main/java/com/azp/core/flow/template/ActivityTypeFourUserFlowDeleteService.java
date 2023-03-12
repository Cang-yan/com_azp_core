package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivityTypeFourUserService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeFourUserFlowDeleteService {
  @Autowired
  private ActivityTypeFourUserService activityTypeFourUserService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_DELETE")
  public Integer delete(String key) {
    return activityTypeFourUserService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_FOUR_USER_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activityTypeFourUserService.deleteList(keys);
  }
}
