package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Notification;
import com.azp.core.sys.model.NotificationPostMapper;
import com.azp.core.sys.service.NotificationService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class NotificationFlowCreateService {
  @Autowired
  private NotificationService notificationService;

  @Flow("FLOW_CODE_NOTIFICATION_CREATE")
  public Notification create(Notification postEntity) {
    return notificationService.post(postEntity);
  }

  @Flow("FLOW_CODE_NOTIFICATION_CREATE_LIST")
  public List<Notification> createList(List<Notification> postEntities) {
    return notificationService.postList(postEntities);
  }

  @Flow("FLOW_CODE_NOTIFICATION_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<NotificationPostMapper> postMappers) {
    return notificationService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_NOTIFICATION_CREATE_MAPPING")
  public Map<String, Object> createMapping(NotificationPostMapper postMapper) {
    return notificationService.postMapping(postMapper);
  }
}
