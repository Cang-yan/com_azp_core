package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.LoginRecord;
import com.azp.core.sys.model.LoginRecordFilterMapper;
import com.azp.core.sys.service.LoginRecordService;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class LoginRecordFlowRetrieveService {
  @Autowired
  private LoginRecordService loginRecordService;

  @Flow("FLOW_CODE_LOGIN_RECORD_RETRIEVE_BY_PK")
  public LoginRecord retrieveByPK(String key) {
    return loginRecordService.getByPK(key);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return loginRecordService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return loginRecordService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(LoginRecordFilterMapper filterMapper) {
    return loginRecordService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_RETRIEVE_LIST_BY_FILTER")
  public List<LoginRecord> retrieveListByFilter(LoginRecordFilterMapper filterMapper) {
    return loginRecordService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(LoginRecordFilterMapper filterMapper) {
    return loginRecordService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_LOGIN_RECORD_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(LoginRecordFilterMapper filterMapper) {
    return loginRecordService.getFilterDetailMapList(filterMapper);
  }
}
