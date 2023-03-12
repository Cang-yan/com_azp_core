package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.DepartmentPointFilter;
import com.azp.core.sys.datainterface.DepartmentPointDAO;
import com.azp.core.sys.dataobject.DepartmentPointDO;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentFilterMapper;
import com.azp.core.sys.model.DepartmentPoint;
import com.azp.core.sys.model.DepartmentPointData;
import com.azp.core.sys.model.DepartmentPointDetailMapper;
import com.azp.core.sys.model.DepartmentPointFilterMapper;
import com.azp.core.sys.model.DepartmentPointPostMapper;
import com.azp.core.sys.model.DepartmentPointSimpleMapper;
import com.azp.core.sys.model.DepartmentPointUpdateMapper;
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
public class DepartmentPointService {
  @Autowired
  private DepartmentPointDAO departmentPointDAO;

  @Autowired
  private DepartmentService departmentService;

  public DepartmentPoint getByPK(String key) {
    DepartmentPointDO entity = departmentPointDAO.selectByPrimaryKey(key);
    return DepartmentPointData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return DepartmentPointSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    DepartmentPoint modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build department data from local database;
    DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
    departmentFilterMapper.id = modelEntity.getDepartmentId();
    departmentFilterMapper.page = 0L;
    departmentFilterMapper.rows = 0;
    Map<String, Object> departmentData = departmentService.getFilterMapList(departmentFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return DepartmentPointDetailMapper.buildMapExtra(modelEntity,departmentData);
  }

  public Long getCountByFilter(DepartmentPointFilterMapper filterMapper) {
    return departmentPointDAO.countByExample(DepartmentPointFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<DepartmentPoint> getListByFilter(DepartmentPointFilterMapper filterMapper) {
    List<DepartmentPoint> entityList = new ArrayList<>();
    for (DepartmentPointDO entity : departmentPointDAO.selectByExample(DepartmentPointFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(DepartmentPointData.convert(entity, new DepartmentPoint()));
    }
    return entityList;
  }

  public List<DepartmentPoint> getListByRelatedId(List<String> idList) {
    List<DepartmentPoint> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (DepartmentPointDO entity : departmentPointDAO.selectByExample(DepartmentPointFilter.initIdQueryFilter(idList))) {
      entityList.add(DepartmentPointData.convert(entity, new DepartmentPoint()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(DepartmentPointFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(DepartmentPointSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(DepartmentPointFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query departmentPoint data;
    List<DepartmentPoint> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> departmentIdList = new ArrayList<>();
    for (DepartmentPoint modelEntity : modelEntityList) {
      departmentIdList.add(modelEntity.getDepartmentId());
    }
    // load data from local database or remote service;
    List<Department> departmentList = departmentService.getListByRelatedId(departmentIdList);
    // loop assembly data;
    for (DepartmentPoint modelEntity : modelEntityList) {
      // filter, map, and form department data;
      Map<String, Object> departmentData = departmentList.stream()
          .filter(item -> modelEntity.getDepartmentId() != null && modelEntity.getDepartmentId().equals(item.getId()))
          .map(DepartmentSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(DepartmentPointDetailMapper.buildMapExtra(modelEntity,departmentData));
    }
    return entityMapList;
  }

  public DepartmentPoint post(DepartmentPoint postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      departmentPointDAO.insertSelective(DepartmentPointData.convert(postEntity, new DepartmentPointDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(DepartmentPointPostMapper postMapper) {
    DepartmentPoint entity = post(postMapper.buildEntity());
    return DepartmentPointDetailMapper.buildMap(entity);
  }

  public List<DepartmentPoint> postList(List<DepartmentPoint> postEntities) {
    List<DepartmentPoint> entityList = new ArrayList<>();
    for (DepartmentPoint postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<DepartmentPointPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (DepartmentPointPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public DepartmentPoint update(DepartmentPoint updateEntity) {
    DepartmentPoint modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    departmentPointDAO.updateByPrimaryKeySelective(DepartmentPointData.convert(updateEntity, new DepartmentPointDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(DepartmentPointUpdateMapper updateMapper) {
    DepartmentPoint entity = update(updateMapper.buildEntity());
    return DepartmentPointDetailMapper.buildMap(entity);
  }

  public List<DepartmentPoint> updateList(List<DepartmentPoint> updateEntities) {
    List<DepartmentPoint> entityList = new ArrayList<>();
    for (DepartmentPoint updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<DepartmentPointUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (DepartmentPointUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public DepartmentPoint put(DepartmentPoint putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    DepartmentPoint modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      departmentPointDAO.insertSelective(DepartmentPointData.convert(putEntity, new DepartmentPointDO()));
    }
    else {
      departmentPointDAO.updateByPrimaryKeySelective(DepartmentPointData.convert(putEntity, new DepartmentPointDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(DepartmentPointUpdateMapper putMapper) {
    DepartmentPoint entity = put(putMapper.buildEntity());
    return DepartmentPointDetailMapper.buildMap(entity);
  }

  public List<DepartmentPoint> putList(List<DepartmentPoint> putEntities) {
    List<DepartmentPoint> entityList = new ArrayList<>();
    for (DepartmentPoint putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<DepartmentPointUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (DepartmentPointUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(departmentPointDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(DepartmentPointFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new DepartmentPointFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<DepartmentPointFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (DepartmentPointFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(DepartmentPointFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(DepartmentPointFilterMapper filterMapper,
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
