package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivitySubCategoryFilter;
import com.azp.core.sys.datainterface.ActivitySubCategoryDAO;
import com.azp.core.sys.dataobject.ActivitySubCategoryDO;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryData;
import com.azp.core.sys.model.ActivitySubCategoryDetailMapper;
import com.azp.core.sys.model.ActivitySubCategoryFilterMapper;
import com.azp.core.sys.model.ActivitySubCategoryPostMapper;
import com.azp.core.sys.model.ActivitySubCategorySimpleMapper;
import com.azp.core.sys.model.ActivitySubCategoryUpdateMapper;
import com.azp.core.sys.model.ActivityTypeFive;
import com.azp.core.sys.model.ActivityTypeFiveFilterMapper;
import com.azp.core.sys.model.ActivityTypeFiveSimpleMapper;
import com.azp.core.sys.model.ActivityTypeFour;
import com.azp.core.sys.model.ActivityTypeFourFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourSimpleMapper;
import com.azp.core.sys.model.ActivityTypeOne;
import com.azp.core.sys.model.ActivityTypeOneFilterMapper;
import com.azp.core.sys.model.ActivityTypeOneSimpleMapper;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoSimpleMapper;
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
public class ActivitySubCategoryService {
  @Autowired
  private ActivitySubCategoryDAO activitySubCategoryDAO;

  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  @Autowired
  private ActivityTypeTwoService activityTypeTwoService;

  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @Autowired
  private ActivityTypeFiveService activityTypeFiveService;

  public ActivitySubCategory getByPK(String key) {
    ActivitySubCategoryDO entity = activitySubCategoryDAO.selectByPrimaryKey(key);
    return ActivitySubCategoryData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivitySubCategorySimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivitySubCategory modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activityTypeOne data from local database;
    ActivityTypeOneFilterMapper activityTypeOneListFilterMapper = new ActivityTypeOneFilterMapper();
    activityTypeOneListFilterMapper.activitySubCategoryId = modelEntity.getId();
    activityTypeOneListFilterMapper.page = 0L;
    activityTypeOneListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeOneListData = activityTypeOneService.getFilterMapList(activityTypeOneListFilterMapper);
    // build activityTypeTwo data from local database;
    ActivityTypeTwoFilterMapper activityTypeTwoListFilterMapper = new ActivityTypeTwoFilterMapper();
    activityTypeTwoListFilterMapper.activitySubCategoryId = modelEntity.getId();
    activityTypeTwoListFilterMapper.page = 0L;
    activityTypeTwoListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeTwoListData = activityTypeTwoService.getFilterMapList(activityTypeTwoListFilterMapper);
    // build activityTypeFour data from local database;
    ActivityTypeFourFilterMapper activityTypeFourListFilterMapper = new ActivityTypeFourFilterMapper();
    activityTypeFourListFilterMapper.activitySubCategoryId = modelEntity.getId();
    activityTypeFourListFilterMapper.page = 0L;
    activityTypeFourListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeFourListData = activityTypeFourService.getFilterMapList(activityTypeFourListFilterMapper);
    // build activityTypeFive data from local database;
    ActivityTypeFiveFilterMapper activityTypeFiveListFilterMapper = new ActivityTypeFiveFilterMapper();
    activityTypeFiveListFilterMapper.activitySubCategory = modelEntity.getId();
    activityTypeFiveListFilterMapper.page = 0L;
    activityTypeFiveListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeFiveListData = activityTypeFiveService.getFilterMapList(activityTypeFiveListFilterMapper);
    return ActivitySubCategoryDetailMapper.buildMapExtra(modelEntity,activityTypeOneListData,activityTypeTwoListData,activityTypeFourListData,activityTypeFiveListData);
  }

