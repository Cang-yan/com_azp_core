package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.LoginRecord;
import com.azp.core.sys.model.LoginRecordUpdateMapper;
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
public class LoginRecordFlowUpdateService {
  @Autowired
  private LoginRecordService loginRecordService;

  @Flow("FLOW_CODE_LOGIN_RECORD_UPDATE")
  public LoginRecord update(LoginRecord updateEntity) {
    return loginRecordService.update(updateEntity);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_UPDATE_LIST")
  public List<LoginRecord> updateList(List<LoginRecord> updateEntities) {
    return loginRecordService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<LoginRecordUpdateMapper> updateMappers) {
    return loginRecordService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(LoginRecordUpdateMapper updateMapper) {
    return loginRecordService.updateMapping(updateMapper);
  }
}
