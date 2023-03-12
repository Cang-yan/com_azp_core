package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeFourPeriodsFilter;
import com.azp.core.sys.datainterface.ActivityTypeFourPeriodsDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourPeriodsDO;
import com.azp.core.sys.model.ActivityTypeFour;
import com.azp.core.sys.model.ActivityTypeFourFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriods;
import com.azp.core.sys.model.ActivityTypeFourPeriodsData;
import com.azp.core.sys.model.ActivityTypeFourPeriodsDetailMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriodsFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriodsPostMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriodsSimpleMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriodsUpdateMapper;
import com.azp.core.sys.model.ActivityTypeFourSimpleMapper;
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
public class ActivityTypeFourPeriodsService {
  @Autowired
  private ActivityTypeFourPeriodsDAO activityTypeFourPeriodsDAO;

  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  public ActivityTypeFourPeriods getByPK(String key) {
    ActivityTypeFourPeriodsDO entity = activityTypeFourPeriodsDAO.selectByPrimaryKey(key);
    return ActivityTypeFourPeriodsData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeFourPeriodsSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeFourPeriods modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activityTypeFour data from local database;
    ActivityTypeFourFilterMapper activityTypeFourListFilterMapper = new ActivityTypeFourFilterMapper();
    activityTypeFourListFilterMapper.periodsNumber = modelEntity.getPeriodsNumber();
    activityTypeFourListFilterMapper.page = 0L;
    activityTypeFourListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeFourListData = activityTypeFourService.getFilterMapList(activityTypeFourListFilterMapper);
    return ActivityTypeFourPeriodsDetailMapper.buildMapExtra(modelEntity,activityTypeFourListData);
  }

  public Long getCountByFilter(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    return activityTypeFourPeriodsDAO.countByExample(ActivityTypeFourPeriodsFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeFourPeriods> getListByFilter(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    List<ActivityTypeFourPeriods> entityList = new ArrayList<>();
    for (ActivityTypeFourPeriodsDO entity : activityTypeFourPeriodsDAO.selectByExample(ActivityTypeFourPeriodsFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeFourPeriodsData.convert(entity, new ActivityTypeFourPeriods()));
    }
    return entityList;
  }

  public List<ActivityTypeFourPeriods> getListByRelatedPeriodsNumber(List<Integer> periodsNumberList) {
    List<ActivityTypeFourPeriods> entityList = new ArrayList<>();
    if (periodsNumberList.size() == 0) return entityList;
    for (ActivityTypeFourPeriodsDO entity : activityTypeFourPeriodsDAO.selectByExample(ActivityTypeFourPeriodsFilter.initPeriodsNumberQueryFilter(periodsNumberList))) {
      entityList.add(ActivityTypeFourPeriodsData.convert(entity, new ActivityTypeFourPeriods()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeFourPeriodsSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeFourPeriods data;
    List<ActivityTypeFourPeriods> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<Integer> periodsNumberList = new ArrayList<>();
    for (ActivityTypeFourPeriods modelEntity : modelEntityList) {
      periodsNumberList.add(modelEntity.getPeriodsNumber());
    }
    // load data from local database or remote service;
    List<ActivityTypeFour> activityTypeFourList = activityTypeFourService.getListByRelatedPeriodsNumber(periodsNumberList);
    // loop assembly data;
    for (ActivityTypeFourPeriods modelEntity : modelEntityList) {
      // filter, map, and form activityTypeFour data;
      List<Map<String, Object>> activityTypeFourListData = activityTypeFourList.stream()
          .filter(item -> modelEntity.getPeriodsNumber() != null && modelEntity.getPeriodsNumber().equals(item.getPeriodsNumber()))
          .map(ActivityTypeFourSimpleMapper::buildMap)
          .collect(Collectors.toList());
      entityMapList.add(ActivityTypeFourPeriodsDetailMapper.buildMapExtra(modelEntity,activityTypeFourListData));
    }
    return entityMapList;
  }

  public ActivityTypeFourPeriods post(ActivityTypeFourPeriods postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeFourPeriodsDAO.insertSelective(ActivityTypeFourPeriodsData.convert(postEntity, new ActivityTypeFourPeriodsDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeFourPeriodsPostMapper postMapper) {
    ActivityTypeFourPeriods entity = post(postMapper.buildEntity());
    return ActivityTypeFourPeriodsDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourPeriods> postList(List<ActivityTypeFourPeriods> postEntities) {
    List<ActivityTypeFourPeriods> entityList = new ArrayList<>();
    for (ActivityTypeFourPeriods postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeFourPeriodsPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourPeriodsPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFourPeriods update(ActivityTypeFourPeriods updateEntity) {
    ActivityTypeFourPeriods modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeFourPeriodsDAO.updateByPrimaryKeySelective(ActivityTypeFourPeriodsData.convert(updateEntity, new ActivityTypeFourPeriodsDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeFourPeriodsUpdateMapper updateMapper) {
    ActivityTypeFourPeriods entity = update(updateMapper.buildEntity());
    return ActivityTypeFourPeriodsDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourPeriods> updateList(List<ActivityTypeFourPeriods> updateEntities) {
    List<ActivityTypeFourPeriods> entityList = new ArrayList<>();
    for (ActivityTypeFourPeriods updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourPeriodsUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourPeriodsUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFourPeriods put(ActivityTypeFourPeriods putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeFourPeriods modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeFourPeriodsDAO.insertSelective(ActivityTypeFourPeriodsData.convert(putEntity, new ActivityTypeFourPeriodsDO()));
    }
    else {
      activityTypeFourPeriodsDAO.updateByPrimaryKeySelective(ActivityTypeFourPeriodsData.convert(putEntity, new ActivityTypeFourPeriodsDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeFourPeriodsUpdateMapper putMapper) {
    ActivityTypeFourPeriods entity = put(putMapper.buildEntity());
    return ActivityTypeFourPeriodsDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFourPeriods> putList(List<ActivityTypeFourPeriods> putEntities) {
    List<ActivityTypeFourPeriods> entityList = new ArrayList<>();
    for (ActivityTypeFourPeriods putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeFourPeriodsUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourPeriodsUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeFourPeriodsDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeFourPeriodsFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeFourPeriodsFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeFourPeriodsFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeFourPeriodsFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeFourPeriodsFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeFourPeriodsFilterMapper filterMapper,
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
