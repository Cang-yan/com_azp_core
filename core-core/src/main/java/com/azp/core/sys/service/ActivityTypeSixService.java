package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeSixFilter;
import com.azp.core.sys.datainterface.ActivityTypeSixDAO;
import com.azp.core.sys.dataobject.ActivityTypeSixDO;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryFilterMapper;
import com.azp.core.sys.model.ActivitySubCategorySimpleMapper;
import com.azp.core.sys.model.ActivityTypeSix;
import com.azp.core.sys.model.ActivityTypeSixData;
import com.azp.core.sys.model.ActivityTypeSixDetailMapper;
import com.azp.core.sys.model.ActivityTypeSixFilterMapper;
import com.azp.core.sys.model.ActivityTypeSixPostMapper;
import com.azp.core.sys.model.ActivityTypeSixSimpleMapper;
import com.azp.core.sys.model.ActivityTypeSixUpdateMapper;
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
public class ActivityTypeSixService {
  @Autowired
  private ActivityTypeSixDAO activityTypeSixDAO;

  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  @Autowired
  private UserService userService;

  public ActivityTypeSix getByPK(String key) {
    ActivityTypeSixDO entity = activityTypeSixDAO.selectByPrimaryKey(key);
    return ActivityTypeSixData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeSixSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeSix modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activitySubCategory data from local database;
    ActivitySubCategoryFilterMapper activitySubCategoryFilterMapper = new ActivitySubCategoryFilterMapper();
    activitySubCategoryFilterMapper.id = modelEntity.getActivitySubCategoryId();
    activitySubCategoryFilterMapper.page = 0L;
    activitySubCategoryFilterMapper.rows = 0;
    Map<String, Object> activitySubCategoryData = activitySubCategoryService.getFilterMapList(activitySubCategoryFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityTypeSixDetailMapper.buildMapExtra(modelEntity,activitySubCategoryData,userData);
  }

  public Long getCountByFilter(ActivityTypeSixFilterMapper filterMapper) {
    return activityTypeSixDAO.countByExample(ActivityTypeSixFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeSix> getListByFilter(ActivityTypeSixFilterMapper filterMapper) {
    List<ActivityTypeSix> entityList = new ArrayList<>();
    for (ActivityTypeSixDO entity : activityTypeSixDAO.selectByExample(ActivityTypeSixFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeSixData.convert(entity, new ActivityTypeSix()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeSixFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeSixSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeSixFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeSix data;
    List<ActivityTypeSix> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> activitySubCategoryIdList = new ArrayList<>();
    ArrayList<String> userIdList = new ArrayList<>();
    for (ActivityTypeSix modelEntity : modelEntityList) {
      activitySubCategoryIdList.add(modelEntity.getActivitySubCategoryId());
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<ActivitySubCategory> activitySubCategoryList = activitySubCategoryService.getListByRelatedId(activitySubCategoryIdList);
    List<User> userList = userService.getListByRelatedId(userIdList);
    // loop assembly data;
    for (ActivityTypeSix modelEntity : modelEntityList) {
      // filter, map, and form activitySubCategory data;
      Map<String, Object> activitySubCategoryData = activitySubCategoryList.stream()
          .filter(item -> modelEntity.getActivitySubCategoryId() != null && modelEntity.getActivitySubCategoryId().equals(item.getId()))
          .map(ActivitySubCategorySimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityTypeSixDetailMapper.buildMapExtra(modelEntity,activitySubCategoryData,userData));
    }
    return entityMapList;
  }

  public ActivityTypeSix post(ActivityTypeSix postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeSixDAO.insertSelective(ActivityTypeSixData.convert(postEntity, new ActivityTypeSixDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeSixPostMapper postMapper) {
    ActivityTypeSix entity = post(postMapper.buildEntity());
    return ActivityTypeSixDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeSix> postList(List<ActivityTypeSix> postEntities) {
    List<ActivityTypeSix> entityList = new ArrayList<>();
    for (ActivityTypeSix postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeSixPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeSixPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeSix update(ActivityTypeSix updateEntity) {
    ActivityTypeSix modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeSixDAO.updateByPrimaryKeySelective(ActivityTypeSixData.convert(updateEntity, new ActivityTypeSixDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeSixUpdateMapper updateMapper) {
    ActivityTypeSix entity = update(updateMapper.buildEntity());
    return ActivityTypeSixDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeSix> updateList(List<ActivityTypeSix> updateEntities) {
    List<ActivityTypeSix> entityList = new ArrayList<>();
    for (ActivityTypeSix updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeSixUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeSixUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeSix put(ActivityTypeSix putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeSix modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeSixDAO.insertSelective(ActivityTypeSixData.convert(putEntity, new ActivityTypeSixDO()));
    }
    else {
      activityTypeSixDAO.updateByPrimaryKeySelective(ActivityTypeSixData.convert(putEntity, new ActivityTypeSixDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeSixUpdateMapper putMapper) {
    ActivityTypeSix entity = put(putMapper.buildEntity());
    return ActivityTypeSixDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeSix> putList(List<ActivityTypeSix> putEntities) {
    List<ActivityTypeSix> entityList = new ArrayList<>();
    for (ActivityTypeSix putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeSixUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeSixUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeSixDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeSixFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeSixFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeSixFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeSixFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeSixFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeSixFilterMapper filterMapper,
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
