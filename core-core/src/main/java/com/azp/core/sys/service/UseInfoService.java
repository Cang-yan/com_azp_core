package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.UseInfoFilter;
import com.azp.core.sys.datainterface.UseInfoDAO;
import com.azp.core.sys.dataobject.UseInfoDO;
import com.azp.core.sys.model.Level;
import com.azp.core.sys.model.LevelFilterMapper;
import com.azp.core.sys.model.LevelSimpleMapper;
import com.azp.core.sys.model.UseInfo;
import com.azp.core.sys.model.UseInfoData;
import com.azp.core.sys.model.UseInfoDetailMapper;
import com.azp.core.sys.model.UseInfoFilterMapper;
import com.azp.core.sys.model.UseInfoPostMapper;
import com.azp.core.sys.model.UseInfoSimpleMapper;
import com.azp.core.sys.model.UseInfoUpdateMapper;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserFilterMapper;
import com.azp.core.sys.model.UserSimpleMapper;
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
public class UseInfoService {
  @Autowired
  private UseInfoDAO useInfoDAO;

  @Autowired
  private LevelService levelService;

  @Autowired
  private UserService userService;

  public UseInfo getByPK(String key) {
    UseInfoDO entity = useInfoDAO.selectByPrimaryKey(key);
    return UseInfoData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return UseInfoSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    UseInfo modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build level data from local database;
    LevelFilterMapper levelFilterMapper = new LevelFilterMapper();
    levelFilterMapper.id = modelEntity.getLevelId();
    levelFilterMapper.page = 0L;
    levelFilterMapper.rows = 0;
    Map<String, Object> levelData = levelService.getFilterMapList(levelFilterMapper).stream().findFirst().orElse(new HashMap<>());
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return UseInfoDetailMapper.buildMapExtra(modelEntity,levelData,userData);
  }

  public Long getCountByFilter(UseInfoFilterMapper filterMapper) {
    return useInfoDAO.countByExample(UseInfoFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<UseInfo> getListByFilter(UseInfoFilterMapper filterMapper) {
    List<UseInfo> entityList = new ArrayList<>();
    for (UseInfoDO entity : useInfoDAO.selectByExample(UseInfoFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(UseInfoData.convert(entity, new UseInfo()));
    }
    return entityList;
  }

  public List<UseInfo> getListByRelatedId(List<String> idList) {
    List<UseInfo> entityList = new ArrayList<>();
    if (idList.size() == 0) return entityList;
    for (UseInfoDO entity : useInfoDAO.selectByExample(UseInfoFilter.initIdQueryFilter(idList))) {
      entityList.add(UseInfoData.convert(entity, new UseInfo()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(UseInfoFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(UseInfoSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(UseInfoFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query useInfo data;
    List<UseInfo> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> levelIdList = new ArrayList<>();
    ArrayList<String> userIdList = new ArrayList<>();
    for (UseInfo modelEntity : modelEntityList) {
      levelIdList.add(modelEntity.getLevelId());
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<Level> levelList = levelService.getListByRelatedId(levelIdList);
    List<User> userList = userService.getListByRelatedId(userIdList);
    // loop assembly data;
    for (UseInfo modelEntity : modelEntityList) {
      // filter, map, and form level data;
      Map<String, Object> levelData = levelList.stream()
          .filter(item -> modelEntity.getLevelId() != null && modelEntity.getLevelId().equals(item.getId()))
          .map(LevelSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(UseInfoDetailMapper.buildMapExtra(modelEntity,levelData,userData));
    }
    return entityMapList;
  }

  public UseInfo post(UseInfo postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      useInfoDAO.insertSelective(UseInfoData.convert(postEntity, new UseInfoDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(UseInfoPostMapper postMapper) {
    UseInfo entity = post(postMapper.buildEntity());
    return UseInfoDetailMapper.buildMap(entity);
  }

  public List<UseInfo> postList(List<UseInfo> postEntities) {
    List<UseInfo> entityList = new ArrayList<>();
    for (UseInfo postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<UseInfoPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UseInfoPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public UseInfo update(UseInfo updateEntity) {
    UseInfo modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    useInfoDAO.updateByPrimaryKeySelective(UseInfoData.convert(updateEntity, new UseInfoDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(UseInfoUpdateMapper updateMapper) {
    UseInfo entity = update(updateMapper.buildEntity());
    return UseInfoDetailMapper.buildMap(entity);
  }

  public List<UseInfo> updateList(List<UseInfo> updateEntities) {
    List<UseInfo> entityList = new ArrayList<>();
    for (UseInfo updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<UseInfoUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UseInfoUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public UseInfo put(UseInfo putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    UseInfo modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      useInfoDAO.insertSelective(UseInfoData.convert(putEntity, new UseInfoDO()));
    }
    else {
      useInfoDAO.updateByPrimaryKeySelective(UseInfoData.convert(putEntity, new UseInfoDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(UseInfoUpdateMapper putMapper) {
    UseInfo entity = put(putMapper.buildEntity());
    return UseInfoDetailMapper.buildMap(entity);
  }

  public List<UseInfo> putList(List<UseInfo> putEntities) {
    List<UseInfo> entityList = new ArrayList<>();
    for (UseInfo putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<UseInfoUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UseInfoUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(useInfoDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(UseInfoFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new UseInfoFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<UseInfoFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (UseInfoFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(UseInfoFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(UseInfoFilterMapper filterMapper,
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
