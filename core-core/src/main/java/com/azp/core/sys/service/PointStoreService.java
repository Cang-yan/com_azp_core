package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.PointStoreFilter;
import com.azp.core.sys.datainterface.PointStoreDAO;
import com.azp.core.sys.dataobject.PointStoreDO;
import com.azp.core.sys.model.PointStore;
import com.azp.core.sys.model.PointStoreData;
import com.azp.core.sys.model.PointStoreDetailMapper;
import com.azp.core.sys.model.PointStoreFilterMapper;
import com.azp.core.sys.model.PointStorePostMapper;
import com.azp.core.sys.model.PointStoreSimpleMapper;
import com.azp.core.sys.model.PointStoreUpdateMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class PointStoreService {
  @Autowired
  private PointStoreDAO pointStoreDAO;

  public PointStore getByPK(String key) {
    PointStoreDO entity = pointStoreDAO.selectByPrimaryKey(key);
    return PointStoreData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return PointStoreSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    PointStore modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return PointStoreDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(PointStoreFilterMapper filterMapper) {
    return pointStoreDAO.countByExample(PointStoreFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<PointStore> getListByFilter(PointStoreFilterMapper filterMapper) {
    List<PointStore> entityList = new ArrayList<>();
    for (PointStoreDO entity : pointStoreDAO.selectByExample(PointStoreFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(PointStoreData.convert(entity, new PointStore()));
    }
    return entityList;
  }

  public List<PointStore> getListByRelatedId(List<String> idList) {
    List<PointStore> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (PointStoreDO entity : pointStoreDAO.selectByExample(PointStoreFilter.initIdQueryFilter(idList))) {
      entityList.add(PointStoreData.convert(entity, new PointStore()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(PointStoreFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(PointStoreSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(PointStoreFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query pointStore data;
    List<PointStore> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (PointStore modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (PointStore modelEntity : modelEntityList) {
      entityMapList.add(PointStoreDetailMapper.buildMap(modelEntity));
    }
    return entityMapList;
  }

  public PointStore post(PointStore postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      pointStoreDAO.insertSelective(PointStoreData.convert(postEntity, new PointStoreDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(PointStorePostMapper postMapper) {
    PointStore entity = post(postMapper.buildEntity());
    return PointStoreDetailMapper.buildMap(entity);
  }

  public List<PointStore> postList(List<PointStore> postEntities) {
    List<PointStore> entityList = new ArrayList<>();
    for (PointStore postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<PointStorePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointStorePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public PointStore update(PointStore updateEntity) {
    PointStore modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    pointStoreDAO.updateByPrimaryKeySelective(PointStoreData.convert(updateEntity, new PointStoreDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(PointStoreUpdateMapper updateMapper) {
    PointStore entity = update(updateMapper.buildEntity());
    return PointStoreDetailMapper.buildMap(entity);
  }

  public List<PointStore> updateList(List<PointStore> updateEntities) {
    List<PointStore> entityList = new ArrayList<>();
    for (PointStore updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<PointStoreUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointStoreUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public PointStore put(PointStore putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    PointStore modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      pointStoreDAO.insertSelective(PointStoreData.convert(putEntity, new PointStoreDO()));
    }
    else {
      pointStoreDAO.updateByPrimaryKeySelective(PointStoreData.convert(putEntity, new PointStoreDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(PointStoreUpdateMapper putMapper) {
    PointStore entity = put(putMapper.buildEntity());
    return PointStoreDetailMapper.buildMap(entity);
  }

  public List<PointStore> putList(List<PointStore> putEntities) {
    List<PointStore> entityList = new ArrayList<>();
    for (PointStore putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<PointStoreUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointStoreUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(pointStoreDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(PointStoreFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new PointStoreFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<PointStoreFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (PointStoreFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(PointStoreFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(PointStoreFilterMapper filterMapper,
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
