package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.AwardGoodEyeFilter;
import com.azp.core.sys.datainterface.AwardGoodEyeDAO;
import com.azp.core.sys.dataobject.AwardGoodEyeDO;
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
public class AwardGoodEyeService {
  @Autowired
  private AwardGoodEyeDAO awardGoodEyeDAO;

  public AwardGoodEye getByPK(String key) {
    AwardGoodEyeDO entity = awardGoodEyeDAO.selectByPrimaryKey(key);
    return AwardGoodEyeData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return AwardGoodEyeSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    AwardGoodEye modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return AwardGoodEyeDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(AwardGoodEyeFilterMapper filterMapper) {
    return awardGoodEyeDAO.countByExample(AwardGoodEyeFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<AwardGoodEye> getListByFilter(AwardGoodEyeFilterMapper filterMapper) {
    List<AwardGoodEye> entityList = new ArrayList<>();
    for (AwardGoodEyeDO entity : awardGoodEyeDAO.selectByExample(AwardGoodEyeFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(AwardGoodEyeData.convert(entity, new AwardGoodEye()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(AwardGoodEyeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(AwardGoodEyeSimpleMapper.buildMap(entity)));
    return entityMapList;
  }
  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(AwardGoodEyeFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query awardGoodEye data;
    List<AwardGoodEye> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (AwardGoodEye modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (AwardGoodEye modelEntity : modelEntityList) {
      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(AwardGoodEyeDetailMapper.buildMap(modelEntity));

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

  public AwardGoodEye post(AwardGoodEye postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      awardGoodEyeDAO.insertSelective(AwardGoodEyeData.convert(postEntity, new AwardGoodEyeDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(AwardGoodEyePostMapper postMapper) {
    AwardGoodEye entity = post(postMapper.buildEntity());
    return AwardGoodEyeDetailMapper.buildMap(entity);
  }

  public List<AwardGoodEye> postList(List<AwardGoodEye> postEntities) {
    List<AwardGoodEye> entityList = new ArrayList<>();
    for (AwardGoodEye postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<AwardGoodEyePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardGoodEyePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public AwardGoodEye update(AwardGoodEye updateEntity) {
    AwardGoodEye modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    awardGoodEyeDAO.updateByPrimaryKeySelective(AwardGoodEyeData.convert(updateEntity, new AwardGoodEyeDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(AwardGoodEyeUpdateMapper updateMapper) {
    AwardGoodEye entity = update(updateMapper.buildEntity());
    return AwardGoodEyeDetailMapper.buildMap(entity);
  }

  public List<AwardGoodEye> updateList(List<AwardGoodEye> updateEntities) {
    List<AwardGoodEye> entityList = new ArrayList<>();
    for (AwardGoodEye updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<AwardGoodEyeUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardGoodEyeUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public AwardGoodEye put(AwardGoodEye putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    AwardGoodEye modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      awardGoodEyeDAO.insertSelective(AwardGoodEyeData.convert(putEntity, new AwardGoodEyeDO()));
    }
    else {
      awardGoodEyeDAO.updateByPrimaryKeySelective(AwardGoodEyeData.convert(putEntity, new AwardGoodEyeDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(AwardGoodEyeUpdateMapper putMapper) {
    AwardGoodEye entity = put(putMapper.buildEntity());
    return AwardGoodEyeDetailMapper.buildMap(entity);
  }

  public List<AwardGoodEye> putList(List<AwardGoodEye> putEntities) {
    List<AwardGoodEye> entityList = new ArrayList<>();
    for (AwardGoodEye putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<AwardGoodEyeUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardGoodEyeUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(awardGoodEyeDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(AwardGoodEyeFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new AwardGoodEyeFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<AwardGoodEyeFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (AwardGoodEyeFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(AwardGoodEyeFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(AwardGoodEyeFilterMapper filterMapper,
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
