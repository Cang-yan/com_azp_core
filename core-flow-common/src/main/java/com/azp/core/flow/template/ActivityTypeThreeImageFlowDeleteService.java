package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivityTypeThreeImageService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeThreeImageFlowDeleteService {
  @Autowired
  private ActivityTypeThreeImageService activityTypeThreeImageService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_DELETE")
  public Integer delete(String key) {
    return activityTypeThreeImageService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activityTypeThreeImageService.deleteList(keys);
  }
}
