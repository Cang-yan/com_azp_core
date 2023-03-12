package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.LoginRecord;
import com.azp.core.sys.model.LoginRecordPostMapper;
import com.azp.core.sys.service.LoginRecordService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class LoginRecordFlowCreateService {
  @Autowired
  private LoginRecordService loginRecordService;

  @Flow("FLOW_CODE_LOGIN_RECORD_CREATE")
  public LoginRecord create(LoginRecord postEntity) {
    return loginRecordService.post(postEntity);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_CREATE_LIST")
  public List<LoginRecord> createList(List<LoginRecord> postEntities) {
    return loginRecordService.postList(postEntities);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<LoginRecordPostMapper> postMappers) {
    return loginRecordService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_CREATE_MAPPING")
  public Map<String, Object> createMapping(LoginRecordPostMapper postMapper) {
    return loginRecordService.postMapping(postMapper);
  }
}
