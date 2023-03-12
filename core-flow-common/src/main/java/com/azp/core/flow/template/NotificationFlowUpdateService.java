package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Notification;
import com.azp.core.sys.model.NotificationUpdateMapper;
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
public class NotificationFlowUpdateService {
  @Autowired
  private NotificationService notificationService;

  @Flow("FLOW_CODE_NOTIFICATION_UPDATE")
  public Notification update(Notification updateEntity) {
    return notificationService.update(updateEntity);
  }

  @Flow("FLOW_CODE_NOTIFICATION_UPDATE_LIST")
  public List<Notification> updateList(List<Notification> updateEntities) {
    return notificationService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_NOTIFICATION_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<NotificationUpdateMapper> updateMappers) {
    return notificationService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_NOTIFICATION_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(NotificationUpdateMapper updateMapper) {
    return notificationService.updateMapping(updateMapper);
  }
}
