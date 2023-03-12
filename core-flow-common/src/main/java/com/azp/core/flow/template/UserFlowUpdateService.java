package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserUpdateMapper;
import com.azp.core.sys.service.UserService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class UserFlowUpdateService {
  @Autowired
  private UserService userService;

  @Flow("FLOW_CODE_USER_UPDATE")
  public User update(User updateEntity) {
    return userService.update(updateEntity);
  }

  @Flow("FLOW_CODE_USER_UPDATE_LIST")
  public List<User> updateList(List<User> updateEntities) {
    return userService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_USER_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<UserUpdateMapper> updateMappers) {
    return userService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_USER_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(UserUpdateMapper updateMapper) {
    return userService.updateMapping(updateMapper);
  }
}
