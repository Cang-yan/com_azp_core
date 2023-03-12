package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.UserPointStatisticsFilter;
import com.azp.core.sys.datainterface.UserPointStatisticsDAO;
import com.azp.core.sys.dataobject.UserPointStatisticsDO;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserFilterMapper;
import com.azp.core.sys.model.UserPointStatistics;
import com.azp.core.sys.model.UserPointStatisticsData;
import com.azp.core.sys.model.UserPointStatisticsDetailMapper;
import com.azp.core.sys.model.UserPointStatisticsFilterMapper;
import com.azp.core.sys.model.UserPointStatisticsPostMapper;
import com.azp.core.sys.model.UserPointStatisticsSimpleMapper;
import com.azp.core.sys.model.UserPointStatisticsUpdateMapper;
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
public class UserPointStatisticsService {
  @Autowired
  private UserPointStatisticsDAO userPointStatisticsDAO;

  @Autowired
  private UserService userService;

  public UserPointStatistics getByPK(String key) {
    UserPointStatisticsDO entity = userPointStatisticsDAO.selectByPrimaryKey(key);
    return UserPointStatisticsData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return UserPointStatisticsSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    UserPointStatistics modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return UserPointStatisticsDetailMapper.buildMapExtra(modelEntity,userData);
  }

  public Long getCountByFilter(UserPointStatisticsFilterMapper filterMapper) {
    return userPointStatisticsDAO.countByExample(UserPointStatisticsFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<UserPointStatistics> getListByFilter(UserPointStatisticsFilterMapper filterMapper) {
    List<UserPointStatistics> entityList = new ArrayList<>();
    for (UserPointStatisticsDO entity : userPointStatisticsDAO.selectByExample(UserPointStatisticsFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(UserPointStatisticsData.convert(entity, new UserPointStatistics()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(UserPointStatisticsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(UserPointStatisticsSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(UserPointStatisticsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query userPointStatistics data;
    List<UserPointStatistics> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> userIdList = new ArrayList<>();
    for (UserPointStatistics modelEntity : modelEntityList) {
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<User> userList = userService.getListByRelatedId(userIdList);
    // loop assembly data;
    for (UserPointStatistics modelEntity : modelEntityList) {
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(UserPointStatisticsDetailMapper.buildMapExtra(modelEntity,userData));
    }
    return entityMapList;
  }

  public UserPointStatistics post(UserPointStatistics postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      userPointStatisticsDAO.insertSelective(UserPointStatisticsData.convert(postEntity, new UserPointStatisticsDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(UserPointStatisticsPostMapper postMapper) {
    UserPointStatistics entity = post(postMapper.buildEntity());
    return UserPointStatisticsDetailMapper.buildMap(entity);
  }

  public List<UserPointStatistics> postList(List<UserPointStatistics> postEntities) {
    List<UserPointStatistics> entityList = new ArrayList<>();
    for (UserPointStatistics postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<UserPointStatisticsPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserPointStatisticsPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public UserPointStatistics update(UserPointStatistics updateEntity) {
    UserPointStatistics modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    userPointStatisticsDAO.updateByPrimaryKeySelective(UserPointStatisticsData.convert(updateEntity, new UserPointStatisticsDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(UserPointStatisticsUpdateMapper updateMapper) {
    UserPointStatistics entity = update(updateMapper.buildEntity());
    return UserPointStatisticsDetailMapper.buildMap(entity);
  }

  public List<UserPointStatistics> updateList(List<UserPointStatistics> updateEntities) {
    List<UserPointStatistics> entityList = new ArrayList<>();
    for (UserPointStatistics updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<UserPointStatisticsUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserPointStatisticsUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public UserPointStatistics put(UserPointStatistics putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    UserPointStatistics modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      userPointStatisticsDAO.insertSelective(UserPointStatisticsData.convert(putEntity, new UserPointStatisticsDO()));
    }
    else {
      userPointStatisticsDAO.updateByPrimaryKeySelective(UserPointStatisticsData.convert(putEntity, new UserPointStatisticsDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(UserPointStatisticsUpdateMapper putMapper) {
    UserPointStatistics entity = put(putMapper.buildEntity());
    return UserPointStatisticsDetailMapper.buildMap(entity);
  }

  public List<UserPointStatistics> putList(List<UserPointStatistics> putEntities) {
    List<UserPointStatistics> entityList = new ArrayList<>();
    for (UserPointStatistics putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<UserPointStatisticsUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserPointStatisticsUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(userPointStatisticsDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(UserPointStatisticsFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new UserPointStatisticsFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<UserPointStatisticsFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (UserPointStatisticsFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(UserPointStatisticsFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(UserPointStatisticsFilterMapper filterMapper,
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
