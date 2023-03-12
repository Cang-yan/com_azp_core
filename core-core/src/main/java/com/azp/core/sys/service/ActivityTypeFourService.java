package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeFourFilter;
import com.azp.core.sys.datainterface.ActivityTypeFourDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourDO;
import com.azp.core.sys.model.ActivityTypeFour;
import com.azp.core.sys.model.ActivityTypeFourData;
import com.azp.core.sys.model.ActivityTypeFourDetailMapper;
import com.azp.core.sys.model.ActivityTypeFourFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriods;
import com.azp.core.sys.model.ActivityTypeFourPeriodsFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriodsSimpleMapper;
import com.azp.core.sys.model.ActivityTypeFourPostMapper;
import com.azp.core.sys.model.ActivityTypeFourSimpleMapper;
import com.azp.core.sys.model.ActivityTypeFourUpdateMapper;
import com.azp.core.sys.model.ActivityTypeFourUser;
import com.azp.core.sys.model.ActivityTypeFourUserFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourUserSimpleMapper;
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
public class ActivityTypeFourService {
  @Autowired
  private ActivityTypeFourDAO activityTypeFourDAO;

  @Autowired
  private ActivityTypeFourUserService activityTypeFourUserService;

  @Autowired
  private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

  public ActivityTypeFour getByPK(String key) {
    ActivityTypeFourDO entity = activityTypeFourDAO.selectByPrimaryKey(key);
    return ActivityTypeFourData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeFourSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeFour modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activityTypeFourUser data from local database;
    ActivityTypeFourUserFilterMapper activityTypeFourUserListFilterMapper = new ActivityTypeFourUserFilterMapper();
    activityTypeFourUserListFilterMapper.activityTypeFourId = modelEntity.getId();
    activityTypeFourUserListFilterMapper.page = 0L;
    activityTypeFourUserListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeFourUserListData = activityTypeFourUserService.getFilterMapList(activityTypeFourUserListFilterMapper);
    // build activityTypeFourPeriods data from local database;
    ActivityTypeFourPeriodsFilterMapper activityTypeFourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
    activityTypeFourPeriodsFilterMapper.periodsNumber = modelEntity.getPeriodsNumber();
    activityTypeFourPeriodsFilterMapper.page = 0L;
    activityTypeFourPeriodsFilterMapper.rows = 0;
    Map<String, Object> activityTypeFourPeriodsData = activityTypeFourPeriodsService.getFilterMapList(activityTypeFourPeriodsFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityTypeFourDetailMapper.buildMapExtra(modelEntity,activityTypeFourUserListData,activityTypeFourPeriodsData);
  }

  public Long getCountByFilter(ActivityTypeFourFilterMapper filterMapper) {
    return activityTypeFourDAO.countByExample(ActivityTypeFourFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeFour> getListByFilter(ActivityTypeFourFilterMapper filterMapper) {
    List<ActivityTypeFour> entityList = new ArrayList<>();
    for (ActivityTypeFourDO entity : activityTypeFourDAO.selectByExample(ActivityTypeFourFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeFourData.convert(entity, new ActivityTypeFour()));
    }
    return entityList;
  }

  public List<ActivityTypeFour> getListByRelatedActivitySubCategoryId(List<String> activitySubCategoryIdList) {
    List<ActivityTypeFour> entityList = new ArrayList<>();
    if (activitySubCategoryIdList.size() == 0) return entityList;
    for (ActivityTypeFourDO entity : activityTypeFourDAO.selectByExample(ActivityTypeFourFilter.initActivitySubCategoryIdQueryFilter(activitySubCategoryIdList))) {
      entityList.add(ActivityTypeFourData.convert(entity, new ActivityTypeFour()));
    }
    return entityList;
  }

  public List<ActivityTypeFour> getListByRelatedId(List<String> idList) {
    List<ActivityTypeFour> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (ActivityTypeFourDO entity : activityTypeFourDAO.selectByExample(ActivityTypeFourFilter.initIdQueryFilter(idList))) {
      entityList.add(ActivityTypeFourData.convert(entity, new ActivityTypeFour()));
    }
    return entityList;
  }

  public List<ActivityTypeFour> getListByRelatedPeriodsNumber(List<Integer> periodsNumberList) {
    List<ActivityTypeFour> entityList = new ArrayList<>();
    if (periodsNumberList.size() == 0) return entityList;
    for (ActivityTypeFourDO entity : activityTypeFourDAO.selectByExample(ActivityTypeFourFilter.initPeriodsNumberQueryFilter(periodsNumberList))) {
      entityList.add(ActivityTypeFourData.convert(entity, new ActivityTypeFour()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeFourFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeFourSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeFourFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeFour data;
    List<ActivityTypeFour> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> idList = new ArrayList<>();
    ArrayList<Integer> periodsNumberList = new ArrayList<>();
    for (ActivityTypeFour modelEntity : modelEntityList) {
      periodsNumberList.add(modelEntity.getPeriodsNumber());
      idList.add(modelEntity.getId());
    }
    // load data from local database or remote service;
    List<ActivityTypeFourUser> activityTypeFourUserList = activityTypeFourUserService.getListByRelatedActivityTypeFourId(idList);
    List<ActivityTypeFourPeriods> activityTypeFourPeriodsList = activityTypeFourPeriodsService.getListByRelatedPeriodsNumber(periodsNumberList);
    // loop assembly data;
    for (ActivityTypeFour modelEntity : modelEntityList) {
      // filter, map, and form activityTypeFourUser data;
      List<Map<String, Object>> activityTypeFourUserListData = activityTypeFourUserList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivityTypeFourId()))
          .map(ActivityTypeFourUserSimpleMapper::buildMap)
          .collect(Collectors.toList());
      // filter, map, and form activityTypeFourPeriods data;
      Map<String, Object> activityTypeFourPeriodsData = activityTypeFourPeriodsList.stream()
          .filter(item -> modelEntity.getPeriodsNumber() != null && modelEntity.getPeriodsNumber().equals(item.getPeriodsNumber()))
          .map(ActivityTypeFourPeriodsSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityTypeFourDetailMapper.buildMapExtra(modelEntity,activityTypeFourUserListData,activityTypeFourPeriodsData));
    }
    return entityMapList;
  }

  public ActivityTypeFour post(ActivityTypeFour postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeFourDAO.insertSelective(ActivityTypeFourData.convert(postEntity, new ActivityTypeFourDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeFourPostMapper postMapper) {
    ActivityTypeFour entity = post(postMapper.buildEntity());
    return ActivityTypeFourDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFour> postList(List<ActivityTypeFour> postEntities) {
    List<ActivityTypeFour> entityList = new ArrayList<>();
    for (ActivityTypeFour postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeFourPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFour update(ActivityTypeFour updateEntity) {
    ActivityTypeFour modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeFourDAO.updateByPrimaryKeySelective(ActivityTypeFourData.convert(updateEntity, new ActivityTypeFourDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeFourUpdateMapper updateMapper) {
    ActivityTypeFour entity = update(updateMapper.buildEntity());
    return ActivityTypeFourDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFour> updateList(List<ActivityTypeFour> updateEntities) {
    List<ActivityTypeFour> entityList = new ArrayList<>();
    for (ActivityTypeFour updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeFourUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeFour put(ActivityTypeFour putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeFour modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeFourDAO.insertSelective(ActivityTypeFourData.convert(putEntity, new ActivityTypeFourDO()));
    }
    else {
      activityTypeFourDAO.updateByPrimaryKeySelective(ActivityTypeFourData.convert(putEntity, new ActivityTypeFourDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeFourUpdateMapper putMapper) {
    ActivityTypeFour entity = put(putMapper.buildEntity());
    return ActivityTypeFourDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeFour> putList(List<ActivityTypeFour> putEntities) {
    List<ActivityTypeFour> entityList = new ArrayList<>();
    for (ActivityTypeFour putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeFourUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeFourUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeFourDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeFourFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeFourFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeFourFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeFourFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeFourFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeFourFilterMapper filterMapper,
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
