package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeThreeFilter;
import com.azp.core.sys.datainterface.ActivityTypeThreeDAO;
import com.azp.core.sys.dataobject.ActivityTypeThreeDO;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryFilterMapper;
import com.azp.core.sys.model.ActivitySubCategorySimpleMapper;
import com.azp.core.sys.model.ActivityTypeThree;
import com.azp.core.sys.model.ActivityTypeThreeData;
import com.azp.core.sys.model.ActivityTypeThreeDetailMapper;
import com.azp.core.sys.model.ActivityTypeThreeFilterMapper;
import com.azp.core.sys.model.ActivityTypeThreeImage;
import com.azp.core.sys.model.ActivityTypeThreeImageFilterMapper;
import com.azp.core.sys.model.ActivityTypeThreeImageSimpleMapper;
import com.azp.core.sys.model.ActivityTypeThreePostMapper;
import com.azp.core.sys.model.ActivityTypeThreeSimpleMapper;
import com.azp.core.sys.model.ActivityTypeThreeUpdateMapper;
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
public class ActivityTypeThreeService {
  @Autowired
  private ActivityTypeThreeDAO activityTypeThreeDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityTypeThreeImageService activityTypeThreeImageService;

  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  public ActivityTypeThree getByPK(String key) {
    ActivityTypeThreeDO entity = activityTypeThreeDAO.selectByPrimaryKey(key);
    return ActivityTypeThreeData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeThreeSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeThree modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getCreateUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build activityTypeThreeImage data from local database;
    ActivityTypeThreeImageFilterMapper activityTypeThreeImageListFilterMapper = new ActivityTypeThreeImageFilterMapper();
    activityTypeThreeImageListFilterMapper.activityTypeThreeId = modelEntity.getId();
    activityTypeThreeImageListFilterMapper.page = 0L;
    activityTypeThreeImageListFilterMapper.rows = 0;
    List<Map<String, Object>> activityTypeThreeImageListData = activityTypeThreeImageService.getFilterMapList(activityTypeThreeImageListFilterMapper);
    // build activitySubCategory data from local database;
    ActivitySubCategoryFilterMapper activitySubCategoryFilterMapper = new ActivitySubCategoryFilterMapper();
    activitySubCategoryFilterMapper.id = modelEntity.getActivitySubCategoryId();
    activitySubCategoryFilterMapper.page = 0L;
    activitySubCategoryFilterMapper.rows = 0;
    Map<String, Object> activitySubCategoryData = activitySubCategoryService.getFilterMapList(activitySubCategoryFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityTypeThreeDetailMapper.buildMapExtra(modelEntity,userData,activityTypeThreeImageListData,activitySubCategoryData);
  }

  public Long getCountByFilter(ActivityTypeThreeFilterMapper filterMapper) {
    return activityTypeThreeDAO.countByExample(ActivityTypeThreeFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeThree> getListByFilter(ActivityTypeThreeFilterMapper filterMapper) {
    List<ActivityTypeThree> entityList = new ArrayList<>();
    for (ActivityTypeThreeDO entity : activityTypeThreeDAO.selectByExample(ActivityTypeThreeFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeThreeData.convert(entity, new ActivityTypeThree()));
    }
    return entityList;
  }

  public List<ActivityTypeThree> getListByRelatedId(List<String> idList) {
    List<ActivityTypeThree> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (ActivityTypeThreeDO entity : activityTypeThreeDAO.selectByExample(ActivityTypeThreeFilter.initIdQueryFilter(idList))) {
      entityList.add(ActivityTypeThreeData.convert(entity, new ActivityTypeThree()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeThreeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeThreeSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeThreeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeThree data;
    List<ActivityTypeThree> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> createUserIdList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> activitySubCategoryIdList = new ArrayList<>();
    for (ActivityTypeThree modelEntity : modelEntityList) {
      createUserIdList.add(modelEntity.getCreateUserId());
      idList.add(modelEntity.getId());
      activitySubCategoryIdList.add(modelEntity.getActivitySubCategoryId());
    }
    // load data from local database or remote service;
    List<User> userList = userService.getListByRelatedId(createUserIdList);
    List<ActivityTypeThreeImage> activityTypeThreeImageList = activityTypeThreeImageService.getListByRelatedActivityTypeThreeId(idList);
    List<ActivitySubCategory> activitySubCategoryList = activitySubCategoryService.getListByRelatedId(activitySubCategoryIdList);
    // loop assembly data;
    for (ActivityTypeThree modelEntity : modelEntityList) {
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getCreateUserId() != null && modelEntity.getCreateUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form activityTypeThreeImage data;
      List<Map<String, Object>> activityTypeThreeImageListData = activityTypeThreeImageList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivityTypeThreeId()))
          .map(ActivityTypeThreeImageSimpleMapper::buildMap)
          .collect(Collectors.toList());
      // filter, map, and form activitySubCategory data;
      Map<String, Object> activitySubCategoryData = activitySubCategoryList.stream()
          .filter(item -> modelEntity.getActivitySubCategoryId() != null && modelEntity.getActivitySubCategoryId().equals(item.getId()))
          .map(ActivitySubCategorySimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityTypeThreeDetailMapper.buildMapExtra(modelEntity,userData,activityTypeThreeImageListData,activitySubCategoryData));
    }
    return entityMapList;
  }

  public ActivityTypeThree post(ActivityTypeThree postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeThreeDAO.insertSelective(ActivityTypeThreeData.convert(postEntity, new ActivityTypeThreeDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeThreePostMapper postMapper) {
    ActivityTypeThree entity = post(postMapper.buildEntity());
    return ActivityTypeThreeDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeThree> postList(List<ActivityTypeThree> postEntities) {
    List<ActivityTypeThree> entityList = new ArrayList<>();
    for (ActivityTypeThree postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeThreePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeThreePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeThree update(ActivityTypeThree updateEntity) {
    ActivityTypeThree modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeThreeDAO.updateByPrimaryKeySelective(ActivityTypeThreeData.convert(updateEntity, new ActivityTypeThreeDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeThreeUpdateMapper updateMapper) {
    ActivityTypeThree entity = update(updateMapper.buildEntity());
    return ActivityTypeThreeDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeThree> updateList(List<ActivityTypeThree> updateEntities) {
    List<ActivityTypeThree> entityList = new ArrayList<>();
    for (ActivityTypeThree updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeThreeUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeThreeUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeThree put(ActivityTypeThree putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeThree modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeThreeDAO.insertSelective(ActivityTypeThreeData.convert(putEntity, new ActivityTypeThreeDO()));
    }
    else {
      activityTypeThreeDAO.updateByPrimaryKeySelective(ActivityTypeThreeData.convert(putEntity, new ActivityTypeThreeDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeThreeUpdateMapper putMapper) {
    ActivityTypeThree entity = put(putMapper.buildEntity());
    return ActivityTypeThreeDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeThree> putList(List<ActivityTypeThree> putEntities) {
    List<ActivityTypeThree> entityList = new ArrayList<>();
    for (ActivityTypeThree putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeThreeUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeThreeUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeThreeDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeThreeFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeThreeFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeThreeFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeThreeFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeThreeFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeThreeFilterMapper filterMapper,
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
