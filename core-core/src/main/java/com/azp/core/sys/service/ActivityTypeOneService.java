package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeOneFilter;
import com.azp.core.sys.datainterface.ActivityTypeOneDAO;
import com.azp.core.sys.dataobject.ActivityTypeOneDO;
import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class ActivityTypeOneService {
  @Autowired
  private ActivityTypeOneDAO activityTypeOneDAO;

  @Autowired
  private ActivityTypeOneUserService activityTypeOneUserService;

  @Autowired
  private PointService pointService;

  public ActivityTypeOne getByPK(String key) {
    ActivityTypeOneDO entity = activityTypeOneDAO.selectByPrimaryKey(key);
    return ActivityTypeOneData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeOneSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeOne modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activityTypeOneUser data from local database;
    ActivityTypeOneUserFilterMapper activityTypeOneUserListFilterMapper = new ActivityTypeOneUserFilterMapper();
    activityTypeOneUserListFilterMapper.activityTypeOneId = modelEntity.getId();
    activityTypeOneUserListFilterMapper.page = 0L;
    activityTypeOneUserListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeOneUserListData = activityTypeOneUserService.getFilterMapList(activityTypeOneUserListFilterMapper);
    return ActivityTypeOneDetailMapper.buildMapExtra(modelEntity,activityTypeOneUserListData);
  }

  public Long getCountByFilter(ActivityTypeOneFilterMapper filterMapper) {
    return activityTypeOneDAO.countByExample(ActivityTypeOneFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeOne> getListByFilter(ActivityTypeOneFilterMapper filterMapper) {
    List<ActivityTypeOne> entityList = new ArrayList<>();
    for (ActivityTypeOneDO entity : activityTypeOneDAO.selectByExample(ActivityTypeOneFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeOneData.convert(entity, new ActivityTypeOne()));
    }
    return entityList;
  }

  public List<ActivityTypeOne> getListByRelatedActivitySubCategoryId(List<String> activitySubCategoryIdList) {
    List<ActivityTypeOne> entityList = new ArrayList<>();
    if (activitySubCategoryIdList.size() == 0) return entityList;
    for (ActivityTypeOneDO entity : activityTypeOneDAO.selectByExample(ActivityTypeOneFilter.initActivitySubCategoryIdQueryFilter(activitySubCategoryIdList))) {
      entityList.add(ActivityTypeOneData.convert(entity, new ActivityTypeOne()));
    }
    return entityList;
  }

  public List<ActivityTypeOne> getListByRelatedId(List<String> idList) {
    List<ActivityTypeOne> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (ActivityTypeOneDO entity : activityTypeOneDAO.selectByExample(ActivityTypeOneFilter.initIdQueryFilter(idList))) {
      entityList.add(ActivityTypeOneData.convert(entity, new ActivityTypeOne()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeOneFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeOneSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeOneFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeOne data;
    List<ActivityTypeOne> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> idList = new ArrayList<>();
    for (ActivityTypeOne modelEntity : modelEntityList) {
      idList.add(modelEntity.getId());
    }
    // load data from local database or remote service;
    List<ActivityTypeOneUser> activityTypeOneUserList = activityTypeOneUserService.getListByRelatedActivityTypeOneId(idList);
    // loop assembly data;
    for (ActivityTypeOne modelEntity : modelEntityList) {
      // filter, map, and form activityTypeOneUser data;
      List<Map<String, Object>> activityTypeOneUserListData = activityTypeOneUserList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivityTypeOneId()))
          .map(ActivityTypeOneUserSimpleMapper::buildMap)
          .collect(Collectors.toList());
      entityMapList.add(ActivityTypeOneDetailMapper.buildMapExtra(modelEntity,activityTypeOneUserListData));
    }
    return entityMapList;
  }

  public ActivityTypeOne post(ActivityTypeOne postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeOneDAO.insertSelective(ActivityTypeOneData.convert(postEntity, new ActivityTypeOneDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeOnePostMapper postMapper) {
    ActivityTypeOne entity = post(postMapper.buildEntity());
    return ActivityTypeOneDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeOne> postList(List<ActivityTypeOne> postEntities) {
    List<ActivityTypeOne> entityList = new ArrayList<>();
    for (ActivityTypeOne postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeOnePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeOnePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeOne update(ActivityTypeOne updateEntity) {
    ActivityTypeOne modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeOneDAO.updateByPrimaryKeySelective(ActivityTypeOneData.convert(updateEntity, new ActivityTypeOneDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeOneUpdateMapper updateMapper) {
    ActivityTypeOne entity = update(updateMapper.buildEntity());
    return ActivityTypeOneDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeOne> updateList(List<ActivityTypeOne> updateEntities) {
    List<ActivityTypeOne> entityList = new ArrayList<>();
    for (ActivityTypeOne updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeOneUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeOneUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeOne put(ActivityTypeOne putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeOne modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeOneDAO.insertSelective(ActivityTypeOneData.convert(putEntity, new ActivityTypeOneDO()));
    }
    else {
      activityTypeOneDAO.updateByPrimaryKeySelective(ActivityTypeOneData.convert(putEntity, new ActivityTypeOneDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeOneUpdateMapper putMapper) {
    ActivityTypeOne entity = put(putMapper.buildEntity());
    return ActivityTypeOneDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeOne> putList(List<ActivityTypeOne> putEntities) {
    List<ActivityTypeOne> entityList = new ArrayList<>();
    for (ActivityTypeOne putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeOneUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeOneUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeOneDAO.deleteByPrimaryKey(key));
    ActivityTypeOneUserFilterMapper activityTypeOneUserFilterMapper = new ActivityTypeOneUserFilterMapper();
    activityTypeOneUserFilterMapper.activityTypeOneId = key;
    activityTypeOneUserService.getListByFilter(activityTypeOneUserFilterMapper).forEach(activityTypeOneUser -> {
        PointFilterMapper pointFilterMapper = new PointFilterMapper();
        pointFilterMapper.relationId = activityTypeOneUser.getId();
        List<String> pointIds = pointService.getListByFilter(pointFilterMapper).stream().map(Point::getId).collect(Collectors.toList());
        pointService.deleteList(pointIds);
        activityTypeOneUserService.delete(activityTypeOneUser.getId());
    });
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeOneFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeOneFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeOneFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeOneFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeOneFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeOneFilterMapper filterMapper,
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
