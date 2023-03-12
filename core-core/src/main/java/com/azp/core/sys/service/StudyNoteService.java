package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.StudyNoteFilter;
import com.azp.core.sys.datainterface.StudyNoteDAO;
import com.azp.core.sys.dataobject.StudyNoteDO;
import com.azp.core.sys.model.ActivityTypeTwoUser;
import com.azp.core.sys.model.ActivityTypeTwoUserFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoUserSimpleMapper;
import com.azp.core.sys.model.StudyNote;
import com.azp.core.sys.model.StudyNoteData;
import com.azp.core.sys.model.StudyNoteDetailMapper;
import com.azp.core.sys.model.StudyNoteFilterMapper;
import com.azp.core.sys.model.StudyNotePostMapper;
import com.azp.core.sys.model.StudyNoteSimpleMapper;
import com.azp.core.sys.model.StudyNoteUpdateMapper;
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
public class StudyNoteService {
  @Autowired
  private StudyNoteDAO studyNoteDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityTypeTwoUserService activityTypeTwoUserService;

  public StudyNote getByPK(String key) {
    StudyNoteDO entity = studyNoteDAO.selectByPrimaryKey(key);
    return StudyNoteData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return StudyNoteSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    StudyNote modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build activityTypeTwoUser data from local database;
    ActivityTypeTwoUserFilterMapper activityTypeTwoUserFilterMapper = new ActivityTypeTwoUserFilterMapper();
    activityTypeTwoUserFilterMapper.id = modelEntity.getActivityTypeTwoUserId();
    activityTypeTwoUserFilterMapper.page = 0L;
    activityTypeTwoUserFilterMapper.rows = 0;
    Map<String, Object> activityTypeTwoUserData = activityTypeTwoUserService.getFilterMapList(activityTypeTwoUserFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return StudyNoteDetailMapper.buildMapExtra(modelEntity,userData,activityTypeTwoUserData);
  }

  public Long getCountByFilter(StudyNoteFilterMapper filterMapper) {
    return studyNoteDAO.countByExample(StudyNoteFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<StudyNote> getListByFilter(StudyNoteFilterMapper filterMapper) {
    List<StudyNote> entityList = new ArrayList<>();
    for (StudyNoteDO entity : studyNoteDAO.selectByExample(StudyNoteFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(StudyNoteData.convert(entity, new StudyNote()));
    }
    return entityList;
  }

  public List<StudyNote> getListByRelatedActivityTypeTwoUserId(List<String> activityTypeTwoUserIdList) {
    List<StudyNote> entityList = new ArrayList<>();
    if (activityTypeTwoUserIdList.size() == 0) return entityList;
    for (StudyNoteDO entity : studyNoteDAO.selectByExample(StudyNoteFilter.initActivityTypeTwoUserIdQueryFilter(activityTypeTwoUserIdList))) {
      entityList.add(StudyNoteData.convert(entity, new StudyNote()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(StudyNoteFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(StudyNoteSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(StudyNoteFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query studyNote data;
    List<StudyNote> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> userIdList = new ArrayList<>();
    ArrayList<String> activityTypeTwoUserIdList = new ArrayList<>();
    for (StudyNote modelEntity : modelEntityList) {
      userIdList.add(modelEntity.getUserId());
      activityTypeTwoUserIdList.add(modelEntity.getActivityTypeTwoUserId());
    }
    // load data from local database or remote service;
    List<User> userList = userService.getListByRelatedId(userIdList);
    List<ActivityTypeTwoUser> activityTypeTwoUserList = activityTypeTwoUserService.getListByRelatedId(activityTypeTwoUserIdList);
    // loop assembly data;
    for (StudyNote modelEntity : modelEntityList) {
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form activityTypeTwoUser data;
      Map<String, Object> activityTypeTwoUserData = activityTypeTwoUserList.stream()
          .filter(item -> modelEntity.getActivityTypeTwoUserId() != null && modelEntity.getActivityTypeTwoUserId().equals(item.getId()))
          .map(ActivityTypeTwoUserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(StudyNoteDetailMapper.buildMapExtra(modelEntity,userData,activityTypeTwoUserData));
    }
    return entityMapList;
  }

  public StudyNote post(StudyNote postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      studyNoteDAO.insertSelective(StudyNoteData.convert(postEntity, new StudyNoteDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(StudyNotePostMapper postMapper) {
    StudyNote entity = post(postMapper.buildEntity());
    return StudyNoteDetailMapper.buildMap(entity);
  }

  public List<StudyNote> postList(List<StudyNote> postEntities) {
    List<StudyNote> entityList = new ArrayList<>();
    for (StudyNote postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<StudyNotePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (StudyNotePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public StudyNote update(StudyNote updateEntity) {
    StudyNote modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    studyNoteDAO.updateByPrimaryKeySelective(StudyNoteData.convert(updateEntity, new StudyNoteDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(StudyNoteUpdateMapper updateMapper) {
    StudyNote entity = update(updateMapper.buildEntity());
    return StudyNoteDetailMapper.buildMap(entity);
  }

  public List<StudyNote> updateList(List<StudyNote> updateEntities) {
    List<StudyNote> entityList = new ArrayList<>();
    for (StudyNote updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<StudyNoteUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (StudyNoteUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public StudyNote put(StudyNote putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    StudyNote modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      studyNoteDAO.insertSelective(StudyNoteData.convert(putEntity, new StudyNoteDO()));
    }
    else {
      studyNoteDAO.updateByPrimaryKeySelective(StudyNoteData.convert(putEntity, new StudyNoteDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(StudyNoteUpdateMapper putMapper) {
    StudyNote entity = put(putMapper.buildEntity());
    return StudyNoteDetailMapper.buildMap(entity);
  }

  public List<StudyNote> putList(List<StudyNote> putEntities) {
    List<StudyNote> entityList = new ArrayList<>();
    for (StudyNote putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<StudyNoteUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (StudyNoteUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(studyNoteDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(StudyNoteFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new StudyNoteFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<StudyNoteFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (StudyNoteFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(StudyNoteFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(StudyNoteFilterMapper filterMapper,
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
