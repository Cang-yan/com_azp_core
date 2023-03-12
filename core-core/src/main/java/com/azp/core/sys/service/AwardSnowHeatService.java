package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.AwardSnowHeatFilter;
import com.azp.core.sys.datainterface.AwardSnowHeatDAO;
import com.azp.core.sys.dataobject.AwardSnowHeatDO;
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
public class AwardSnowHeatService {
  @Autowired
  private AwardSnowHeatDAO awardSnowHeatDAO;

  public AwardSnowHeat getByPK(String key) {
    AwardSnowHeatDO entity = awardSnowHeatDAO.selectByPrimaryKey(key);
    return AwardSnowHeatData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return AwardSnowHeatSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    AwardSnowHeat modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return AwardSnowHeatDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(AwardSnowHeatFilterMapper filterMapper) {
    return awardSnowHeatDAO.countByExample(AwardSnowHeatFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<AwardSnowHeat> getListByFilter(AwardSnowHeatFilterMapper filterMapper) {
    List<AwardSnowHeat> entityList = new ArrayList<>();
    for (AwardSnowHeatDO entity : awardSnowHeatDAO.selectByExample(AwardSnowHeatFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(AwardSnowHeatData.convert(entity, new AwardSnowHeat()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(AwardSnowHeatFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(AwardSnowHeatSimpleMapper.buildMap(entity)));
    return entityMapList;
  }
  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(AwardSnowHeatFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query awardSnowHeat data;
    List<AwardSnowHeat> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (AwardSnowHeat modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (AwardSnowHeat modelEntity : modelEntityList) {
      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(AwardSnowHeatDetailMapper.buildMap(modelEntity));

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

  public AwardSnowHeat post(AwardSnowHeat postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      awardSnowHeatDAO.insertSelective(AwardSnowHeatData.convert(postEntity, new AwardSnowHeatDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(AwardSnowHeatPostMapper postMapper) {
    AwardSnowHeat entity = post(postMapper.buildEntity());
    return AwardSnowHeatDetailMapper.buildMap(entity);
  }

  public List<AwardSnowHeat> postList(List<AwardSnowHeat> postEntities) {
    List<AwardSnowHeat> entityList = new ArrayList<>();
    for (AwardSnowHeat postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<AwardSnowHeatPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSnowHeatPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public AwardSnowHeat update(AwardSnowHeat updateEntity) {
    AwardSnowHeat modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    awardSnowHeatDAO.updateByPrimaryKeySelective(AwardSnowHeatData.convert(updateEntity, new AwardSnowHeatDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(AwardSnowHeatUpdateMapper updateMapper) {
    AwardSnowHeat entity = update(updateMapper.buildEntity());
    return AwardSnowHeatDetailMapper.buildMap(entity);
  }

  public List<AwardSnowHeat> updateList(List<AwardSnowHeat> updateEntities) {
    List<AwardSnowHeat> entityList = new ArrayList<>();
    for (AwardSnowHeat updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<AwardSnowHeatUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSnowHeatUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public AwardSnowHeat put(AwardSnowHeat putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    AwardSnowHeat modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      awardSnowHeatDAO.insertSelective(AwardSnowHeatData.convert(putEntity, new AwardSnowHeatDO()));
    }
    else {
      awardSnowHeatDAO.updateByPrimaryKeySelective(AwardSnowHeatData.convert(putEntity, new AwardSnowHeatDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(AwardSnowHeatUpdateMapper putMapper) {
    AwardSnowHeat entity = put(putMapper.buildEntity());
    return AwardSnowHeatDetailMapper.buildMap(entity);
  }

  public List<AwardSnowHeat> putList(List<AwardSnowHeat> putEntities) {
    List<AwardSnowHeat> entityList = new ArrayList<>();
    for (AwardSnowHeat putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<AwardSnowHeatUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSnowHeatUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(awardSnowHeatDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(AwardSnowHeatFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new AwardSnowHeatFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<AwardSnowHeatFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (AwardSnowHeatFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(AwardSnowHeatFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(AwardSnowHeatFilterMapper filterMapper,
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
