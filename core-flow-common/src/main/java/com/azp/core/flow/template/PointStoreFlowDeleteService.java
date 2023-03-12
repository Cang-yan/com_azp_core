package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.PointStoreService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class PointStoreFlowDeleteService {
  @Autowired
  private PointStoreService pointStoreService;

  @Flow("FLOW_CODE_POINT_STORE_DELETE")
  public Integer delete(String key) {
    return pointStoreService.delete(key);
  }

  @Flow("FLOW_CODE_POINT_STORE_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return pointStoreService.deleteList(keys);
  }
}
