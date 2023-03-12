package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeFiveFilter;
import com.azp.core.sys.datainterface.ActivityTypeFiveDAO;
import com.azp.core.sys.dataobject.ActivityTypeFiveDO;
import com.azp.core.sys.model.ActivityTypeFive;
import com.azp.core.sys.model.ActivityTypeFiveData;
import com.azp.core.sys.model.ActivityTypeFiveDetailMapper;
import com.azp.core.sys.model.ActivityTypeFiveFilterMapper;
import com.azp.core.sys.model.ActivityTypeFivePostMapper;
import com.azp.core.sys.model.ActivityTypeFiveSimpleMapper;
import com.azp.core.sys.model.ActivityTypeFiveUpdateMapper;
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
public class ActivityTypeFiveService {
  @Autowired
  private ActivityTypeFiveDAO activityTypeFiveDAO;

  @Autowired
  private UserService userService;

  public ActivityTypeFive getByPK(String key) {
    ActivityTypeFiveDO entity = activityTypeFiveDAO.selectByPrimaryKey(key);
    return ActivityTypeFiveData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeFiveSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeFive modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityTypeFiveDetailMapper.buildMapExtra(modelEntity,userData);
  }

  public Long getCountByFilter(ActivityTypeFiveFilterMapper filterMapper) {
    return activityTypeFiveDAO.countByExample(ActivityTypeFiveFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeFive> getListByFilter(ActivityTypeFiveFilterMapper filterMapper) {
    List<ActivityTypeFive> entityList = new ArrayList<>();
    for (ActivityTypeFiveDO entity : activityTypeFiveDAO.selectByExample(ActivityTypeFiveFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeFiveData.convert(entity, new ActivityTypeFive()));
    }
    return entityList;
  }

  public List<ActivityTypeFive> getListByRelatedActivitySubCategory(List<String> activitySubCategoryList) {
    List<ActivityTypeFive> entityList = new ArrayList<>();
    if (activitySubCategoryList.size() == 0) return entityList;
    for (ActivityTypeFiveDO entity : activityTypeFiveDAO.selectByExample(ActivityTypeFiveFilter.initActivitySubCategoryQueryFilter(activitySubCategoryList))) {
      entityList.add(ActivityTypeFiveData.convert(entity, new ActivityTypeFive()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeFiveFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeFiveSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeFiveFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeFive data;
    List<ActivityTypeFive> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> userIdList = new ArrayList<>();
    for (ActivityTypeFive modelEntity : modelEntityList) {
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<User> userList = userService.getListByRelatedId(userIdList);
    // loop assembly data;
    for (ActivityTypeFive modelEntity : modelEntityList) {
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityTypeFiveDetailMapper.buildMapExtra(modelEntity,userData));
    }
    return entityMapList;
  }

  public ActivityTypeFive post(ActivityTypeFive postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeFiveDAO.insertSelective(ActivityTypeFiveData.convert(postEntity, new ActivityTypeFiveDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeFivePostMapper postMapper) {
    ActivityTypeFive entity = post(postMapper.buildEntity());
    return ActivityTypeFiveDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFive> postList(List<ActivityTypeFive> postEntities) {
    List<ActivityTypeFive> entityList = new ArrayList<>();
    for (ActivityTypeFive postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeFivePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFivePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFive update(ActivityTypeFive updateEntity) {
    ActivityTypeFive modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeFiveDAO.updateByPrimaryKeySelective(ActivityTypeFiveData.convert(updateEntity, new ActivityTypeFiveDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeFiveUpdateMapper updateMapper) {
    ActivityTypeFive entity = update(updateMapper.buildEntity());
    return ActivityTypeFiveDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFive> updateList(List<ActivityTypeFive> updateEntities) {
    List<ActivityTypeFive> entityList = new ArrayList<>();
    for (ActivityTypeFive updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFiveUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFiveUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFive put(ActivityTypeFive putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeFive modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeFiveDAO.insertSelective(ActivityTypeFiveData.convert(putEntity, new ActivityTypeFiveDO()));
    }
    else {
      activityTypeFiveDAO.updateByPrimaryKeySelective(ActivityTypeFiveData.convert(putEntity, new ActivityTypeFiveDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeFiveUpdateMapper putMapper) {
    ActivityTypeFive entity = put(putMapper.buildEntity());
    return ActivityTypeFiveDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFive> putList(List<ActivityTypeFive> putEntities) {
    List<ActivityTypeFive> entityList = new ArrayList<>();
    for (ActivityTypeFive putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeFiveUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFiveUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeFiveDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeFiveFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeFiveFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeFiveFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeFiveFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeFiveFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeFiveFilterMapper filterMapper,
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
