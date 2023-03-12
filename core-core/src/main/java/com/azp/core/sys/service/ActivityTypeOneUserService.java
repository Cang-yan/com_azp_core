package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeOneUserFilter;
import com.azp.core.sys.datainterface.ActivityTypeOneUserDAO;
import com.azp.core.sys.dataobject.ActivityTypeOneUserDO;
import com.azp.core.sys.model.ActivityTypeOne;
import com.azp.core.sys.model.ActivityTypeOneFilterMapper;
import com.azp.core.sys.model.ActivityTypeOneSimpleMapper;
import com.azp.core.sys.model.ActivityTypeOneUser;
import com.azp.core.sys.model.ActivityTypeOneUserData;
import com.azp.core.sys.model.ActivityTypeOneUserDetailMapper;
import com.azp.core.sys.model.ActivityTypeOneUserFilterMapper;
import com.azp.core.sys.model.ActivityTypeOneUserPostMapper;
import com.azp.core.sys.model.ActivityTypeOneUserSimpleMapper;
import com.azp.core.sys.model.ActivityTypeOneUserUpdateMapper;
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
public class ActivityTypeOneUserService {
  @Autowired
  private ActivityTypeOneUserDAO activityTypeOneUserDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  public ActivityTypeOneUser getByPK(String key) {
    ActivityTypeOneUserDO entity = activityTypeOneUserDAO.selectByPrimaryKey(key);
    return ActivityTypeOneUserData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeOneUserSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeOneUser modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build activityTypeOne data from local database;
    ActivityTypeOneFilterMapper activityTypeOneFilterMapper = new ActivityTypeOneFilterMapper();
    activityTypeOneFilterMapper.id = modelEntity.getActivityTypeOneId();
    activityTypeOneFilterMapper.page = 0L;
    activityTypeOneFilterMapper.rows = 0;
    Map<String, Object> activityTypeOneData = activityTypeOneService.getFilterMapList(activityTypeOneFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityTypeOneUserDetailMapper.buildMapExtra(modelEntity,userData,activityTypeOneData);
  }

  public Long getCountByFilter(ActivityTypeOneUserFilterMapper filterMapper) {
    return activityTypeOneUserDAO.countByExample(ActivityTypeOneUserFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeOneUser> getListByFilter(ActivityTypeOneUserFilterMapper filterMapper) {
    List<ActivityTypeOneUser> entityList = new ArrayList<>();
    for (ActivityTypeOneUserDO entity : activityTypeOneUserDAO.selectByExample(ActivityTypeOneUserFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeOneUserData.convert(entity, new ActivityTypeOneUser()));
    }
    return entityList;
  }

  public List<ActivityTypeOneUser> getListByRelatedActivityTypeOneId(List<String> activityTypeOneIdList) {
    List<ActivityTypeOneUser> entityList = new ArrayList<>();
    if (activityTypeOneIdList.size() == 0) return entityList;
    for (ActivityTypeOneUserDO entity : activityTypeOneUserDAO.selectByExample(ActivityTypeOneUserFilter.initActivityTypeOneIdQueryFilter(activityTypeOneIdList))) {
      entityList.add(ActivityTypeOneUserData.convert(entity, new ActivityTypeOneUser()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeOneUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeOneUserSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeOneUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeOneUser data;
    List<ActivityTypeOneUser> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> userIdList = new ArrayList<>();
    ArrayList<String> activityTypeOneIdList = new ArrayList<>();
    for (ActivityTypeOneUser modelEntity : modelEntityList) {
      userIdList.add(modelEntity.getUserId());
      activityTypeOneIdList.add(modelEntity.getActivityTypeOneId());
    }
    // load data from local database or remote service;
    List<User> userList = userService.getListByRelatedId(userIdList);
    List<ActivityTypeOne> activityTypeOneList = activityTypeOneService.getListByRelatedId(activityTypeOneIdList);
    // loop assembly data;
    for (ActivityTypeOneUser modelEntity : modelEntityList) {
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form activityTypeOne data;
      Map<String, Object> activityTypeOneData = activityTypeOneList.stream()
          .filter(item -> modelEntity.getActivityTypeOneId() != null && modelEntity.getActivityTypeOneId().equals(item.getId()))
          .map(ActivityTypeOneSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityTypeOneUserDetailMapper.buildMapExtra(modelEntity,userData,activityTypeOneData));
    }
    return entityMapList;
  }

  public ActivityTypeOneUser post(ActivityTypeOneUser postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeOneUserDAO.insertSelective(ActivityTypeOneUserData.convert(postEntity, new ActivityTypeOneUserDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeOneUserPostMapper postMapper) {
    ActivityTypeOneUser entity = post(postMapper.buildEntity());
    return ActivityTypeOneUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeOneUser> postList(List<ActivityTypeOneUser> postEntities) {
    List<ActivityTypeOneUser> entityList = new ArrayList<>();
    for (ActivityTypeOneUser postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeOneUserPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeOneUserPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeOneUser update(ActivityTypeOneUser updateEntity) {
    ActivityTypeOneUser modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeOneUserDAO.updateByPrimaryKeySelective(ActivityTypeOneUserData.convert(updateEntity, new ActivityTypeOneUserDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeOneUserUpdateMapper updateMapper) {
    ActivityTypeOneUser entity = update(updateMapper.buildEntity());
    return ActivityTypeOneUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeOneUser> updateList(List<ActivityTypeOneUser> updateEntities) {
    List<ActivityTypeOneUser> entityList = new ArrayList<>();
    for (ActivityTypeOneUser updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeOneUserUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeOneUserUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeOneUser put(ActivityTypeOneUser putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeOneUser modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeOneUserDAO.insertSelective(ActivityTypeOneUserData.convert(putEntity, new ActivityTypeOneUserDO()));
    }
    else {
      activityTypeOneUserDAO.updateByPrimaryKeySelective(ActivityTypeOneUserData.convert(putEntity, new ActivityTypeOneUserDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeOneUserUpdateMapper putMapper) {
    ActivityTypeOneUser entity = put(putMapper.buildEntity());
    return ActivityTypeOneUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeOneUser> putList(List<ActivityTypeOneUser> putEntities) {
    List<ActivityTypeOneUser> entityList = new ArrayList<>();
    for (ActivityTypeOneUser putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeOneUserUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeOneUserUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeOneUserDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeOneUserFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeOneUserFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeOneUserFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeOneUserFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeOneUserFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeOneUserFilterMapper filterMapper,
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
