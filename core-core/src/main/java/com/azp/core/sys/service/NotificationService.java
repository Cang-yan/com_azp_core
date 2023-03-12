package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.NotificationFilter;
import com.azp.core.sys.datainterface.NotificationDAO;
import com.azp.core.sys.dataobject.NotificationDO;
import com.azp.core.sys.model.Notification;
import com.azp.core.sys.model.NotificationData;
import com.azp.core.sys.model.NotificationDetailMapper;
import com.azp.core.sys.model.NotificationFilterMapper;
import com.azp.core.sys.model.NotificationPostMapper;
import com.azp.core.sys.model.NotificationSimpleMapper;
import com.azp.core.sys.model.NotificationUpdateMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class NotificationService {
  @Autowired
  private NotificationDAO notificationDAO;

  public Notification getByPK(String key) {
    NotificationDO entity = notificationDAO.selectByPrimaryKey(key);
    return NotificationData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return NotificationSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    Notification modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return NotificationDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(NotificationFilterMapper filterMapper) {
    return notificationDAO.countByExample(NotificationFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<Notification> getListByFilter(NotificationFilterMapper filterMapper) {
    List<Notification> entityList = new ArrayList<>();
    for (NotificationDO entity : notificationDAO.selectByExample(NotificationFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(NotificationData.convert(entity, new Notification()));
    }
    return entityList;
  }

  public List<Notification> getListByRelatedId(List<String> idList) {
    List<Notification> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (NotificationDO entity : notificationDAO.selectByExample(NotificationFilter.initIdQueryFilter(idList))) {
      entityList.add(NotificationData.convert(entity, new Notification()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(NotificationFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(NotificationSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(NotificationFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query notification data;
    List<Notification> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (Notification modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (Notification modelEntity : modelEntityList) {
      entityMapList.add(NotificationDetailMapper.buildMap(modelEntity));
    }
    return entityMapList;
  }

  public Notification post(Notification postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      notificationDAO.insertSelective(NotificationData.convert(postEntity, new NotificationDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(NotificationPostMapper postMapper) {
    Notification entity = post(postMapper.buildEntity());
    return NotificationDetailMapper.buildMap(entity);
  }

  public List<Notification> postList(List<Notification> postEntities) {
    List<Notification> entityList = new ArrayList<>();
    for (Notification postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<NotificationPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (NotificationPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public Notification update(Notification updateEntity) {
    Notification modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    notificationDAO.updateByPrimaryKeySelective(NotificationData.convert(updateEntity, new NotificationDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(NotificationUpdateMapper updateMapper) {
    Notification entity = update(updateMapper.buildEntity());
    return NotificationDetailMapper.buildMap(entity);
  }

  public List<Notification> updateList(List<Notification> updateEntities) {
    List<Notification> entityList = new ArrayList<>();
    for (Notification updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<NotificationUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (NotificationUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public Notification put(Notification putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    Notification modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      notificationDAO.insertSelective(NotificationData.convert(putEntity, new NotificationDO()));
    }
    else {
      notificationDAO.updateByPrimaryKeySelective(NotificationData.convert(putEntity, new NotificationDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(NotificationUpdateMapper putMapper) {
    Notification entity = put(putMapper.buildEntity());
    return NotificationDetailMapper.buildMap(entity);
  }

  public List<Notification> putList(List<Notification> putEntities) {
    List<Notification> entityList = new ArrayList<>();
    for (Notification putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<NotificationUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (NotificationUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(notificationDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(NotificationFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new NotificationFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<NotificationFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (NotificationFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(NotificationFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(NotificationFilterMapper filterMapper,
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
