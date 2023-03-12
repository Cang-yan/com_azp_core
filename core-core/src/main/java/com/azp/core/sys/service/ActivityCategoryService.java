package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityCategoryFilter;
import com.azp.core.sys.datainterface.ActivityCategoryDAO;
import com.azp.core.sys.dataobject.ActivityCategoryDO;
import com.azp.core.sys.model.ActivityCategory;
import com.azp.core.sys.model.ActivityCategoryData;
import com.azp.core.sys.model.ActivityCategoryDetailMapper;
import com.azp.core.sys.model.ActivityCategoryFilterMapper;
import com.azp.core.sys.model.ActivityCategoryPostMapper;
import com.azp.core.sys.model.ActivityCategorySimpleMapper;
import com.azp.core.sys.model.ActivityCategoryUpdateMapper;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryFilterMapper;
import com.azp.core.sys.model.ActivitySubCategorySimpleMapper;
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
public class ActivityCategoryService {
  @Autowired
  private ActivityCategoryDAO activityCategoryDAO;

  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  public ActivityCategory getByPK(String key) {
    ActivityCategoryDO entity = activityCategoryDAO.selectByPrimaryKey(key);
    return ActivityCategoryData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityCategorySimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityCategory modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activitySubCategory data from local database;
    ActivitySubCategoryFilterMapper activitySubCategoryListFilterMapper = new ActivitySubCategoryFilterMapper();
    activitySubCategoryListFilterMapper.activityCategoryId = modelEntity.getId();
    activitySubCategoryListFilterMapper.page = 0L;
    activitySubCategoryListFilterMapper.rows = 0;
    List<Map<String, Object>> activitySubCategoryListData = activitySubCategoryService.getFilterMapList(activitySubCategoryListFilterMapper);
    return ActivityCategoryDetailMapper.buildMapExtra(modelEntity,activitySubCategoryListData);
  }

  public Long getCountByFilter(ActivityCategoryFilterMapper filterMapper) {
    return activityCategoryDAO.countByExample(ActivityCategoryFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityCategory> getListByFilter(ActivityCategoryFilterMapper filterMapper) {
    List<ActivityCategory> entityList = new ArrayList<>();
    for (ActivityCategoryDO entity : activityCategoryDAO.selectByExample(ActivityCategoryFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityCategoryData.convert(entity, new ActivityCategory()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityCategoryFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityCategorySimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityCategoryFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityCategory data;
    List<ActivityCategory> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> idList = new ArrayList<>();
    for (ActivityCategory modelEntity : modelEntityList) {
      idList.add(modelEntity.getId());
    }
    // load data from local database or remote service;
    List<ActivitySubCategory> activitySubCategoryList = activitySubCategoryService.getListByRelatedActivityCategoryId(idList);
    // loop assembly data;
    for (ActivityCategory modelEntity : modelEntityList) {
      // filter, map, and form activitySubCategory data;
      List<Map<String, Object>> activitySubCategoryListData = activitySubCategoryList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivityCategoryId()))
          .map(ActivitySubCategorySimpleMapper::buildMap)
          .collect(Collectors.toList());
      entityMapList.add(ActivityCategoryDetailMapper.buildMapExtra(modelEntity,activitySubCategoryListData));
    }
    return entityMapList;
  }

  public ActivityCategory post(ActivityCategory postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityCategoryDAO.insertSelective(ActivityCategoryData.convert(postEntity, new ActivityCategoryDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityCategoryPostMapper postMapper) {
    ActivityCategory entity = post(postMapper.buildEntity());
    return ActivityCategoryDetailMapper.buildMap(entity);
  }

  public List<ActivityCategory> postList(List<ActivityCategory> postEntities) {
    List<ActivityCategory> entityList = new ArrayList<>();
    for (ActivityCategory postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityCategoryPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityCategoryPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityCategory update(ActivityCategory updateEntity) {
    ActivityCategory modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityCategoryDAO.updateByPrimaryKeySelective(ActivityCategoryData.convert(updateEntity, new ActivityCategoryDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityCategoryUpdateMapper updateMapper) {
    ActivityCategory entity = update(updateMapper.buildEntity());
    return ActivityCategoryDetailMapper.buildMap(entity);
  }

  public List<ActivityCategory> updateList(List<ActivityCategory> updateEntities) {
    List<ActivityCategory> entityList = new ArrayList<>();
    for (ActivityCategory updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityCategoryUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityCategoryUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityCategory put(ActivityCategory putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityCategory modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityCategoryDAO.insertSelective(ActivityCategoryData.convert(putEntity, new ActivityCategoryDO()));
    }
    else {
      activityCategoryDAO.updateByPrimaryKeySelective(ActivityCategoryData.convert(putEntity, new ActivityCategoryDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityCategoryUpdateMapper putMapper) {
    ActivityCategory entity = put(putMapper.buildEntity());
    return ActivityCategoryDetailMapper.buildMap(entity);
  }

  public List<ActivityCategory> putList(List<ActivityCategory> putEntities) {
    List<ActivityCategory> entityList = new ArrayList<>();
    for (ActivityCategory putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityCategoryUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityCategoryUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityCategoryDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityCategoryFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityCategoryFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityCategoryFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityCategoryFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityCategoryFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityCategoryFilterMapper filterMapper,
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
