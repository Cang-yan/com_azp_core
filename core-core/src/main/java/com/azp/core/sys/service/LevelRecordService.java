package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.LevelRecordFilter;
import com.azp.core.sys.datainterface.LevelRecordDAO;
import com.azp.core.sys.dataobject.LevelRecordDO;
import com.azp.core.sys.model.Level;
import com.azp.core.sys.model.LevelFilterMapper;
import com.azp.core.sys.model.LevelRecord;
import com.azp.core.sys.model.LevelRecordData;
import com.azp.core.sys.model.LevelRecordDetailMapper;
import com.azp.core.sys.model.LevelRecordFilterMapper;
import com.azp.core.sys.model.LevelRecordPostMapper;
import com.azp.core.sys.model.LevelRecordSimpleMapper;
import com.azp.core.sys.model.LevelRecordUpdateMapper;
import com.azp.core.sys.model.LevelSimpleMapper;
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
public class LevelRecordService {
  @Autowired
  private LevelRecordDAO levelRecordDAO;

  @Autowired
  private LevelService levelService;

  public LevelRecord getByPK(String key) {
    LevelRecordDO entity = levelRecordDAO.selectByPrimaryKey(key);
    return LevelRecordData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return LevelRecordSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    LevelRecord modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build level data from local database;
    LevelFilterMapper levelFilterMapper = new LevelFilterMapper();
    levelFilterMapper.id = modelEntity.getLevelId();
    levelFilterMapper.page = 0L;
    levelFilterMapper.rows = 0;
    Map<String, Object> levelData = levelService.getFilterMapList(levelFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return LevelRecordDetailMapper.buildMapExtra(modelEntity,levelData);
  }

  public Long getCountByFilter(LevelRecordFilterMapper filterMapper) {
    return levelRecordDAO.countByExample(LevelRecordFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<LevelRecord> getListByFilter(LevelRecordFilterMapper filterMapper) {
    List<LevelRecord> entityList = new ArrayList<>();
    for (LevelRecordDO entity : levelRecordDAO.selectByExample(LevelRecordFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(LevelRecordData.convert(entity, new LevelRecord()));
    }
    return entityList;
  }

  public List<LevelRecord> getListByRelatedLevelId(List<String> levelIdList) {
    List<LevelRecord> entityList = new ArrayList<>();
    if (levelIdList.size() == 0) return entityList;
    for (LevelRecordDO entity : levelRecordDAO.selectByExample(LevelRecordFilter.initLevelIdQueryFilter(levelIdList))) {
      entityList.add(LevelRecordData.convert(entity, new LevelRecord()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(LevelRecordFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(LevelRecordSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(LevelRecordFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query levelRecord data;
    List<LevelRecord> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> levelIdList = new ArrayList<>();
    for (LevelRecord modelEntity : modelEntityList) {
      levelIdList.add(modelEntity.getLevelId());
    }
    // load data from local database or remote service;
    List<Level> levelList = levelService.getListByRelatedId(levelIdList);
    // loop assembly data;
    for (LevelRecord modelEntity : modelEntityList) {
      // filter, map, and form level data;
      Map<String, Object> levelData = levelList.stream()
          .filter(item -> modelEntity.getLevelId() != null && modelEntity.getLevelId().equals(item.getId()))
          .map(LevelSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(LevelRecordDetailMapper.buildMapExtra(modelEntity,levelData));
    }
    return entityMapList;
  }

  public LevelRecord post(LevelRecord postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      levelRecordDAO.insertSelective(LevelRecordData.convert(postEntity, new LevelRecordDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(LevelRecordPostMapper postMapper) {
    LevelRecord entity = post(postMapper.buildEntity());
    return LevelRecordDetailMapper.buildMap(entity);
  }

  public List<LevelRecord> postList(List<LevelRecord> postEntities) {
    List<LevelRecord> entityList = new ArrayList<>();
    for (LevelRecord postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<LevelRecordPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LevelRecordPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public LevelRecord update(LevelRecord updateEntity) {
    LevelRecord modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    levelRecordDAO.updateByPrimaryKeySelective(LevelRecordData.convert(updateEntity, new LevelRecordDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(LevelRecordUpdateMapper updateMapper) {
    LevelRecord entity = update(updateMapper.buildEntity());
    return LevelRecordDetailMapper.buildMap(entity);
  }

  public List<LevelRecord> updateList(List<LevelRecord> updateEntities) {
    List<LevelRecord> entityList = new ArrayList<>();
    for (LevelRecord updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<LevelRecordUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LevelRecordUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public LevelRecord put(LevelRecord putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    LevelRecord modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      levelRecordDAO.insertSelective(LevelRecordData.convert(putEntity, new LevelRecordDO()));
    }
    else {
      levelRecordDAO.updateByPrimaryKeySelective(LevelRecordData.convert(putEntity, new LevelRecordDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(LevelRecordUpdateMapper putMapper) {
    LevelRecord entity = put(putMapper.buildEntity());
    return LevelRecordDetailMapper.buildMap(entity);
  }

  public List<LevelRecord> putList(List<LevelRecord> putEntities) {
    List<LevelRecord> entityList = new ArrayList<>();
    for (LevelRecord putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<LevelRecordUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LevelRecordUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(levelRecordDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(LevelRecordFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new LevelRecordFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<LevelRecordFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (LevelRecordFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(LevelRecordFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(LevelRecordFilterMapper filterMapper,
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