  public Long getCountByFilter(ActivitySubCategoryFilterMapper filterMapper) {
    return activitySubCategoryDAO.countByExample(ActivitySubCategoryFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivitySubCategory> getListByFilter(ActivitySubCategoryFilterMapper filterMapper) {
    List<ActivitySubCategory> entityList = new ArrayList<>();
    for (ActivitySubCategoryDO entity : activitySubCategoryDAO.selectByExample(ActivitySubCategoryFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivitySubCategoryData.convert(entity, new ActivitySubCategory()));
    }
    return entityList;
  }

  public List<ActivitySubCategory> getListByRelatedActivityCategoryId(List<String> activityCategoryIdList) {
    List<ActivitySubCategory> entityList = new ArrayList<>();
    if (activityCategoryIdList.size() == 0) return entityList;
    for (ActivitySubCategoryDO entity : activitySubCategoryDAO.selectByExample(ActivitySubCategoryFilter.initActivityCategoryIdQueryFilter(activityCategoryIdList))) {
      entityList.add(ActivitySubCategoryData.convert(entity, new ActivitySubCategory()));
    }
    return entityList;
  }

  public List<ActivitySubCategory> getListByRelatedId(List<String> idList) {
    List<ActivitySubCategory> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (ActivitySubCategoryDO entity : activitySubCategoryDAO.selectByExample(ActivitySubCategoryFilter.initIdQueryFilter(idList))) {
      entityList.add(ActivitySubCategoryData.convert(entity, new ActivitySubCategory()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivitySubCategoryFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivitySubCategorySimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivitySubCategoryFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activitySubCategory data;
    List<ActivitySubCategory> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> idList = new ArrayList<>();
    for (ActivitySubCategory modelEntity : modelEntityList) {
      idList.add(modelEntity.getId());
    }
    // load data from local database or remote service;
    List<ActivityTypeOne> activityTypeOneList = activityTypeOneService.getListByRelatedActivitySubCategoryId(idList);
    List<ActivityTypeTwo> activityTypeTwoList = activityTypeTwoService.getListByRelatedActivitySubCategoryId(idList);
    List<ActivityTypeFour> activityTypeFourList = activityTypeFourService.getListByRelatedActivitySubCategoryId(idList);
    List<ActivityTypeFive> activityTypeFiveList = activityTypeFiveService.getListByRelatedActivitySubCategory(idList);
    // loop assembly data;
    for (ActivitySubCategory modelEntity : modelEntityList) {
      // filter, map, and form activityTypeOne data;
      List<Map<String, Object>> activityTypeOneListData = activityTypeOneList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivitySubCategoryId()))
          .map(ActivityTypeOneSimpleMapper::buildMap)
          .collect(Collectors.toList());
      // filter, map, and form activityTypeTwo data;
      List<Map<String, Object>> activityTypeTwoListData = activityTypeTwoList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivitySubCategoryId()))
          .map(ActivityTypeTwoSimpleMapper::buildMap)
          .collect(Collectors.toList());
      // filter, map, and form activityTypeFour data;
      List<Map<String, Object>> activityTypeFourListData = activityTypeFourList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivitySubCategoryId()))
          .map(ActivityTypeFourSimpleMapper::buildMap)
          .collect(Collectors.toList());
      // filter, map, and form activityTypeFive data;
      List<Map<String, Object>> activityTypeFiveListData = activityTypeFiveList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivitySubCategory()))
          .map(ActivityTypeFiveSimpleMapper::buildMap)
          .collect(Collectors.toList());
      entityMapList.add(ActivitySubCategoryDetailMapper.buildMapExtra(modelEntity,activityTypeOneListData,activityTypeTwoListData,activityTypeFourListData,activityTypeFiveListData));
    }
    return entityMapList;
  }

  public ActivitySubCategory post(ActivitySubCategory postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activitySubCategoryDAO.insertSelective(ActivitySubCategoryData.convert(postEntity, new ActivitySubCategoryDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivitySubCategoryPostMapper postMapper) {
    ActivitySubCategory entity = post(postMapper.buildEntity());
    return ActivitySubCategoryDetailMapper.buildMap(entity);
  }

  public List<ActivitySubCategory> postList(List<ActivitySubCategory> postEntities) {
    List<ActivitySubCategory> entityList = new ArrayList<>();
    for (ActivitySubCategory postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivitySubCategoryPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivitySubCategoryPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivitySubCategory update(ActivitySubCategory updateEntity) {
    ActivitySubCategory modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activitySubCategoryDAO.updateByPrimaryKeySelective(ActivitySubCategoryData.convert(updateEntity, new ActivitySubCategoryDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivitySubCategoryUpdateMapper updateMapper) {
    ActivitySubCategory entity = update(updateMapper.buildEntity());
    return ActivitySubCategoryDetailMapper.buildMap(entity);
  }

  public List<ActivitySubCategory> updateList(List<ActivitySubCategory> updateEntities) {
    List<ActivitySubCategory> entityList = new ArrayList<>();
    for (ActivitySubCategory updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivitySubCategoryUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivitySubCategoryUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivitySubCategory put(ActivitySubCategory putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivitySubCategory modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activitySubCategoryDAO.insertSelective(ActivitySubCategoryData.convert(putEntity, new ActivitySubCategoryDO()));
    }
    else {
      activitySubCategoryDAO.updateByPrimaryKeySelective(ActivitySubCategoryData.convert(putEntity, new ActivitySubCategoryDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivitySubCategoryUpdateMapper putMapper) {
    ActivitySubCategory entity = put(putMapper.buildEntity());
    return ActivitySubCategoryDetailMapper.buildMap(entity);
  }

  public List<ActivitySubCategory> putList(List<ActivitySubCategory> putEntities) {
    List<ActivitySubCategory> entityList = new ArrayList<>();
    for (ActivitySubCategory putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivitySubCategoryUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivitySubCategoryUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activitySubCategoryDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivitySubCategoryFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivitySubCategoryFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivitySubCategoryFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivitySubCategoryFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivitySubCategoryFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivitySubCategoryFilterMapper filterMapper,
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
