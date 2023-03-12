package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserPostMapper;
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
public class UserFlowCreateService {
  @Autowired
  private UserService userService;

  @Flow("FLOW_CODE_USER_CREATE")
  public User create(User postEntity) {
    return userService.post(postEntity);
  }

  @Flow("FLOW_CODE_USER_CREATE_LIST")
  public List<User> createList(List<User> postEntities) {
    return userService.postList(postEntities);
  }

  @Flow("FLOW_CODE_USER_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<UserPostMapper> postMappers) {
    return userService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_USER_CREATE_MAPPING")
  public Map<String, Object> createMapping(UserPostMapper postMapper) {
    return userService.postMapping(postMapper);
  }
}
