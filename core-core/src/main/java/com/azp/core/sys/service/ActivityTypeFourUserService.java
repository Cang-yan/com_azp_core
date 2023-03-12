package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeFourUserFilter;
import com.azp.core.sys.datainterface.ActivityTypeFourUserDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourUserDO;
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
public class ActivityTypeFourUserService {
  @Autowired
  private ActivityTypeFourUserDAO activityTypeFourUserDAO;

  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityTypeFourUserPointService activityTypeFourUserPointService;

  public ActivityTypeFourUser getByPK(String key) {
    ActivityTypeFourUserDO entity = activityTypeFourUserDAO.selectByPrimaryKey(key);
    return ActivityTypeFourUserData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeFourUserSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeFourUser modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activityTypeFour data from local database;
    ActivityTypeFourFilterMapper activityTypeFourFilterMapper = new ActivityTypeFourFilterMapper();
    activityTypeFourFilterMapper.id = modelEntity.getActivityTypeFourId();
    activityTypeFourFilterMapper.page = 0L;
    activityTypeFourFilterMapper.rows = 0;
    Map<String, Object> activityTypeFourData = activityTypeFourService.getFilterMapList(activityTypeFourFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build activityTypeFourUserPoint data from local database;
    ActivityTypeFourUserPointFilterMapper activityTypeFourUserPointListFilterMapper = new ActivityTypeFourUserPointFilterMapper();
    activityTypeFourUserPointListFilterMapper.activityTypeFourUserId = modelEntity.getId();
    activityTypeFourUserPointListFilterMapper.page = 0L;
    activityTypeFourUserPointListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeFourUserPointListData = activityTypeFourUserPointService.getFilterMapList(activityTypeFourUserPointListFilterMapper);
    return ActivityTypeFourUserDetailMapper.buildMapExtra(modelEntity,activityTypeFourData,userData,activityTypeFourUserPointListData);
  }

  public Long getCountByFilter(ActivityTypeFourUserFilterMapper filterMapper) {
    return activityTypeFourUserDAO.countByExample(ActivityTypeFourUserFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeFourUser> getListByFilter(ActivityTypeFourUserFilterMapper filterMapper) {
    List<ActivityTypeFourUser> entityList = new ArrayList<>();
    for (ActivityTypeFourUserDO entity : activityTypeFourUserDAO.selectByExample(ActivityTypeFourUserFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeFourUserData.convert(entity, new ActivityTypeFourUser()));
    }
    return entityList;
  }

  public List<ActivityTypeFourUser> getListByRelatedActivityTypeFourId(List<String> activityTypeFourIdList) {
    List<ActivityTypeFourUser> entityList = new ArrayList<>();
    if (activityTypeFourIdList.size() == 0) return entityList;
    for (ActivityTypeFourUserDO entity : activityTypeFourUserDAO.selectByExample(ActivityTypeFourUserFilter.initActivityTypeFourIdQueryFilter(activityTypeFourIdList))) {
      entityList.add(ActivityTypeFourUserData.convert(entity, new ActivityTypeFourUser()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeFourUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeFourUserSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeFourUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeFourUser data;
    List<ActivityTypeFourUser> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> activityTypeFourIdList = new ArrayList<>();
    ArrayList<String> userIdList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    for (ActivityTypeFourUser modelEntity : modelEntityList) {
      activityTypeFourIdList.add(modelEntity.getActivityTypeFourId());
      idList.add(modelEntity.getId());
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<ActivityTypeFour> activityTypeFourList = activityTypeFourService.getListByRelatedId(activityTypeFourIdList);
    List<User> userList = userService.getListByRelatedId(userIdList);
    List<ActivityTypeFourUserPoint> activityTypeFourUserPointList = activityTypeFourUserPointService.getListByRelatedActivityTypeFourUserId(idList);
    // loop assembly data;
    for (ActivityTypeFourUser modelEntity : modelEntityList) {
      // filter, map, and form activityTypeFour data;
      Map<String, Object> activityTypeFourData = activityTypeFourList.stream()
          .filter(item -> modelEntity.getActivityTypeFourId() != null && modelEntity.getActivityTypeFourId().equals(item.getId()))
          .map(ActivityTypeFourSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form activityTypeFourUserPoint data;
      List<Map<String, Object>> activityTypeFourUserPointListData = activityTypeFourUserPointList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivityTypeFourUserId()))
          .map(ActivityTypeFourUserPointSimpleMapper::buildMap)
          .collect(Collectors.toList());
      entityMapList.add(ActivityTypeFourUserDetailMapper.buildMapExtra(modelEntity,activityTypeFourData,userData,activityTypeFourUserPointListData));
    }
    return entityMapList;
  }

  public ActivityTypeFourUser post(ActivityTypeFourUser postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeFourUserDAO.insertSelective(ActivityTypeFourUserData.convert(postEntity, new ActivityTypeFourUserDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeFourUserPostMapper postMapper) {
    ActivityTypeFourUser entity = post(postMapper.buildEntity());
    return ActivityTypeFourUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourUser> postList(List<ActivityTypeFourUser> postEntities) {
    List<ActivityTypeFourUser> entityList = new ArrayList<>();
    for (ActivityTypeFourUser postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeFourUserPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUserPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFourUser update(ActivityTypeFourUser updateEntity) {
    ActivityTypeFourUser modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeFourUserDAO.updateByPrimaryKeySelective(ActivityTypeFourUserData.convert(updateEntity, new ActivityTypeFourUserDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeFourUserUpdateMapper updateMapper) {
    ActivityTypeFourUser entity = update(updateMapper.buildEntity());
    return ActivityTypeFourUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourUser> updateList(List<ActivityTypeFourUser> updateEntities) {
    List<ActivityTypeFourUser> entityList = new ArrayList<>();
    for (ActivityTypeFourUser updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourUserUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUserUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFourUser put(ActivityTypeFourUser putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeFourUser modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeFourUserDAO.insertSelective(ActivityTypeFourUserData.convert(putEntity, new ActivityTypeFourUserDO()));
    }
    else {
      activityTypeFourUserDAO.updateByPrimaryKeySelective(ActivityTypeFourUserData.convert(putEntity, new ActivityTypeFourUserDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeFourUserUpdateMapper putMapper) {
    ActivityTypeFourUser entity = put(putMapper.buildEntity());
    return ActivityTypeFourUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourUser> putList(List<ActivityTypeFourUser> putEntities) {
    List<ActivityTypeFourUser> entityList = new ArrayList<>();
    for (ActivityTypeFourUser putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeFourUserUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUserUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeFourUserDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeFourUserFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeFourUserFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeFourUserFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeFourUserFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeFourUserFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeFourUserFilterMapper filterMapper,
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
