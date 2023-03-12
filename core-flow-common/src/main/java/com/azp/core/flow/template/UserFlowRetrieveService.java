package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserFilterMapper;
import com.azp.core.sys.service.UserService;
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
public class UserFlowRetrieveService {
  @Autowired
  private UserService userService;

  @Flow("FLOW_CODE_USER_RETRIEVE_BY_PK")
  public User retrieveByPK(String key) {
    return userService.getByPK(key);
  }

  @Flow("FLOW_CODE_USER_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return userService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_USER_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return userService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_USER_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(UserFilterMapper filterMapper) {
    return userService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_USER_RETRIEVE_LIST_BY_FILTER")
  public List<User> retrieveListByFilter(UserFilterMapper filterMapper) {
    return userService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_USER_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(UserFilterMapper filterMapper) {
    return userService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_USER_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(UserFilterMapper filterMapper) {
    return userService.getFilterDetailMapList(filterMapper);
  }
}
