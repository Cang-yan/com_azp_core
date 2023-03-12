package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.DepartmentFilter;
import com.azp.core.sys.datainterface.DepartmentDAO;
import com.azp.core.sys.dataobject.DepartmentDO;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentData;
import com.azp.core.sys.model.DepartmentDetailMapper;
import com.azp.core.sys.model.DepartmentFilterMapper;
import com.azp.core.sys.model.DepartmentPoint;
import com.azp.core.sys.model.DepartmentPointFilterMapper;
import com.azp.core.sys.model.DepartmentPointSimpleMapper;
import com.azp.core.sys.model.DepartmentPostMapper;
import com.azp.core.sys.model.DepartmentSimpleMapper;
import com.azp.core.sys.model.DepartmentUpdateMapper;
import com.azp.core.sys.model.Group;
import com.azp.core.sys.model.GroupFilterMapper;
import com.azp.core.sys.model.GroupSimpleMapper;
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
public class DepartmentService {
  @Autowired
  private DepartmentDAO departmentDAO;

  @Autowired
  private DepartmentPointService departmentPointService;

  @Autowired
  private UserService userService;

  @Autowired
  private GroupService groupService;

  public Department getByPK(String key) {
    DepartmentDO entity = departmentDAO.selectByPrimaryKey(key);
    return DepartmentData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return DepartmentSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    Department modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build departmentPoint data from local database;
    DepartmentPointFilterMapper departmentPointFilterMapper = new DepartmentPointFilterMapper();
    departmentPointFilterMapper.id = modelEntity.getDepartmentPointId();
    departmentPointFilterMapper.page = 0L;
    departmentPointFilterMapper.rows = 0;
    Map<String, Object> departmentPointData = departmentPointService.getFilterMapList(departmentPointFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build user data from local database;
    UserFilterMapper userListFilterMapper = new UserFilterMapper();
    userListFilterMapper.departmentId = modelEntity.getId();
    userListFilterMapper.page = 0L;
    userListFilterMapper.rows = 0;
    List<Map<String, Object>> userListData = userService.getFilterMapList(userListFilterMapper);
    // build group data from local database;
    GroupFilterMapper groupListFilterMapper = new GroupFilterMapper();
    groupListFilterMapper.departmentId = modelEntity.getId();
    groupListFilterMapper.page = 0L;
    groupListFilterMapper.rows = 0;
    List<Map<String, Object>> groupListData = groupService.getFilterMapList(groupListFilterMapper);
    return DepartmentDetailMapper.buildMapExtra(modelEntity,departmentPointData,userListData,groupListData);
  }

  public Long getCountByFilter(DepartmentFilterMapper filterMapper) {
    return departmentDAO.countByExample(DepartmentFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<Department> getListByFilter(DepartmentFilterMapper filterMapper) {
    List<Department> entityList = new ArrayList<>();
    for (DepartmentDO entity : departmentDAO.selectByExample(DepartmentFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(DepartmentData.convert(entity, new Department()));
    }
    return entityList;
  }

  public List<Department> getListByRelatedId(List<String> idList) {
    List<Department> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (DepartmentDO entity : departmentDAO.selectByExample(DepartmentFilter.initIdQueryFilter(idList))) {
      entityList.add(DepartmentData.convert(entity, new Department()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(DepartmentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(DepartmentSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(DepartmentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query department data;
    List<Department> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> departmentPointIdList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    for (Department modelEntity : modelEntityList) {
      departmentPointIdList.add(modelEntity.getDepartmentPointId());
      idList.add(modelEntity.getId());
    }
    // load data from local database or remote service;
    List<DepartmentPoint> departmentPointList = departmentPointService.getListByRelatedId(departmentPointIdList);
    List<User> userList = userService.getListByRelatedDepartmentId(idList);
    List<Group> groupList = groupService.getListByRelatedDepartmentId(idList);
    // loop assembly data;
    for (Department modelEntity : modelEntityList) {
      // filter, map, and form departmentPoint data;
      Map<String, Object> departmentPointData = departmentPointList.stream()
          .filter(item -> modelEntity.getDepartmentPointId() != null && modelEntity.getDepartmentPointId().equals(item.getId()))
          .map(DepartmentPointSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form user data;
      List<Map<String, Object>> userListData = userList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getDepartmentId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList());
      // filter, map, and form group data;
      List<Map<String, Object>> groupListData = groupList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getDepartmentId()))
          .map(GroupSimpleMapper::buildMap)
          .collect(Collectors.toList());
      entityMapList.add(DepartmentDetailMapper.buildMapExtra(modelEntity,departmentPointData,userListData,groupListData));
    }
    return entityMapList;
  }

  public Department post(Department postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      departmentDAO.insertSelective(DepartmentData.convert(postEntity, new DepartmentDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(DepartmentPostMapper postMapper) {
    Department entity = post(postMapper.buildEntity());
    return DepartmentDetailMapper.buildMap(entity);
  }

  public List<Department> postList(List<Department> postEntities) {
    List<Department> entityList = new ArrayList<>();
    for (Department postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<DepartmentPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (DepartmentPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public Department update(Department updateEntity) {
    Department modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    departmentDAO.updateByPrimaryKeySelective(DepartmentData.convert(updateEntity, new DepartmentDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(DepartmentUpdateMapper updateMapper) {
    Department entity = update(updateMapper.buildEntity());
    return DepartmentDetailMapper.buildMap(entity);
  }

  public List<Department> updateList(List<Department> updateEntities) {
    List<Department> entityList = new ArrayList<>();
    for (Department updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<DepartmentUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (DepartmentUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public Department put(Department putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    Department modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      departmentDAO.insertSelective(DepartmentData.convert(putEntity, new DepartmentDO()));
    }
    else {
      departmentDAO.updateByPrimaryKeySelective(DepartmentData.convert(putEntity, new DepartmentDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(DepartmentUpdateMapper putMapper) {
    Department entity = put(putMapper.buildEntity());
    return DepartmentDetailMapper.buildMap(entity);
  }

  public List<Department> putList(List<Department> putEntities) {
    List<Department> entityList = new ArrayList<>();
    for (Department putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<DepartmentUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (DepartmentUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(departmentDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(DepartmentFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new DepartmentFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<DepartmentFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (DepartmentFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(DepartmentFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(DepartmentFilterMapper filterMapper,
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
