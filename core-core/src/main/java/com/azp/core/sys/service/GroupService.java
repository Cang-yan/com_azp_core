package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.GroupFilter;
import com.azp.core.sys.datainterface.GroupDAO;
import com.azp.core.sys.dataobject.GroupDO;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentFilterMapper;
import com.azp.core.sys.model.DepartmentSimpleMapper;
import com.azp.core.sys.model.Group;
import com.azp.core.sys.model.GroupData;
import com.azp.core.sys.model.GroupDetailMapper;
import com.azp.core.sys.model.GroupFilterMapper;
import com.azp.core.sys.model.GroupPostMapper;
import com.azp.core.sys.model.GroupSimpleMapper;
import com.azp.core.sys.model.GroupUpdateMapper;
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
public class GroupService {
  @Autowired
  private GroupDAO groupDAO;

  @Autowired
  private DepartmentService departmentService;

  public Group getByPK(String key) {
    GroupDO entity = groupDAO.selectByPrimaryKey(key);
    return GroupData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return GroupSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    Group modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build department data from local database;
    DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
    departmentFilterMapper.id = modelEntity.getDepartmentId();
    departmentFilterMapper.page = 0L;
    departmentFilterMapper.rows = 0;
    Map<String, Object> departmentData = departmentService.getFilterMapList(departmentFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return GroupDetailMapper.buildMapExtra(modelEntity,departmentData);
  }

  public Long getCountByFilter(GroupFilterMapper filterMapper) {
    return groupDAO.countByExample(GroupFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<Group> getListByFilter(GroupFilterMapper filterMapper) {
    List<Group> entityList = new ArrayList<>();
    for (GroupDO entity : groupDAO.selectByExample(GroupFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(GroupData.convert(entity, new Group()));
    }
    return entityList;
  }

  public List<Group> getListByRelatedId(List<String> idList) {
    List<Group> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (GroupDO entity : groupDAO.selectByExample(GroupFilter.initIdQueryFilter(idList))) {
      entityList.add(GroupData.convert(entity, new Group()));
    }
    return entityList;
  }

  public List<Group> getListByRelatedDepartmentId(List<String> departmentIdList) {
    List<Group> entityList = new ArrayList<>();
    if (departmentIdList.size() == 0) return entityList;
    for (GroupDO entity : groupDAO.selectByExample(GroupFilter.initDepartmentIdQueryFilter(departmentIdList))) {
      entityList.add(GroupData.convert(entity, new Group()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(GroupFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(GroupSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(GroupFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query group data;
    List<Group> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> departmentIdList = new ArrayList<>();
    for (Group modelEntity : modelEntityList) {
      departmentIdList.add(modelEntity.getDepartmentId());
    }
    // load data from local database or remote service;
    List<Department> departmentList = departmentService.getListByRelatedId(departmentIdList);
    // loop assembly data;
    for (Group modelEntity : modelEntityList) {
      // filter, map, and form department data;
      Map<String, Object> departmentData = departmentList.stream()
          .filter(item -> modelEntity.getDepartmentId() != null && modelEntity.getDepartmentId().equals(item.getId()))
          .map(DepartmentSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(GroupDetailMapper.buildMapExtra(modelEntity,departmentData));
    }
    return entityMapList;
  }

  public Group post(Group postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      groupDAO.insertSelective(GroupData.convert(postEntity, new GroupDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(GroupPostMapper postMapper) {
    Group entity = post(postMapper.buildEntity());
    return GroupDetailMapper.buildMap(entity);
  }

  public List<Group> postList(List<Group> postEntities) {
    List<Group> entityList = new ArrayList<>();
    for (Group postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<GroupPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (GroupPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public Group update(Group updateEntity) {
    Group modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    groupDAO.updateByPrimaryKeySelective(GroupData.convert(updateEntity, new GroupDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(GroupUpdateMapper updateMapper) {
    Group entity = update(updateMapper.buildEntity());
    return GroupDetailMapper.buildMap(entity);
  }

  public List<Group> updateList(List<Group> updateEntities) {
    List<Group> entityList = new ArrayList<>();
    for (Group updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<GroupUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (GroupUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public Group put(Group putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    Group modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      groupDAO.insertSelective(GroupData.convert(putEntity, new GroupDO()));
    }
    else {
      groupDAO.updateByPrimaryKeySelective(GroupData.convert(putEntity, new GroupDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(GroupUpdateMapper putMapper) {
    Group entity = put(putMapper.buildEntity());
    return GroupDetailMapper.buildMap(entity);
  }

  public List<Group> putList(List<Group> putEntities) {
    List<Group> entityList = new ArrayList<>();
    for (Group putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<GroupUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (GroupUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(groupDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(GroupFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new GroupFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<GroupFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (GroupFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(GroupFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(GroupFilterMapper filterMapper,
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
