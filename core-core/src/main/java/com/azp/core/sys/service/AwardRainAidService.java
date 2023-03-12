package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.AwardRainAidFilter;
import com.azp.core.sys.datainterface.AwardRainAidDAO;
import com.azp.core.sys.dataobject.AwardRainAidDO;
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
public class AwardRainAidService {
  @Autowired
  private AwardRainAidDAO awardRainAidDAO;

  public AwardRainAid getByPK(String key) {
    AwardRainAidDO entity = awardRainAidDAO.selectByPrimaryKey(key);
    return AwardRainAidData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return AwardRainAidSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    AwardRainAid modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return AwardRainAidDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(AwardRainAidFilterMapper filterMapper) {
    return awardRainAidDAO.countByExample(AwardRainAidFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<AwardRainAid> getListByFilter(AwardRainAidFilterMapper filterMapper) {
    List<AwardRainAid> entityList = new ArrayList<>();
    for (AwardRainAidDO entity : awardRainAidDAO.selectByExample(AwardRainAidFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(AwardRainAidData.convert(entity, new AwardRainAid()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(AwardRainAidFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(AwardRainAidSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(AwardRainAidFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query awardRainAid data;
    List<AwardRainAid> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (AwardRainAid modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (AwardRainAid modelEntity : modelEntityList) {
      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(AwardRainAidDetailMapper.buildMap(modelEntity));

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

  public AwardRainAid post(AwardRainAid postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      awardRainAidDAO.insertSelective(AwardRainAidData.convert(postEntity, new AwardRainAidDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(AwardRainAidPostMapper postMapper) {
    AwardRainAid entity = post(postMapper.buildEntity());
    return AwardRainAidDetailMapper.buildMap(entity);
  }

  public List<AwardRainAid> postList(List<AwardRainAid> postEntities) {
    List<AwardRainAid> entityList = new ArrayList<>();
    for (AwardRainAid postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<AwardRainAidPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardRainAidPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public AwardRainAid update(AwardRainAid updateEntity) {
    AwardRainAid modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    awardRainAidDAO.updateByPrimaryKeySelective(AwardRainAidData.convert(updateEntity, new AwardRainAidDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(AwardRainAidUpdateMapper updateMapper) {
    AwardRainAid entity = update(updateMapper.buildEntity());
    return AwardRainAidDetailMapper.buildMap(entity);
  }

  public List<AwardRainAid> updateList(List<AwardRainAid> updateEntities) {
    List<AwardRainAid> entityList = new ArrayList<>();
    for (AwardRainAid updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<AwardRainAidUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardRainAidUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public AwardRainAid put(AwardRainAid putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    AwardRainAid modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      awardRainAidDAO.insertSelective(AwardRainAidData.convert(putEntity, new AwardRainAidDO()));
    }
    else {
      awardRainAidDAO.updateByPrimaryKeySelective(AwardRainAidData.convert(putEntity, new AwardRainAidDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(AwardRainAidUpdateMapper putMapper) {
    AwardRainAid entity = put(putMapper.buildEntity());
    return AwardRainAidDetailMapper.buildMap(entity);
  }

  public List<AwardRainAid> putList(List<AwardRainAid> putEntities) {
    List<AwardRainAid> entityList = new ArrayList<>();
    for (AwardRainAid putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<AwardRainAidUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardRainAidUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(awardRainAidDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(AwardRainAidFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new AwardRainAidFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<AwardRainAidFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (AwardRainAidFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(AwardRainAidFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(AwardRainAidFilterMapper filterMapper,
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
