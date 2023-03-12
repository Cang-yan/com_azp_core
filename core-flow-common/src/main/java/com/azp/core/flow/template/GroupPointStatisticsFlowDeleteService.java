package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.GroupPointStatisticsService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class GroupPointStatisticsFlowDeleteService {
  @Autowired
  private GroupPointStatisticsService groupPointStatisticsService;

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_DELETE")
  public Integer delete(String key) {
    return groupPointStatisticsService.delete(key);
  }

  @Flow("FLOW_CODE_GROUP_POINT_STATISTICS_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return groupPointStatisticsService.deleteList(keys);
  }
}
