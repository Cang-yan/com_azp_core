package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ActivityDepartmentSelectFilter;
import com.azp.core.sys.datainterface.ActivityDepartmentSelectDAO;
import com.azp.core.sys.dataobject.ActivityDepartmentSelectDO;
import com.azp.core.sys.model.ActivityDepartmentSelect;
import com.azp.core.sys.model.ActivityDepartmentSelectData;
import com.azp.core.sys.model.ActivityDepartmentSelectDetailMapper;
import com.azp.core.sys.model.ActivityDepartmentSelectFilterMapper;
import com.azp.core.sys.model.ActivityDepartmentSelectPostMapper;
import com.azp.core.sys.model.ActivityDepartmentSelectSimpleMapper;
import com.azp.core.sys.model.ActivityDepartmentSelectUpdateMapper;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentFilterMapper;
import com.azp.core.sys.model.DepartmentSimpleMapper;
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
public class ActivityDepartmentSelectService {
  @Autowired
  private ActivityDepartmentSelectDAO activityDepartmentSelectDAO;

  @Autowired
  private DepartmentService departmentService;

  public ActivityDepartmentSelect getByPK(String key) {
    ActivityDepartmentSelectDO entity = activityDepartmentSelectDAO.selectByPrimaryKey(key);
    return ActivityDepartmentSelectData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ActivityDepartmentSelectSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ActivityDepartmentSelect modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build department data from local database;
    DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
    departmentFilterMapper.id = modelEntity.getDepartmentId();
    departmentFilterMapper.page = 0L;
    departmentFilterMapper.rows = 0;
    Map<String, Object> departmentData = departmentService.getFilterMapList(departmentFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return ActivityDepartmentSelectDetailMapper.buildMapExtra(modelEntity,departmentData);
  }

  public Long getCountByFilter(ActivityDepartmentSelectFilterMapper filterMapper) {
    return activityDepartmentSelectDAO.countByExample(ActivityDepartmentSelectFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ActivityDepartmentSelect> getListByFilter(ActivityDepartmentSelectFilterMapper filterMapper) {
    List<ActivityDepartmentSelect> entityList = new ArrayList<>();
    for (ActivityDepartmentSelectDO entity : activityDepartmentSelectDAO.selectByExample(ActivityDepartmentSelectFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ActivityDepartmentSelectData.convert(entity, new ActivityDepartmentSelect()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ActivityDepartmentSelectFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ActivityDepartmentSelectSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(ActivityDepartmentSelectFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query activityDepartmentSelect data;
    List<ActivityDepartmentSelect> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> departmentIdList = new ArrayList<>();
    for (ActivityDepartmentSelect modelEntity : modelEntityList) {
      departmentIdList.add(modelEntity.getDepartmentId());
    }
    // load data from local database or remote service;
    List<Department> departmentList = departmentService.getListByRelatedId(departmentIdList);
    // loop assembly data;
    for (ActivityDepartmentSelect modelEntity : modelEntityList) {
      // filter, map, and form department data;
      Map<String, Object> departmentData = departmentList.stream()
          .filter(item -> modelEntity.getDepartmentId() != null && modelEntity.getDepartmentId().equals(item.getId()))
          .map(DepartmentSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(ActivityDepartmentSelectDetailMapper.buildMapExtra(modelEntity,departmentData));
    }
    return entityMapList;
  }

  public ActivityDepartmentSelect post(ActivityDepartmentSelect postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      activityDepartmentSelectDAO.insertSelective(ActivityDepartmentSelectData.convert(postEntity, new ActivityDepartmentSelectDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ActivityDepartmentSelectPostMapper postMapper) {
    ActivityDepartmentSelect entity = post(postMapper.buildEntity());
    return ActivityDepartmentSelectDetailMapper.buildMap(entity);
  }

  public List<ActivityDepartmentSelect> postList(List<ActivityDepartmentSelect> postEntities) {
    List<ActivityDepartmentSelect> entityList = new ArrayList<>();
    for (ActivityDepartmentSelect postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ActivityDepartmentSelectPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityDepartmentSelectPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ActivityDepartmentSelect update(ActivityDepartmentSelect updateEntity) {
    ActivityDepartmentSelect modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    activityDepartmentSelectDAO.updateByPrimaryKeySelective(ActivityDepartmentSelectData.convert(updateEntity, new ActivityDepartmentSelectDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ActivityDepartmentSelectUpdateMapper updateMapper) {
    ActivityDepartmentSelect entity = update(updateMapper.buildEntity());
    return ActivityDepartmentSelectDetailMapper.buildMap(entity);
  }

  public List<ActivityDepartmentSelect> updateList(List<ActivityDepartmentSelect> updateEntities) {
    List<ActivityDepartmentSelect> entityList = new ArrayList<>();
    for (ActivityDepartmentSelect updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ActivityDepartmentSelectUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityDepartmentSelectUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ActivityDepartmentSelect put(ActivityDepartmentSelect putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ActivityDepartmentSelect modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      activityDepartmentSelectDAO.insertSelective(ActivityDepartmentSelectData.convert(putEntity, new ActivityDepartmentSelectDO()));
    }
    else {
      activityDepartmentSelectDAO.updateByPrimaryKeySelective(ActivityDepartmentSelectData.convert(putEntity, new ActivityDepartmentSelectDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ActivityDepartmentSelectUpdateMapper putMapper) {
    ActivityDepartmentSelect entity = put(putMapper.buildEntity());
    return ActivityDepartmentSelectDetailMapper.buildMap(entity);
  }

  public List<ActivityDepartmentSelect> putList(List<ActivityDepartmentSelect> putEntities) {
    List<ActivityDepartmentSelect> entityList = new ArrayList<>();
    for (ActivityDepartmentSelect putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ActivityDepartmentSelectUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ActivityDepartmentSelectUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(activityDepartmentSelectDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ActivityDepartmentSelectFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ActivityDepartmentSelectFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ActivityDepartmentSelectFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ActivityDepartmentSelectFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ActivityDepartmentSelectFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ActivityDepartmentSelectFilterMapper filterMapper,
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
