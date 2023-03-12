package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.NotificationUser;
import com.azp.core.sys.model.NotificationUserFilterMapper;
import com.azp.core.sys.service.NotificationUserService;
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
public class NotificationUserFlowRetrieveService {
  @Autowired
  private NotificationUserService notificationUserService;

  @Flow("FLOW_CODE_NOTIFICATION_USER_RETRIEVE_BY_PK")
  public NotificationUser retrieveByPK(String key) {
    return notificationUserService.getByPK(key);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return notificationUserService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return notificationUserService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(NotificationUserFilterMapper filterMapper) {
    return notificationUserService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_RETRIEVE_LIST_BY_FILTER")
  public List<NotificationUser> retrieveListByFilter(NotificationUserFilterMapper filterMapper) {
    return notificationUserService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(NotificationUserFilterMapper filterMapper) {
    return notificationUserService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_NOTIFICATION_USER_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(NotificationUserFilterMapper filterMapper) {
    return notificationUserService.getFilterDetailMapList(filterMapper);
  }
}
