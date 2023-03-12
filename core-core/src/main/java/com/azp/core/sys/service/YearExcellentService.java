package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.YearExcellentFilter;
import com.azp.core.sys.datainterface.YearExcellentDAO;
import com.azp.core.sys.dataobject.YearExcellentDO;
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
public class YearExcellentService {
  @Autowired
  private YearExcellentDAO yearExcellentDAO;

  public YearExcellent getByPK(String key) {
    YearExcellentDO entity = yearExcellentDAO.selectByPrimaryKey(key);
    return YearExcellentData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return YearExcellentSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    YearExcellent modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return YearExcellentDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(YearExcellentFilterMapper filterMapper) {
    return yearExcellentDAO.countByExample(YearExcellentFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<YearExcellent> getListByFilter(YearExcellentFilterMapper filterMapper) {
    List<YearExcellent> entityList = new ArrayList<>();
    for (YearExcellentDO entity : yearExcellentDAO.selectByExample(YearExcellentFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(YearExcellentData.convert(entity, new YearExcellent()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(YearExcellentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(YearExcellentSimpleMapper.buildMap(entity)));
    return entityMapList;
  }
  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(YearExcellentFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query yearExcellent data;
    List<YearExcellent> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (YearExcellent modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (YearExcellent modelEntity : modelEntityList) {
      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(YearExcellentDetailMapper.buildMap(modelEntity));

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

  public YearExcellent post(YearExcellent postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      yearExcellentDAO.insertSelective(YearExcellentData.convert(postEntity, new YearExcellentDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(YearExcellentPostMapper postMapper) {
    YearExcellent entity = post(postMapper.buildEntity());
    return YearExcellentDetailMapper.buildMap(entity);
  }

  public List<YearExcellent> postList(List<YearExcellent> postEntities) {
    List<YearExcellent> entityList = new ArrayList<>();
    for (YearExcellent postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<YearExcellentPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (YearExcellentPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public YearExcellent update(YearExcellent updateEntity) {
    YearExcellent modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    yearExcellentDAO.updateByPrimaryKeySelective(YearExcellentData.convert(updateEntity, new YearExcellentDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(YearExcellentUpdateMapper updateMapper) {
    YearExcellent entity = update(updateMapper.buildEntity());
    return YearExcellentDetailMapper.buildMap(entity);
  }

  public List<YearExcellent> updateList(List<YearExcellent> updateEntities) {
    List<YearExcellent> entityList = new ArrayList<>();
    for (YearExcellent updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<YearExcellentUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (YearExcellentUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public YearExcellent put(YearExcellent putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    YearExcellent modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      yearExcellentDAO.insertSelective(YearExcellentData.convert(putEntity, new YearExcellentDO()));
    }
    else {
      yearExcellentDAO.updateByPrimaryKeySelective(YearExcellentData.convert(putEntity, new YearExcellentDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(YearExcellentUpdateMapper putMapper) {
    YearExcellent entity = put(putMapper.buildEntity());
    return YearExcellentDetailMapper.buildMap(entity);
  }

  public List<YearExcellent> putList(List<YearExcellent> putEntities) {
    List<YearExcellent> entityList = new ArrayList<>();
    for (YearExcellent putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<YearExcellentUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (YearExcellentUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(yearExcellentDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(YearExcellentFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new YearExcellentFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<YearExcellentFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (YearExcellentFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(YearExcellentFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(YearExcellentFilterMapper filterMapper,
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
