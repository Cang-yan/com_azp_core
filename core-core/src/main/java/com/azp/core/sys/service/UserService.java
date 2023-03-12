package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.UserFilter;
import com.azp.core.sys.datainterface.UserDAO;
import com.azp.core.sys.dataobject.UserDO;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentFilterMapper;
import com.azp.core.sys.model.DepartmentSimpleMapper;
import com.azp.core.sys.model.Group;
import com.azp.core.sys.model.GroupFilterMapper;
import com.azp.core.sys.model.GroupSimpleMapper;
import com.azp.core.sys.model.UseInfo;
import com.azp.core.sys.model.UseInfoFilterMapper;
import com.azp.core.sys.model.UseInfoSimpleMapper;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserData;
import com.azp.core.sys.model.UserDetailMapper;
import com.azp.core.sys.model.UserFilterMapper;
import com.azp.core.sys.model.UserPostMapper;
import com.azp.core.sys.model.UserSimpleMapper;
import com.azp.core.sys.model.UserUpdateMapper;
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
public class UserService {
  @Autowired
  private UserDAO userDAO;

  @Autowired
  private DepartmentService departmentService;

  @Autowired
  private GroupService groupService;

  @Autowired
  private UseInfoService useInfoService;

  public User getByPK(String key) {
    UserDO entity = userDAO.selectByPrimaryKey(key);
    return UserData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return UserSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    User modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build department data from local database;
    DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
    departmentFilterMapper.id = modelEntity.getDepartmentId();
    departmentFilterMapper.page = 0L;
    departmentFilterMapper.rows = 0;
    Map<String, Object> departmentData = departmentService.getFilterMapList(departmentFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build group data from local database;
    GroupFilterMapper groupFilterMapper = new GroupFilterMapper();
    groupFilterMapper.id = modelEntity.getGroupId();
    groupFilterMapper.page = 0L;
    groupFilterMapper.rows = 0;
    Map<String, Object> groupData = groupService.getFilterMapList(groupFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build useInfo data from local database;
    UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
    useInfoFilterMapper.id = modelEntity.getUserInfoId();
    useInfoFilterMapper.page = 0L;
    useInfoFilterMapper.rows = 0;
    Map<String, Object> useInfoData = useInfoService.getFilterMapList(useInfoFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return UserDetailMapper.buildMapExtra(modelEntity,departmentData,groupData,useInfoData);
  }

  public Long getCountByFilter(UserFilterMapper filterMapper) {
    return userDAO.countByExample(UserFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<User> getListByFilter(UserFilterMapper filterMapper) {
    List<User> entityList = new ArrayList<>();
    for (UserDO entity : userDAO.selectByExample(UserFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(UserData.convert(entity, new User()));
    }
    return entityList;
  }

  public List<User> getListByRelatedId(List<String> idList) {
    List<User> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (UserDO entity : userDAO.selectByExample(UserFilter.initIdQueryFilter(idList))) {
      entityList.add(UserData.convert(entity, new User()));
    }
    return entityList;
  }

  public List<User> getListByRelatedDepartmentId(List<String> departmentIdList) {
    List<User> entityList = new ArrayList<>();
    if (departmentIdList.size() == 0) return entityList;
    for (UserDO entity : userDAO.selectByExample(UserFilter.initDepartmentIdQueryFilter(departmentIdList))) {
      entityList.add(UserData.convert(entity, new User()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(UserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(UserSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(UserFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query user data;
    List<User> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> departmentIdList = new ArrayList<>();
    ArrayList<String> groupIdList = new ArrayList<>();
    ArrayList<String> userInfoIdList = new ArrayList<>();
    for (User modelEntity : modelEntityList) {
      departmentIdList.add(modelEntity.getDepartmentId());
      groupIdList.add(modelEntity.getGroupId());
      userInfoIdList.add(modelEntity.getUserInfoId());
    }
    // load data from local database or remote service;
    List<Department> departmentList = departmentService.getListByRelatedId(departmentIdList);
    List<Group> groupList = groupService.getListByRelatedId(groupIdList);
    List<UseInfo> useInfoList = useInfoService.getListByRelatedId(userInfoIdList);
    // loop assembly data;
    for (User modelEntity : modelEntityList) {
      // filter, map, and form department data;
      Map<String, Object> departmentData = departmentList.stream()
          .filter(item -> modelEntity.getDepartmentId() != null && modelEntity.getDepartmentId().equals(item.getId()))
          .map(DepartmentSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form group data;
      Map<String, Object> groupData = groupList.stream()
          .filter(item -> modelEntity.getGroupId() != null && modelEntity.getGroupId().equals(item.getId()))
          .map(GroupSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form useInfo data;
      Map<String, Object> useInfoData = useInfoList.stream()
          .filter(item -> modelEntity.getUserInfoId() != null && modelEntity.getUserInfoId().equals(item.getId()))
          .map(UseInfoSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(UserDetailMapper.buildMapExtra(modelEntity,departmentData,groupData,useInfoData));
    }
    return entityMapList;
  }

  public User post(User postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      userDAO.insertSelective(UserData.convert(postEntity, new UserDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(UserPostMapper postMapper) {
    User entity = post(postMapper.buildEntity());
    return UserDetailMapper.buildMap(entity);
  }

  public List<User> postList(List<User> postEntities) {
    List<User> entityList = new ArrayList<>();
    for (User postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<UserPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public User update(User updateEntity) {
    User modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    userDAO.updateByPrimaryKeySelective(UserData.convert(updateEntity, new UserDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(UserUpdateMapper updateMapper) {
    User entity = update(updateMapper.buildEntity());
    return UserDetailMapper.buildMap(entity);
  }

  public List<User> updateList(List<User> updateEntities) {
    List<User> entityList = new ArrayList<>();
    for (User updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<UserUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public User put(User putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    User modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      userDAO.insertSelective(UserData.convert(putEntity, new UserDO()));
    }
    else {
      userDAO.updateByPrimaryKeySelective(UserData.convert(putEntity, new UserDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(UserUpdateMapper putMapper) {
    User entity = put(putMapper.buildEntity());
    return UserDetailMapper.buildMap(entity);
  }

  public List<User> putList(List<User> putEntities) {
    List<User> entityList = new ArrayList<>();
    for (User putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<UserUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(userDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(UserFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new UserFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<UserFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (UserFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(UserFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(UserFilterMapper filterMapper,
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
