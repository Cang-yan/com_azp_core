package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.LevelRecordService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class LevelRecordFlowDeleteService {
  @Autowired
  private LevelRecordService levelRecordService;

  @Flow("FLOW_CODE_LEVEL_RECORD_DELETE")
  public Integer delete(String key) {
    return levelRecordService.delete(key);
  }

  @Flow("FLOW_CODE_LEVEL_RECORD_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return levelRecordService.deleteList(keys);
  }
}
