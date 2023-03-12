package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.ActivitySubCategoryService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivitySubCategoryFlowDeleteService {
  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_DELETE")
  public Integer delete(String key) {
    return activitySubCategoryService.delete(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_SUB_CATEGORY_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return activitySubCategoryService.deleteList(keys);
  }
}
