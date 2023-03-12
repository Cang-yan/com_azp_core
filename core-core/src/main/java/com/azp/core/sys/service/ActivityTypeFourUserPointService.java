package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeFourUserPointFilter;
import com.azp.core.sys.datainterface.ActivityTypeFourUserPointDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourUserPointDO;
import com.azp.core.sys.model.ActivityTypeFourUserPoint;
import com.azp.core.sys.model.ActivityTypeFourUserPointData;
import com.azp.core.sys.model.ActivityTypeFourUserPointDetailMapper;
import com.azp.core.sys.model.ActivityTypeFourUserPointFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourUserPointPostMapper;
import com.azp.core.sys.model.ActivityTypeFourUserPointSimpleMapper;
import com.azp.core.sys.model.ActivityTypeFourUserPointUpdateMapper;
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
public class ActivityTypeFourUserPointService {
  @Autowired
  private ActivityTypeFourUserPointDAO activityTypeFourUserPointDAO;

  public ActivityTypeFourUserPoint getByPK(String key) {
    ActivityTypeFourUserPointDO entity = activityTypeFourUserPointDAO.selectByPrimaryKey(key);
    return ActivityTypeFourUserPointData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeFourUserPointSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeFourUserPoint modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return ActivityTypeFourUserPointDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(ActivityTypeFourUserPointFilterMapper filterMapper) {
    return activityTypeFourUserPointDAO.countByExample(ActivityTypeFourUserPointFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeFourUserPoint> getListByFilter(ActivityTypeFourUserPointFilterMapper filterMapper) {
    List<ActivityTypeFourUserPoint> entityList = new ArrayList<>();
    for (ActivityTypeFourUserPointDO entity : activityTypeFourUserPointDAO.selectByExample(ActivityTypeFourUserPointFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeFourUserPointData.convert(entity, new ActivityTypeFourUserPoint()));
    }
    return entityList;
  }

  public List<ActivityTypeFourUserPoint> getListByRelatedActivityTypeFourUserId(List<String> activityTypeFourUserIdList) {
    List<ActivityTypeFourUserPoint> entityList = new ArrayList<>();
    if (activityTypeFourUserIdList.size() == 0) return entityList;
    for (ActivityTypeFourUserPointDO entity : activityTypeFourUserPointDAO.selectByExample(ActivityTypeFourUserPointFilter.initActivityTypeFourUserIdQueryFilter(activityTypeFourUserIdList))) {
      entityList.add(ActivityTypeFourUserPointData.convert(entity, new ActivityTypeFourUserPoint()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeFourUserPointFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeFourUserPointSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeFourUserPointFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeFourUserPoint data;
    List<ActivityTypeFourUserPoint> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (ActivityTypeFourUserPoint modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (ActivityTypeFourUserPoint modelEntity : modelEntityList) {
      entityMapList.add(ActivityTypeFourUserPointDetailMapper.buildMap(modelEntity));
    }
    return entityMapList;
  }

  public ActivityTypeFourUserPoint post(ActivityTypeFourUserPoint postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeFourUserPointDAO.insertSelective(ActivityTypeFourUserPointData.convert(postEntity, new ActivityTypeFourUserPointDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeFourUserPointPostMapper postMapper) {
    ActivityTypeFourUserPoint entity = post(postMapper.buildEntity());
    return ActivityTypeFourUserPointDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourUserPoint> postList(List<ActivityTypeFourUserPoint> postEntities) {
    List<ActivityTypeFourUserPoint> entityList = new ArrayList<>();
    for (ActivityTypeFourUserPoint postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeFourUserPointPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUserPointPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFourUserPoint update(ActivityTypeFourUserPoint updateEntity) {
    ActivityTypeFourUserPoint modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeFourUserPointDAO.updateByPrimaryKeySelective(ActivityTypeFourUserPointData.convert(updateEntity, new ActivityTypeFourUserPointDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeFourUserPointUpdateMapper updateMapper) {
    ActivityTypeFourUserPoint entity = update(updateMapper.buildEntity());
    return ActivityTypeFourUserPointDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourUserPoint> updateList(List<ActivityTypeFourUserPoint> updateEntities) {
    List<ActivityTypeFourUserPoint> entityList = new ArrayList<>();
    for (ActivityTypeFourUserPoint updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourUserPointUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUserPointUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFourUserPoint put(ActivityTypeFourUserPoint putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeFourUserPoint modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeFourUserPointDAO.insertSelective(ActivityTypeFourUserPointData.convert(putEntity, new ActivityTypeFourUserPointDO()));
    }
    else {
      activityTypeFourUserPointDAO.updateByPrimaryKeySelective(ActivityTypeFourUserPointData.convert(putEntity, new ActivityTypeFourUserPointDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeFourUserPointUpdateMapper putMapper) {
    ActivityTypeFourUserPoint entity = put(putMapper.buildEntity());
    return ActivityTypeFourUserPointDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourUserPoint> putList(List<ActivityTypeFourUserPoint> putEntities) {
    List<ActivityTypeFourUserPoint> entityList = new ArrayList<>();
    for (ActivityTypeFourUserPoint putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeFourUserPointUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUserPointUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeFourUserPointDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeFourUserPointFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeFourUserPointFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeFourUserPointFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeFourUserPointFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeFourUserPointFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeFourUserPointFilterMapper filterMapper,
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
