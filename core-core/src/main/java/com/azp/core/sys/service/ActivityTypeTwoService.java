package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeTwoFilter;
import com.azp.core.sys.datainterface.ActivityTypeTwoDAO;
import com.azp.core.sys.dataobject.ActivityTypeTwoDO;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoData;
import com.azp.core.sys.model.ActivityTypeTwoDetailMapper;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoPostMapper;
import com.azp.core.sys.model.ActivityTypeTwoSimpleMapper;
import com.azp.core.sys.model.ActivityTypeTwoUpdateMapper;
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
public class ActivityTypeTwoService {
  @Autowired
  private ActivityTypeTwoDAO activityTypeTwoDAO;

  public ActivityTypeTwo getByPK(String key) {
    ActivityTypeTwoDO entity = activityTypeTwoDAO.selectByPrimaryKey(key);
    return ActivityTypeTwoData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeTwoSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeTwo modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return ActivityTypeTwoDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(ActivityTypeTwoFilterMapper filterMapper) {
    return activityTypeTwoDAO.countByExample(ActivityTypeTwoFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeTwo> getListByFilter(ActivityTypeTwoFilterMapper filterMapper) {
    List<ActivityTypeTwo> entityList = new ArrayList<>();
    for (ActivityTypeTwoDO entity : activityTypeTwoDAO.selectByExample(ActivityTypeTwoFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeTwoData.convert(entity, new ActivityTypeTwo()));
    }
    return entityList;
  }

  public List<ActivityTypeTwo> getListByRelatedActivitySubCategoryId(List<String> activitySubCategoryIdList) {
    List<ActivityTypeTwo> entityList = new ArrayList<>();
    if (activitySubCategoryIdList.size() == 0) return entityList;
    for (ActivityTypeTwoDO entity : activityTypeTwoDAO.selectByExample(ActivityTypeTwoFilter.initActivitySubCategoryIdQueryFilter(activitySubCategoryIdList))) {
      entityList.add(ActivityTypeTwoData.convert(entity, new ActivityTypeTwo()));
    }
    return entityList;
  }

  public List<ActivityTypeTwo> getListByRelatedId(List<String> idList) {
    List<ActivityTypeTwo> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (ActivityTypeTwoDO entity : activityTypeTwoDAO.selectByExample(ActivityTypeTwoFilter.initIdQueryFilter(idList))) {
      entityList.add(ActivityTypeTwoData.convert(entity, new ActivityTypeTwo()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeTwoFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeTwoSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeTwoFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeTwo data;
    List<ActivityTypeTwo> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (ActivityTypeTwo modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (ActivityTypeTwo modelEntity : modelEntityList) {
      entityMapList.add(ActivityTypeTwoDetailMapper.buildMap(modelEntity));
    }
    return entityMapList;
  }

  public ActivityTypeTwo post(ActivityTypeTwo postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeTwoDAO.insertSelective(ActivityTypeTwoData.convert(postEntity, new ActivityTypeTwoDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeTwoPostMapper postMapper) {
    ActivityTypeTwo entity = post(postMapper.buildEntity());
    return ActivityTypeTwoDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeTwo> postList(List<ActivityTypeTwo> postEntities) {
    List<ActivityTypeTwo> entityList = new ArrayList<>();
    for (ActivityTypeTwo postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeTwoPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeTwoPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeTwo update(ActivityTypeTwo updateEntity) {
    ActivityTypeTwo modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeTwoDAO.updateByPrimaryKeySelective(ActivityTypeTwoData.convert(updateEntity, new ActivityTypeTwoDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeTwoUpdateMapper updateMapper) {
    ActivityTypeTwo entity = update(updateMapper.buildEntity());
    return ActivityTypeTwoDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeTwo> updateList(List<ActivityTypeTwo> updateEntities) {
    List<ActivityTypeTwo> entityList = new ArrayList<>();
    for (ActivityTypeTwo updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeTwoUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeTwoUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeTwo put(ActivityTypeTwo putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeTwo modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeTwoDAO.insertSelective(ActivityTypeTwoData.convert(putEntity, new ActivityTypeTwoDO()));
    }
    else {
      activityTypeTwoDAO.updateByPrimaryKeySelective(ActivityTypeTwoData.convert(putEntity, new ActivityTypeTwoDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeTwoUpdateMapper putMapper) {
    ActivityTypeTwo entity = put(putMapper.buildEntity());
    return ActivityTypeTwoDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeTwo> putList(List<ActivityTypeTwo> putEntities) {
    List<ActivityTypeTwo> entityList = new ArrayList<>();
    for (ActivityTypeTwo putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeTwoUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeTwoUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeTwoDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeTwoFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeTwoFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeTwoFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeTwoFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeTwoFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeTwoFilterMapper filterMapper,
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
