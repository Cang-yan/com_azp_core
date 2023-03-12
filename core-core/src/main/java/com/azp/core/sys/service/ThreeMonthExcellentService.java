package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.ThreeMonthExcellentFilter;
import com.azp.core.sys.datainterface.ThreeMonthExcellentDAO;
import com.azp.core.sys.dataobject.ThreeMonthExcellentDO;
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
public class ThreeMonthExcellentService {
  @Autowired
  private ThreeMonthExcellentDAO threeMonthExcellentDAO;

  public ThreeMonthExcellent getByPK(String key) {
    ThreeMonthExcellentDO entity = threeMonthExcellentDAO.selectByPrimaryKey(key);
    return ThreeMonthExcellentData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return ThreeMonthExcellentSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    ThreeMonthExcellent modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return ThreeMonthExcellentDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(ThreeMonthExcellentFilterMapper filterMapper) {
    return threeMonthExcellentDAO.countByExample(ThreeMonthExcellentFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<ThreeMonthExcellent> getListByFilter(ThreeMonthExcellentFilterMapper filterMapper) {
    List<ThreeMonthExcellent> entityList = new ArrayList<>();
    for (ThreeMonthExcellentDO entity : threeMonthExcellentDAO.selectByExample(ThreeMonthExcellentFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(ThreeMonthExcellentData.convert(entity, new ThreeMonthExcellent()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(ThreeMonthExcellentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(ThreeMonthExcellentSimpleMapper.buildMap(entity)));
    return entityMapList;
  }
  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(ThreeMonthExcellentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query threeMonthExcellent data;
    List<ThreeMonthExcellent> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (ThreeMonthExcellent modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (ThreeMonthExcellent modelEntity : modelEntityList) {

      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(ThreeMonthExcellentDetailMapper.buildMap(modelEntity));

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

  public ThreeMonthExcellent post(ThreeMonthExcellent postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      threeMonthExcellentDAO.insertSelective(ThreeMonthExcellentData.convert(postEntity, new ThreeMonthExcellentDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(ThreeMonthExcellentPostMapper postMapper) {
    ThreeMonthExcellent entity = post(postMapper.buildEntity());
    return ThreeMonthExcellentDetailMapper.buildMap(entity);
  }

  public List<ThreeMonthExcellent> postList(List<ThreeMonthExcellent> postEntities) {
    List<ThreeMonthExcellent> entityList = new ArrayList<>();
    for (ThreeMonthExcellent postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<ThreeMonthExcellentPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ThreeMonthExcellentPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public ThreeMonthExcellent update(ThreeMonthExcellent updateEntity) {
    ThreeMonthExcellent modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    threeMonthExcellentDAO.updateByPrimaryKeySelective(ThreeMonthExcellentData.convert(updateEntity, new ThreeMonthExcellentDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(ThreeMonthExcellentUpdateMapper updateMapper) {
    ThreeMonthExcellent entity = update(updateMapper.buildEntity());
    return ThreeMonthExcellentDetailMapper.buildMap(entity);
  }

  public List<ThreeMonthExcellent> updateList(List<ThreeMonthExcellent> updateEntities) {
    List<ThreeMonthExcellent> entityList = new ArrayList<>();
    for (ThreeMonthExcellent updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<ThreeMonthExcellentUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ThreeMonthExcellentUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public ThreeMonthExcellent put(ThreeMonthExcellent putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    ThreeMonthExcellent modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      threeMonthExcellentDAO.insertSelective(ThreeMonthExcellentData.convert(putEntity, new ThreeMonthExcellentDO()));
    }
    else {
      threeMonthExcellentDAO.updateByPrimaryKeySelective(ThreeMonthExcellentData.convert(putEntity, new ThreeMonthExcellentDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(ThreeMonthExcellentUpdateMapper putMapper) {
    ThreeMonthExcellent entity = put(putMapper.buildEntity());
    return ThreeMonthExcellentDetailMapper.buildMap(entity);
  }

  public List<ThreeMonthExcellent> putList(List<ThreeMonthExcellent> putEntities) {
    List<ThreeMonthExcellent> entityList = new ArrayList<>();
    for (ThreeMonthExcellent putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<ThreeMonthExcellentUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (ThreeMonthExcellentUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(threeMonthExcellentDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(ThreeMonthExcellentFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new ThreeMonthExcellentFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<ThreeMonthExcellentFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (ThreeMonthExcellentFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(ThreeMonthExcellentFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(ThreeMonthExcellentFilterMapper filterMapper,
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
