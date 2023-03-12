package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.LevelService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class LevelFlowDeleteService {
  @Autowired
  private LevelService levelService;

  @Flow("FLOW_CODE_LEVEL_DELETE")
  public Integer delete(String key) {
    return levelService.delete(key);
  }

  @Flow("FLOW_CODE_LEVEL_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return levelService.deleteList(keys);
  }
}
