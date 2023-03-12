package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.LoginRecordService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class LoginRecordFlowDeleteService {
  @Autowired
  private LoginRecordService loginRecordService;

  @Flow("FLOW_CODE_LOGIN_RECORD_DELETE")
  public Integer delete(String key) {
    return loginRecordService.delete(key);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return loginRecordService.deleteList(keys);
  }
}
