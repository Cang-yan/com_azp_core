package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityTypeTwoUserFilter;
import com.azp.core.sys.datainterface.ActivityTypeTwoUserDAO;
import com.azp.core.sys.dataobject.ActivityTypeTwoUserDO;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoSimpleMapper;
import com.azp.core.sys.model.ActivityTypeTwoUser;
import com.azp.core.sys.model.ActivityTypeTwoUserData;
import com.azp.core.sys.model.ActivityTypeTwoUserDetailMapper;
import com.azp.core.sys.model.ActivityTypeTwoUserFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoUserPostMapper;
import com.azp.core.sys.model.ActivityTypeTwoUserSimpleMapper;
import com.azp.core.sys.model.ActivityTypeTwoUserUpdateMapper;
import com.azp.core.sys.model.StudyNote;
import com.azp.core.sys.model.StudyNoteFilterMapper;
import com.azp.core.sys.model.StudyNoteSimpleMapper;
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
public class ActivityTypeTwoUserService {
  @Autowired
  private ActivityTypeTwoUserDAO activityTypeTwoUserDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityTypeTwoService activityTypeTwoService;

  @Autowired
  private StudyNoteService studyNoteService;

  public ActivityTypeTwoUser getByPK(String key) {
    ActivityTypeTwoUserDO entity = activityTypeTwoUserDAO.selectByPrimaryKey(key);
    return ActivityTypeTwoUserData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityTypeTwoUserSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityTypeTwoUser modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build activityTypeTwo data from local database;
    ActivityTypeTwoFilterMapper activityTypeTwoFilterMapper = new ActivityTypeTwoFilterMapper();
    activityTypeTwoFilterMapper.id = modelEntity.getActivityTypeTwoId();
    activityTypeTwoFilterMapper.page = 0L;
    activityTypeTwoFilterMapper.rows = 0;
    Map<String, Object> activityTypeTwoData = activityTypeTwoService.getFilterMapList(activityTypeTwoFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build studyNote data from local database;
    StudyNoteFilterMapper studyNoteFilterMapper = new StudyNoteFilterMapper();
    studyNoteFilterMapper.activityTypeTwoUserId = modelEntity.getId();
    studyNoteFilterMapper.page = 0L;
    studyNoteFilterMapper.rows = 0;
    Map<String, Object> studyNoteData = studyNoteService.getFilterMapList(studyNoteFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityTypeTwoUserDetailMapper.buildMapExtra(modelEntity,userData,activityTypeTwoData,studyNoteData);
  }

  public Long getCountByFilter(ActivityTypeTwoUserFilterMapper filterMapper) {
    return activityTypeTwoUserDAO.countByExample(ActivityTypeTwoUserFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityTypeTwoUser> getListByFilter(ActivityTypeTwoUserFilterMapper filterMapper) {
    List<ActivityTypeTwoUser> entityList = new ArrayList<>();
    for (ActivityTypeTwoUserDO entity : activityTypeTwoUserDAO.selectByExample(ActivityTypeTwoUserFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityTypeTwoUserData.convert(entity, new ActivityTypeTwoUser()));
    }
    return entityList;
  }

  public List<ActivityTypeTwoUser> getListByRelatedId(List<String> idList) {
    List<ActivityTypeTwoUser> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (ActivityTypeTwoUserDO entity : activityTypeTwoUserDAO.selectByExample(ActivityTypeTwoUserFilter.initIdQueryFilter(idList))) {
      entityList.add(ActivityTypeTwoUserData.convert(entity, new ActivityTypeTwoUser()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityTypeTwoUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityTypeTwoUserSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityTypeTwoUserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityTypeTwoUser data;
    List<ActivityTypeTwoUser> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> userIdList = new ArrayList<>();
    ArrayList<String> activityTypeTwoIdList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    for (ActivityTypeTwoUser modelEntity : modelEntityList) {
      idList.add(modelEntity.getId());
      userIdList.add(modelEntity.getUserId());
      activityTypeTwoIdList.add(modelEntity.getActivityTypeTwoId());
    }
    // load data from local database or remote service;
    List<User> userList = userService.getListByRelatedId(userIdList);
    List<ActivityTypeTwo> activityTypeTwoList = activityTypeTwoService.getListByRelatedId(activityTypeTwoIdList);
    List<StudyNote> studyNoteList = studyNoteService.getListByRelatedActivityTypeTwoUserId(idList);
    // loop assembly data;
    for (ActivityTypeTwoUser modelEntity : modelEntityList) {
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form activityTypeTwo data;
      Map<String, Object> activityTypeTwoData = activityTypeTwoList.stream()
          .filter(item -> modelEntity.getActivityTypeTwoId() != null && modelEntity.getActivityTypeTwoId().equals(item.getId()))
          .map(ActivityTypeTwoSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form studyNote data;
      Map<String, Object> studyNoteData = studyNoteList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getActivityTypeTwoUserId()))
          .map(StudyNoteSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityTypeTwoUserDetailMapper.buildMapExtra(modelEntity,userData,activityTypeTwoData,studyNoteData));
    }
    return entityMapList;
  }

  public ActivityTypeTwoUser post(ActivityTypeTwoUser postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityTypeTwoUserDAO.insertSelective(ActivityTypeTwoUserData.convert(postEntity, new ActivityTypeTwoUserDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityTypeTwoUserPostMapper postMapper) {
    ActivityTypeTwoUser entity = post(postMapper.buildEntity());
    return ActivityTypeTwoUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeTwoUser> postList(List<ActivityTypeTwoUser> postEntities) {
    List<ActivityTypeTwoUser> entityList = new ArrayList<>();
    for (ActivityTypeTwoUser postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityTypeTwoUserPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeTwoUserPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityTypeTwoUser update(ActivityTypeTwoUser updateEntity) {
    ActivityTypeTwoUser modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityTypeTwoUserDAO.updateByPrimaryKeySelective(ActivityTypeTwoUserData.convert(updateEntity, new ActivityTypeTwoUserDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityTypeTwoUserUpdateMapper updateMapper) {
    ActivityTypeTwoUser entity = update(updateMapper.buildEntity());
    return ActivityTypeTwoUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeTwoUser> updateList(List<ActivityTypeTwoUser> updateEntities) {
    List<ActivityTypeTwoUser> entityList = new ArrayList<>();
    for (ActivityTypeTwoUser updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityTypeTwoUserUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeTwoUserUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityTypeTwoUser put(ActivityTypeTwoUser putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityTypeTwoUser modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityTypeTwoUserDAO.insertSelective(ActivityTypeTwoUserData.convert(putEntity, new ActivityTypeTwoUserDO()));
    }
    else {
      activityTypeTwoUserDAO.updateByPrimaryKeySelective(ActivityTypeTwoUserData.convert(putEntity, new ActivityTypeTwoUserDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityTypeTwoUserUpdateMapper putMapper) {
    ActivityTypeTwoUser entity = put(putMapper.buildEntity());
    return ActivityTypeTwoUserDetailMapper.buildMap(entity);
  }

  public List<ActivityTypeTwoUser> putList(List<ActivityTypeTwoUser> putEntities) {
    List<ActivityTypeTwoUser> entityList = new ArrayList<>();
    for (ActivityTypeTwoUser putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityTypeTwoUserUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityTypeTwoUserUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityTypeTwoUserDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityTypeTwoUserFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityTypeTwoUserFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityTypeTwoUserFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityTypeTwoUserFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityTypeTwoUserFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityTypeTwoUserFilterMapper filterMapper,
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
