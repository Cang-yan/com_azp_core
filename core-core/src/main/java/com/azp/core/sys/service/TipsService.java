package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.TipsFilter;
import com.azp.core.sys.datainterface.TipsDAO;
import com.azp.core.sys.dataobject.TipsDO;
import com.azp.core.sys.model.Tips;
import com.azp.core.sys.model.TipsData;
import com.azp.core.sys.model.TipsDetailMapper;
import com.azp.core.sys.model.TipsFilterMapper;
import com.azp.core.sys.model.TipsPostMapper;
import com.azp.core.sys.model.TipsSimpleMapper;
import com.azp.core.sys.model.TipsUpdateMapper;
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
public class TipsService {
  @Autowired
  private TipsDAO tipsDAO;

  public Tips getByPK(String key) {
    TipsDO entity = tipsDAO.selectByPrimaryKey(key);
    return TipsData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return TipsSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    Tips modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return TipsDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(TipsFilterMapper filterMapper) {
    return tipsDAO.countByExample(TipsFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<Tips> getListByFilter(TipsFilterMapper filterMapper) {
    List<Tips> entityList = new ArrayList<>();
    for (TipsDO entity : tipsDAO.selectByExample(TipsFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(TipsData.convert(entity, new Tips()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(TipsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(TipsSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(TipsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query tips data;
    List<Tips> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (Tips modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (Tips modelEntity : modelEntityList) {
      entityMapList.add(TipsDetailMapper.buildMap(modelEntity));
    }
    return entityMapList;
  }

  public Tips post(Tips postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      tipsDAO.insertSelective(TipsData.convert(postEntity, new TipsDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(TipsPostMapper postMapper) {
    Tips entity = post(postMapper.buildEntity());
    return TipsDetailMapper.buildMap(entity);
  }

  public List<Tips> postList(List<Tips> postEntities) {
    List<Tips> entityList = new ArrayList<>();
    for (Tips postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<TipsPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (TipsPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public Tips update(Tips updateEntity) {
    Tips modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    tipsDAO.updateByPrimaryKeySelective(TipsData.convert(updateEntity, new TipsDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(TipsUpdateMapper updateMapper) {
    Tips entity = update(updateMapper.buildEntity());
    return TipsDetailMapper.buildMap(entity);
  }

  public List<Tips> updateList(List<Tips> updateEntities) {
    List<Tips> entityList = new ArrayList<>();
    for (Tips updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<TipsUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (TipsUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public Tips put(Tips putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    Tips modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      tipsDAO.insertSelective(TipsData.convert(putEntity, new TipsDO()));
    }
    else {
      tipsDAO.updateByPrimaryKeySelective(TipsData.convert(putEntity, new TipsDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(TipsUpdateMapper putMapper) {
    Tips entity = put(putMapper.buildEntity());
    return TipsDetailMapper.buildMap(entity);
  }

  public List<Tips> putList(List<Tips> putEntities) {
    List<Tips> entityList = new ArrayList<>();
    for (Tips putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<TipsUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (TipsUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(tipsDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(TipsFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new TipsFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<TipsFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (TipsFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(TipsFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(TipsFilterMapper filterMapper,
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
