package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.NotificationUser;
import com.azp.core.sys.model.NotificationUserPostMapper;
import com.azp.core.sys.service.NotificationUserService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class NotificationUserFlowCreateService {
  @Autowired
  private NotificationUserService notificationUserService;

  @Flow("FLOW_CODE_NOTIFICATION_USER_CREATE")
  public NotificationUser create(NotificationUser postEntity) {
    return notificationUserService.post(postEntity);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_CREATE_LIST")
  public List<NotificationUser> createList(List<NotificationUser> postEntities) {
    return notificationUserService.postList(postEntities);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<NotificationUserPostMapper> postMappers) {
    return notificationUserService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_CREATE_MAPPING")
  public Map<String, Object> createMapping(NotificationUserPostMapper postMapper) {
    return notificationUserService.postMapping(postMapper);
  }
}
