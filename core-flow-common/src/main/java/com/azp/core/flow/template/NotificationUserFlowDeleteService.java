package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.NotificationUserService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class NotificationUserFlowDeleteService {
  @Autowired
  private NotificationUserService notificationUserService;

  @Flow("FLOW_CODE_NOTIFICATION_USER_DELETE")
  public Integer delete(String key) {
    return notificationUserService.delete(key);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return notificationUserService.deleteList(keys);
  }
}
