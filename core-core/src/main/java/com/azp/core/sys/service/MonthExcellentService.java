package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.MonthExcellentFilter;
import com.azp.core.sys.datainterface.MonthExcellentDAO;
import com.azp.core.sys.dataobject.MonthExcellentDO;
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
public class MonthExcellentService {
  @Autowired
  private MonthExcellentDAO monthExcellentDAO;

  public MonthExcellent getByPK(String key) {
    MonthExcellentDO entity = monthExcellentDAO.selectByPrimaryKey(key);
    return MonthExcellentData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return MonthExcellentSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    MonthExcellent modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return MonthExcellentDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(MonthExcellentFilterMapper filterMapper) {
    return monthExcellentDAO.countByExample(MonthExcellentFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<MonthExcellent> getListByFilter(MonthExcellentFilterMapper filterMapper) {
    List<MonthExcellent> entityList = new ArrayList<>();
    for (MonthExcellentDO entity : monthExcellentDAO.selectByExample(MonthExcellentFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(MonthExcellentData.convert(entity, new MonthExcellent()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(MonthExcellentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(MonthExcellentSimpleMapper.buildMap(entity)));
    return entityMapList;
  }
  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(MonthExcellentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query monthExcellent data;
    List<MonthExcellent> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (MonthExcellent modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (MonthExcellent modelEntity : modelEntityList) {
      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(MonthExcellentDetailMapper.buildMap(modelEntity));

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

  public MonthExcellent post(MonthExcellent postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      monthExcellentDAO.insertSelective(MonthExcellentData.convert(postEntity, new MonthExcellentDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(MonthExcellentPostMapper postMapper) {
    MonthExcellent entity = post(postMapper.buildEntity());
    return MonthExcellentDetailMapper.buildMap(entity);
  }

  public List<MonthExcellent> postList(List<MonthExcellent> postEntities) {
    List<MonthExcellent> entityList = new ArrayList<>();
    for (MonthExcellent postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<MonthExcellentPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (MonthExcellentPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public MonthExcellent update(MonthExcellent updateEntity) {
    MonthExcellent modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    monthExcellentDAO.updateByPrimaryKeySelective(MonthExcellentData.convert(updateEntity, new MonthExcellentDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(MonthExcellentUpdateMapper updateMapper) {
    MonthExcellent entity = update(updateMapper.buildEntity());
    return MonthExcellentDetailMapper.buildMap(entity);
  }

  public List<MonthExcellent> updateList(List<MonthExcellent> updateEntities) {
    List<MonthExcellent> entityList = new ArrayList<>();
    for (MonthExcellent updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<MonthExcellentUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (MonthExcellentUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public MonthExcellent put(MonthExcellent putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    MonthExcellent modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      monthExcellentDAO.insertSelective(MonthExcellentData.convert(putEntity, new MonthExcellentDO()));
    }
    else {
      monthExcellentDAO.updateByPrimaryKeySelective(MonthExcellentData.convert(putEntity, new MonthExcellentDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(MonthExcellentUpdateMapper putMapper) {
    MonthExcellent entity = put(putMapper.buildEntity());
    return MonthExcellentDetailMapper.buildMap(entity);
  }

  public List<MonthExcellent> putList(List<MonthExcellent> putEntities) {
    List<MonthExcellent> entityList = new ArrayList<>();
    for (MonthExcellent putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<MonthExcellentUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (MonthExcellentUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(monthExcellentDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(MonthExcellentFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new MonthExcellentFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<MonthExcellentFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (MonthExcellentFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(MonthExcellentFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(MonthExcellentFilterMapper filterMapper,
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
