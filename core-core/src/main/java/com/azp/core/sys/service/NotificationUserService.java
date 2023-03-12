package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.NotificationUserFilter;
import com.azp.core.sys.datainterface.NotificationUserDAO;
import com.azp.core.sys.dataobject.NotificationUserDO;
import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class NotificationUserService {
  @Autowired
  private NotificationUserDAO notificationUserDAO;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @Autowired
  private UseInfoService useInfoService;

  public NotificationUser getByPK(String key) {
    NotificationUserDO entity = notificationUserDAO.selectByPrimaryKey(key);
    return NotificationUserData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return NotificationUserSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    NotificationUser modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build notification data from local database;
    NotificationFilterMapper notificationFilterMapper = new NotificationFilterMapper();
    notificationFilterMapper.id = modelEntity.getNotificationId();
    notificationFilterMapper.page = 0L;
    notificationFilterMapper.rows = 0;
    Map<String, Object> notificationData = notificationService.getFilterMapList(notificationFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return NotificationUserDetailMapper.buildMapExtra(modelEntity,notificationData,userData);
  }

  public Long getCountByFilter(NotificationUserFilterMapper filterMapper) {
    return notificationUserDAO.countByExample(NotificationUserFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<NotificationUser> getListByFilter(NotificationUserFilterMapper filterMapper) {
    List<NotificationUser> entityList = new ArrayList<>();
    for (NotificationUserDO entity : notificationUserDAO.selectByExample(NotificationUserFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(NotificationUserData.convert(entity, new NotificationUser()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(NotificationUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(NotificationUserSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(NotificationUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query notificationUser data;
    List<NotificationUser> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> notificationIdList = new ArrayList<>();
    ArrayList<String> userIdList = new ArrayList<>();
    for (NotificationUser modelEntity : modelEntityList) {
      notificationIdList.add(modelEntity.getNotificationId());
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<Notification> notificationList = notificationService.getListByRelatedId(notificationIdList);
    List<User> userList = userService.getListByRelatedId(userIdList);
    // loop assembly data;
    for (NotificationUser modelEntity : modelEntityList) {
      // filter, map, and form notification data;
      Map<String, Object> notificationData = notificationList.stream()
          .filter(item -> modelEntity.getNotificationId() != null && modelEntity.getNotificationId().equals(item.getId()))
          .map(NotificationSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
        Notification notification = notificationService.getByPK(modelEntity.getNotificationId());
      if(notification.getType() == 10)
      {
        Map<String,Object> teamData = activityTypeFourService.getSimpleMapByPK(notification.getRelationId());
        Map<String,Object> senderDate = userService.getSimpleMapByPK(modelEntity.getSenderId());
        Map<String, Object> map = NotificationUserDetailMapper.buildMap(modelEntity);
        map.put("notification", notificationData);
        map.put("user", userData);
        UseInfo useInfo = useInfoService.getByPK(userService.getByPK(modelEntity.getSenderId()).getUserInfoId());
        if(useInfo != null && useInfo.getHead() != null)
        {
          senderDate.put("head", useInfo.getHead());
        }
        else
        {
          senderDate.put("head", null);
        }
        map.put("sender", senderDate);
        map.put("team", teamData);
        entityMapList.add(map);
      }
      else {
        entityMapList.add(NotificationUserDetailMapper.buildMapExtra(modelEntity, notificationData, userData));
      }
    }
    return entityMapList;
  }

  public NotificationUser post(NotificationUser postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      notificationUserDAO.insertSelective(NotificationUserData.convert(postEntity, new NotificationUserDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(NotificationUserPostMapper postMapper) {
    NotificationUser entity = post(postMapper.buildEntity());
    return NotificationUserDetailMapper.buildMap(entity);
  }

  public List<NotificationUser> postList(List<NotificationUser> postEntities) {
    List<NotificationUser> entityList = new ArrayList<>();
    for (NotificationUser postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<NotificationUserPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (NotificationUserPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public NotificationUser update(NotificationUser updateEntity) {
    NotificationUser modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    notificationUserDAO.updateByPrimaryKeySelective(NotificationUserData.convert(updateEntity, new NotificationUserDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(NotificationUserUpdateMapper updateMapper) {
    NotificationUser entity = update(updateMapper.buildEntity());
    return NotificationUserDetailMapper.buildMap(entity);
  }

  public List<NotificationUser> updateList(List<NotificationUser> updateEntities) {
    List<NotificationUser> entityList = new ArrayList<>();
    for (NotificationUser updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<NotificationUserUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (NotificationUserUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public NotificationUser put(NotificationUser putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    NotificationUser modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      notificationUserDAO.insertSelective(NotificationUserData.convert(putEntity, new NotificationUserDO()));
    }
    else {
      notificationUserDAO.updateByPrimaryKeySelective(NotificationUserData.convert(putEntity, new NotificationUserDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(NotificationUserUpdateMapper putMapper) {
    NotificationUser entity = put(putMapper.buildEntity());
    return NotificationUserDetailMapper.buildMap(entity);
  }

  public List<NotificationUser> putList(List<NotificationUser> putEntities) {
    List<NotificationUser> entityList = new ArrayList<>();
    for (NotificationUser putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<NotificationUserUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (NotificationUserUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(notificationUserDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(NotificationUserFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new NotificationUserFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<NotificationUserFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (NotificationUserFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(NotificationUserFilterMapper filterMapper,
      List<String> groupBy) {
    Map<String, Map<String, Object>> entityMapGroup = new LinkedHashMap<>();
    if (groupBy.size() == 0) return entityMapGroup;
    List<Map<String, Object>> entityMapList = getFilterMapList(filterMapper);
    Map<String, List<Map<String, Object>>> entityMapListGroup = new LinkedHashMap<>();
    for (Map<String, Object> entityMap : entityMapList) {
      StringBuilder key = new StringBuilder();
      for (String groupTarget : groupBy) {
        key.append(entityMap.get(groupTarget).toString()).append("-");
      }
      key.deleteCharAt(key.length()-1);
      entityMapListGroup.putIfAbsent(key.toString(), new ArrayList<>());
      entityMapListGroup.get(key.toString()).add(entityMap);
    }
    entityMapListGroup.forEach((key, value) -> {
      entityMapGroup.putIfAbsent(key, new LinkedHashMap<>());
      entityMapGroup.get(key).put("data", value);
      entityMapGroup.get(key).put("count", value.size());
      entityMapGroup.get(key).put("rate", value.size() / (double) entityMapList.size());
    } );
    return entityMapGroup;
  }

  public Map<String, Map<String, Object>> getFilterDetailListGroup(NotificationUserFilterMapper filterMapper,
      List<String> groupBy) {
    Map<String, Map<String, Object>> entityMapGroup = new LinkedHashMap<>();
    if (groupBy.size() == 0) return entityMapGroup;
    List<Map<String, Object>> entityMapList = getFilterDetailMapList(filterMapper);
    Map<String, List<Map<String, Object>>> entityMapListGroup = new LinkedHashMap<>();
    for (Map<String, Object> entityMap : entityMapList) {
      StringBuilder key = new StringBuilder();
      for (String groupTarget : groupBy) {
        key.append(entityMap.get(groupTarget).toString()).append("-");
      }
      key.deleteCharAt(key.length()-1);
      entityMapListGroup.putIfAbsent(key.toString(), new ArrayList<>());
      entityMapListGroup.get(key.toString()).add(entityMap);
    }
    entityMapListGroup.forEach((key, value) -> {
      entityMapGroup.putIfAbsent(key, new LinkedHashMap<>());
      entityMapGroup.get(key).put("data", value);
      entityMapGroup.get(key).put("count", value.size());
      entityMapGroup.get(key).put("rate", value.size() / (double) entityMapList.size());
    } );
    return entityMapGroup;
  }
}
