package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.PointExchangeFilter;
import com.azp.core.sys.datainterface.PointExchangeDAO;
import com.azp.core.sys.dataobject.PointExchangeDO;
import com.azp.core.sys.model.PointExchange;
import com.azp.core.sys.model.PointExchangeData;
import com.azp.core.sys.model.PointExchangeDetailMapper;
import com.azp.core.sys.model.PointExchangeFilterMapper;
import com.azp.core.sys.model.PointExchangePostMapper;
import com.azp.core.sys.model.PointExchangeSimpleMapper;
import com.azp.core.sys.model.PointExchangeUpdateMapper;
import com.azp.core.sys.model.PointStore;
import com.azp.core.sys.model.PointStoreFilterMapper;
import com.azp.core.sys.model.PointStoreSimpleMapper;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserFilterMapper;
import com.azp.core.sys.model.UserSimpleMapper;
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
public class PointExchangeService {
  @Autowired
  private PointExchangeDAO pointExchangeDAO;

  @Autowired
  private PointStoreService pointStoreService;

  @Autowired
  private UserService userService;

  public PointExchange getByPK(String key) {
    PointExchangeDO entity = pointExchangeDAO.selectByPrimaryKey(key);
    return PointExchangeData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return PointExchangeSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    PointExchange modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build pointStore data from local database;
    PointStoreFilterMapper pointStoreFilterMapper = new PointStoreFilterMapper();
    pointStoreFilterMapper.id = modelEntity.getProductId();
    pointStoreFilterMapper.page = 0L;
    pointStoreFilterMapper.rows = 0;
    Map<String, Object> pointStoreData = pointStoreService.getFilterMapList(pointStoreFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return PointExchangeDetailMapper.buildMapExtra(modelEntity,pointStoreData,userData);
  }

  public Long getCountByFilter(PointExchangeFilterMapper filterMapper) {
    return pointExchangeDAO.countByExample(PointExchangeFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<PointExchange> getListByFilter(PointExchangeFilterMapper filterMapper) {
    List<PointExchange> entityList = new ArrayList<>();
    for (PointExchangeDO entity : pointExchangeDAO.selectByExample(PointExchangeFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(PointExchangeData.convert(entity, new PointExchange()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(PointExchangeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(PointExchangeSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(PointExchangeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query pointExchange data;
    List<PointExchange> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> productIdList = new ArrayList<>();
    ArrayList<String> userIdList = new ArrayList<>();
    for (PointExchange modelEntity : modelEntityList) {
      productIdList.add(modelEntity.getProductId());
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<PointStore> pointStoreList = pointStoreService.getListByRelatedId(productIdList);
    List<User> userList = userService.getListByRelatedId(userIdList);
    // loop assembly data;
    for (PointExchange modelEntity : modelEntityList) {
      // filter, map, and form pointStore data;
      Map<String, Object> pointStoreData = pointStoreList.stream()
          .filter(item -> modelEntity.getProductId() != null && modelEntity.getProductId().equals(item.getId()))
          .map(PointStoreSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(PointExchangeDetailMapper.buildMapExtra(modelEntity,pointStoreData,userData));
    }
    return entityMapList;
  }

  public PointExchange post(PointExchange postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      pointExchangeDAO.insertSelective(PointExchangeData.convert(postEntity, new PointExchangeDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(PointExchangePostMapper postMapper) {
    PointExchange entity = post(postMapper.buildEntity());
    return PointExchangeDetailMapper.buildMap(entity);
  }

  public List<PointExchange> postList(List<PointExchange> postEntities) {
    List<PointExchange> entityList = new ArrayList<>();
    for (PointExchange postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<PointExchangePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointExchangePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public PointExchange update(PointExchange updateEntity) {
    PointExchange modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    pointExchangeDAO.updateByPrimaryKeySelective(PointExchangeData.convert(updateEntity, new PointExchangeDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(PointExchangeUpdateMapper updateMapper) {
    PointExchange entity = update(updateMapper.buildEntity());
    return PointExchangeDetailMapper.buildMap(entity);
  }

  public List<PointExchange> updateList(List<PointExchange> updateEntities) {
    List<PointExchange> entityList = new ArrayList<>();
    for (PointExchange updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<PointExchangeUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointExchangeUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public PointExchange put(PointExchange putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    PointExchange modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      pointExchangeDAO.insertSelective(PointExchangeData.convert(putEntity, new PointExchangeDO()));
    }
    else {
      pointExchangeDAO.updateByPrimaryKeySelective(PointExchangeData.convert(putEntity, new PointExchangeDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(PointExchangeUpdateMapper putMapper) {
    PointExchange entity = put(putMapper.buildEntity());
    return PointExchangeDetailMapper.buildMap(entity);
  }

  public List<PointExchange> putList(List<PointExchange> putEntities) {
    List<PointExchange> entityList = new ArrayList<>();
    for (PointExchange putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<PointExchangeUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointExchangeUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(pointExchangeDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(PointExchangeFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new PointExchangeFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<PointExchangeFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (PointExchangeFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(PointExchangeFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(PointExchangeFilterMapper filterMapper,
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
