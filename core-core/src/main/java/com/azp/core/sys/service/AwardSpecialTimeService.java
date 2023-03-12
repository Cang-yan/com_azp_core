package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.AwardSpecialTimeFilter;
import com.azp.core.sys.datainterface.AwardSpecialTimeDAO;
import com.azp.core.sys.dataobject.AwardSpecialTimeDO;
import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class AwardSpecialTimeService {
  @Autowired
  private AwardSpecialTimeDAO awardSpecialTimeDAO;

  public AwardSpecialTime getByPK(String key) {
    AwardSpecialTimeDO entity = awardSpecialTimeDAO.selectByPrimaryKey(key);
    return AwardSpecialTimeData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return AwardSpecialTimeSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    AwardSpecialTime modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return AwardSpecialTimeDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(AwardSpecialTimeFilterMapper filterMapper) {
    return awardSpecialTimeDAO.countByExample(AwardSpecialTimeFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<AwardSpecialTime> getListByFilter(AwardSpecialTimeFilterMapper filterMapper) {
    List<AwardSpecialTime> entityList = new ArrayList<>();
    for (AwardSpecialTimeDO entity : awardSpecialTimeDAO.selectByExample(AwardSpecialTimeFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(AwardSpecialTimeData.convert(entity, new AwardSpecialTime()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(AwardSpecialTimeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(AwardSpecialTimeSimpleMapper.buildMap(entity)));
    return entityMapList;
  }
  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(AwardSpecialTimeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query awardSpecialTime data;
    List<AwardSpecialTime> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (AwardSpecialTime modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (AwardSpecialTime modelEntity : modelEntityList) {
      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(AwardSpecialTimeDetailMapper.buildMap(modelEntity));

      String head = "";
      UserFilterMapper userFilterMapper = new UserFilterMapper();
      userFilterMapper.userCodeIn= Collections.singletonList(modelEntity.getUserCode());
      List<User> userList =userService.getListByFilter(userFilterMapper);
      if(!userList.isEmpty()&&userList.get(0).getUserInfoId()!=null) {
        UseInfo useInfo = useInfoService.getByPK(userList.get(0).getUserInfoId());
        if (useInfo!=null) head =useInfo.getHead();
      }
      detailMapper.put("userHead",head);

      entityMapList.add(detailMapper);
    }
    return entityMapList;
  }

  public AwardSpecialTime post(AwardSpecialTime postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      awardSpecialTimeDAO.insertSelective(AwardSpecialTimeData.convert(postEntity, new AwardSpecialTimeDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(AwardSpecialTimePostMapper postMapper) {
    AwardSpecialTime entity = post(postMapper.buildEntity());
    return AwardSpecialTimeDetailMapper.buildMap(entity);
  }

  public List<AwardSpecialTime> postList(List<AwardSpecialTime> postEntities) {
    List<AwardSpecialTime> entityList = new ArrayList<>();
    for (AwardSpecialTime postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<AwardSpecialTimePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSpecialTimePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public AwardSpecialTime update(AwardSpecialTime updateEntity) {
    AwardSpecialTime modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    awardSpecialTimeDAO.updateByPrimaryKeySelective(AwardSpecialTimeData.convert(updateEntity, new AwardSpecialTimeDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(AwardSpecialTimeUpdateMapper updateMapper) {
    AwardSpecialTime entity = update(updateMapper.buildEntity());
    return AwardSpecialTimeDetailMapper.buildMap(entity);
  }

  public List<AwardSpecialTime> updateList(List<AwardSpecialTime> updateEntities) {
    List<AwardSpecialTime> entityList = new ArrayList<>();
    for (AwardSpecialTime updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<AwardSpecialTimeUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSpecialTimeUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public AwardSpecialTime put(AwardSpecialTime putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    AwardSpecialTime modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      awardSpecialTimeDAO.insertSelective(AwardSpecialTimeData.convert(putEntity, new AwardSpecialTimeDO()));
    }
    else {
      awardSpecialTimeDAO.updateByPrimaryKeySelective(AwardSpecialTimeData.convert(putEntity, new AwardSpecialTimeDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(AwardSpecialTimeUpdateMapper putMapper) {
    AwardSpecialTime entity = put(putMapper.buildEntity());
    return AwardSpecialTimeDetailMapper.buildMap(entity);
  }

  public List<AwardSpecialTime> putList(List<AwardSpecialTime> putEntities) {
    List<AwardSpecialTime> entityList = new ArrayList<>();
    for (AwardSpecialTime putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<AwardSpecialTimeUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSpecialTimeUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(awardSpecialTimeDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(AwardSpecialTimeFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new AwardSpecialTimeFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<AwardSpecialTimeFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (AwardSpecialTimeFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(AwardSpecialTimeFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(AwardSpecialTimeFilterMapper filterMapper,
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
