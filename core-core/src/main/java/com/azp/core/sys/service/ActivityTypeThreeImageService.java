package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeThreeImageFilter;
import com.azp.core.sys.datainterface.ActivityTypeThreeImageDAO;
import com.azp.core.sys.dataobject.ActivityTypeThreeImageDO;
import com.azp.core.sys.model.ActivityTypeThree;
import com.azp.core.sys.model.ActivityTypeThreeFilterMapper;
import com.azp.core.sys.model.ActivityTypeThreeImage;
import com.azp.core.sys.model.ActivityTypeThreeImageData;
import com.azp.core.sys.model.ActivityTypeThreeImageDetailMapper;
import com.azp.core.sys.model.ActivityTypeThreeImageFilterMapper;
import com.azp.core.sys.model.ActivityTypeThreeImagePostMapper;
import com.azp.core.sys.model.ActivityTypeThreeImageSimpleMapper;
import com.azp.core.sys.model.ActivityTypeThreeImageUpdateMapper;
import com.azp.core.sys.model.ActivityTypeThreeSimpleMapper;
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
public class ActivityTypeThreeImageService {
  @Autowired
  private ActivityTypeThreeImageDAO activityTypeThreeImageDAO;

  @Autowired
  private ActivityTypeThreeService activityTypeThreeService;

  public ActivityTypeThreeImage getByPK(String key) {
    ActivityTypeThreeImageDO entity = activityTypeThreeImageDAO.selectByPrimaryKey(key);
    return ActivityTypeThreeImageData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeThreeImageSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeThreeImage modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activityTypeThree data from local database;
    ActivityTypeThreeFilterMapper activityTypeThreeFilterMapper = new ActivityTypeThreeFilterMapper();
    activityTypeThreeFilterMapper.id = modelEntity.getActivityTypeThreeId();
    activityTypeThreeFilterMapper.page = 0L;
    activityTypeThreeFilterMapper.rows = 0;
    Map<String, Object> activityTypeThreeData = activityTypeThreeService.getFilterMapList(activityTypeThreeFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityTypeThreeImageDetailMapper.buildMapExtra(modelEntity,activityTypeThreeData);
  }

  public Long getCountByFilter(ActivityTypeThreeImageFilterMapper filterMapper) {
    return activityTypeThreeImageDAO.countByExample(ActivityTypeThreeImageFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeThreeImage> getListByFilter(ActivityTypeThreeImageFilterMapper filterMapper) {
    List<ActivityTypeThreeImage> entityList = new ArrayList<>();
    for (ActivityTypeThreeImageDO entity : activityTypeThreeImageDAO.selectByExample(ActivityTypeThreeImageFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeThreeImageData.convert(entity, new ActivityTypeThreeImage()));
    }
    return entityList;
  }

  public List<ActivityTypeThreeImage> getListByRelatedActivityTypeThreeId(List<String> activityTypeThreeIdList) {
    List<ActivityTypeThreeImage> entityList = new ArrayList<>();
    if (activityTypeThreeIdList.size() == 0) return entityList;
    for (ActivityTypeThreeImageDO entity : activityTypeThreeImageDAO.selectByExample(ActivityTypeThreeImageFilter.initActivityTypeThreeIdQueryFilter(activityTypeThreeIdList))) {
      entityList.add(ActivityTypeThreeImageData.convert(entity, new ActivityTypeThreeImage()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeThreeImageFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeThreeImageSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeThreeImageFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeThreeImage data;
    List<ActivityTypeThreeImage> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> activityTypeThreeIdList = new ArrayList<>();
    for (ActivityTypeThreeImage modelEntity : modelEntityList) {
      activityTypeThreeIdList.add(modelEntity.getActivityTypeThreeId());
    }
    // load data from local database or remote service;
    List<ActivityTypeThree> activityTypeThreeList = activityTypeThreeService.getListByRelatedId(activityTypeThreeIdList);
    // loop assembly data;
    for (ActivityTypeThreeImage modelEntity : modelEntityList) {
      // filter, map, and form activityTypeThree data;
      Map<String, Object> activityTypeThreeData = activityTypeThreeList.stream()
          .filter(item -> modelEntity.getActivityTypeThreeId() != null && modelEntity.getActivityTypeThreeId().equals(item.getId()))
          .map(ActivityTypeThreeSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityTypeThreeImageDetailMapper.buildMapExtra(modelEntity,activityTypeThreeData));
    }
    return entityMapList;
  }

  public ActivityTypeThreeImage post(ActivityTypeThreeImage postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeThreeImageDAO.insertSelective(ActivityTypeThreeImageData.convert(postEntity, new ActivityTypeThreeImageDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeThreeImagePostMapper postMapper) {
    ActivityTypeThreeImage entity = post(postMapper.buildEntity());
    return ActivityTypeThreeImageDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeThreeImage> postList(List<ActivityTypeThreeImage> postEntities) {
    List<ActivityTypeThreeImage> entityList = new ArrayList<>();
    for (ActivityTypeThreeImage postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeThreeImagePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeThreeImagePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeThreeImage update(ActivityTypeThreeImage updateEntity) {
    ActivityTypeThreeImage modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeThreeImageDAO.updateByPrimaryKeySelective(ActivityTypeThreeImageData.convert(updateEntity, new ActivityTypeThreeImageDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeThreeImageUpdateMapper updateMapper) {
    ActivityTypeThreeImage entity = update(updateMapper.buildEntity());
    return ActivityTypeThreeImageDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeThreeImage> updateList(List<ActivityTypeThreeImage> updateEntities) {
    List<ActivityTypeThreeImage> entityList = new ArrayList<>();
    for (ActivityTypeThreeImage updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeThreeImageUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeThreeImageUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeThreeImage put(ActivityTypeThreeImage putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeThreeImage modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeThreeImageDAO.insertSelective(ActivityTypeThreeImageData.convert(putEntity, new ActivityTypeThreeImageDO()));
    }
    else {
      activityTypeThreeImageDAO.updateByPrimaryKeySelective(ActivityTypeThreeImageData.convert(putEntity, new ActivityTypeThreeImageDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeThreeImageUpdateMapper putMapper) {
    ActivityTypeThreeImage entity = put(putMapper.buildEntity());
    return ActivityTypeThreeImageDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeThreeImage> putList(List<ActivityTypeThreeImage> putEntities) {
    List<ActivityTypeThreeImage> entityList = new ArrayList<>();
    for (ActivityTypeThreeImage putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeThreeImageUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeThreeImageUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeThreeImageDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeThreeImageFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeThreeImageFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeThreeImageFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeThreeImageFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeThreeImageFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeThreeImageFilterMapper filterMapper,
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
