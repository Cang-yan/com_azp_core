package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.NotificationUser;
import com.azp.core.sys.model.NotificationUserUpdateMapper;
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
public class NotificationUserFlowUpdateService {
  @Autowired
  private NotificationUserService notificationUserService;

  @Flow("FLOW_CODE_NOTIFICATION_USER_UPDATE")
  public NotificationUser update(NotificationUser updateEntity) {
    return notificationUserService.update(updateEntity);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_UPDATE_LIST")
  public List<NotificationUser> updateList(List<NotificationUser> updateEntities) {
    return notificationUserService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<NotificationUserUpdateMapper> updateMappers) {
    return notificationUserService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(NotificationUserUpdateMapper updateMapper) {
    return notificationUserService.updateMapping(updateMapper);
  }
}
