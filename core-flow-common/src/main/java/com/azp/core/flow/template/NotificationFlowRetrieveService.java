package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Notification;
import com.azp.core.sys.model.NotificationFilterMapper;
import com.azp.core.sys.service.NotificationService;
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
public class NotificationFlowRetrieveService {
  @Autowired
  private NotificationService notificationService;

  @Flow("FLOW_CODE_NOTIFICATION_RETRIEVE_BY_PK")
  public Notification retrieveByPK(String key) {
    return notificationService.getByPK(key);
  }

  @Flow("FLOW_CODE_NOTIFICATION_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return notificationService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_NOTIFICATION_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return notificationService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_NOTIFICATION_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(NotificationFilterMapper filterMapper) {
    return notificationService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_NOTIFICATION_RETRIEVE_LIST_BY_FILTER")
  public List<Notification> retrieveListByFilter(NotificationFilterMapper filterMapper) {
    return notificationService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_NOTIFICATION_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(NotificationFilterMapper filterMapper) {
    return notificationService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_NOTIFICATION_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(NotificationFilterMapper filterMapper) {
    return notificationService.getFilterDetailMapList(filterMapper);
  }
}
