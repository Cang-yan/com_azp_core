package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.PointService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class PointFlowDeleteService {
  @Autowired
  private PointService pointService;

  @Flow("FLOW_CODE_POINT_DELETE")
  public Integer delete(String key) {
    return pointService.delete(key);
  }

  @Flow("FLOW_CODE_POINT_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return pointService.deleteList(keys);
  }
}
