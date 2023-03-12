package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.LevelFilter;
import com.azp.core.sys.datainterface.LevelDAO;
import com.azp.core.sys.dataobject.LevelDO;
import com.azp.core.sys.model.Level;
import com.azp.core.sys.model.LevelData;
import com.azp.core.sys.model.LevelDetailMapper;
import com.azp.core.sys.model.LevelFilterMapper;
import com.azp.core.sys.model.LevelPostMapper;
import com.azp.core.sys.model.LevelRecord;
import com.azp.core.sys.model.LevelRecordFilterMapper;
import com.azp.core.sys.model.LevelRecordSimpleMapper;
import com.azp.core.sys.model.LevelSimpleMapper;
import com.azp.core.sys.model.LevelUpdateMapper;
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
public class LevelService {
  @Autowired
  private LevelDAO levelDAO;

  @Autowired
  private LevelRecordService levelRecordService;

  public Level getByPK(String key) {
    LevelDO entity = levelDAO.selectByPrimaryKey(key);
    return LevelData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return LevelSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    Level modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build levelRecord data from local database;
    LevelRecordFilterMapper levelRecordFilterMapper = new LevelRecordFilterMapper();
    levelRecordFilterMapper.levelId = modelEntity.getId();
    levelRecordFilterMapper.page = 0L;
    levelRecordFilterMapper.rows = 0;
    Map<String, Object> levelRecordData = levelRecordService.getFilterMapList(levelRecordFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return LevelDetailMapper.buildMapExtra(modelEntity,levelRecordData);
  }

  public Long getCountByFilter(LevelFilterMapper filterMapper) {
    return levelDAO.countByExample(LevelFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<Level> getListByFilter(LevelFilterMapper filterMapper) {
    List<Level> entityList = new ArrayList<>();
    for (LevelDO entity : levelDAO.selectByExample(LevelFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(LevelData.convert(entity, new Level()));
    }
    return entityList;
  }

  public List<Level> getListByRelatedId(List<String> idList) {
    List<Level> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (LevelDO entity : levelDAO.selectByExample(LevelFilter.initIdQueryFilter(idList))) {
      entityList.add(LevelData.convert(entity, new Level()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(LevelFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(LevelSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(LevelFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query level data;
    List<Level> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> idList = new ArrayList<>();
    for (Level modelEntity : modelEntityList) {
      idList.add(modelEntity.getId());
    }
    // load data from local database or remote service;
    List<LevelRecord> levelRecordList = levelRecordService.getListByRelatedLevelId(idList);
    // loop assembly data;
    for (Level modelEntity : modelEntityList) {
      // filter, map, and form levelRecord data;
      Map<String, Object> levelRecordData = levelRecordList.stream()
          .filter(item -> modelEntity.getId() != null && modelEntity.getId().equals(item.getLevelId()))
          .map(LevelRecordSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(LevelDetailMapper.buildMapExtra(modelEntity,levelRecordData));
    }
    return entityMapList;
  }

  public Level post(Level postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      levelDAO.insertSelective(LevelData.convert(postEntity, new LevelDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(LevelPostMapper postMapper) {
    Level entity = post(postMapper.buildEntity());
    return LevelDetailMapper.buildMap(entity);
  }

  public List<Level> postList(List<Level> postEntities) {
    List<Level> entityList = new ArrayList<>();
    for (Level postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<LevelPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LevelPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public Level update(Level updateEntity) {
    Level modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    levelDAO.updateByPrimaryKeySelective(LevelData.convert(updateEntity, new LevelDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(LevelUpdateMapper updateMapper) {
    Level entity = update(updateMapper.buildEntity());
    return LevelDetailMapper.buildMap(entity);
  }

  public List<Level> updateList(List<Level> updateEntities) {
    List<Level> entityList = new ArrayList<>();
    for (Level updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<LevelUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LevelUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public Level put(Level putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    Level modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      levelDAO.insertSelective(LevelData.convert(putEntity, new LevelDO()));
    }
    else {
      levelDAO.updateByPrimaryKeySelective(LevelData.convert(putEntity, new LevelDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(LevelUpdateMapper putMapper) {
    Level entity = put(putMapper.buildEntity());
    return LevelDetailMapper.buildMap(entity);
  }

  public List<Level> putList(List<Level> putEntities) {
    List<Level> entityList = new ArrayList<>();
    for (Level putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<LevelUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LevelUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(levelDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(LevelFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new LevelFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<LevelFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (LevelFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(LevelFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(LevelFilterMapper filterMapper,
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
