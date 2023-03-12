package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.LoginRecordFilter;
import com.azp.core.sys.datainterface.LoginRecordDAO;
import com.azp.core.sys.dataobject.LoginRecordDO;
import com.azp.core.sys.model.LoginRecord;
import com.azp.core.sys.model.LoginRecordData;
import com.azp.core.sys.model.LoginRecordDetailMapper;
import com.azp.core.sys.model.LoginRecordFilterMapper;
import com.azp.core.sys.model.LoginRecordPostMapper;
import com.azp.core.sys.model.LoginRecordSimpleMapper;
import com.azp.core.sys.model.LoginRecordUpdateMapper;
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
public class LoginRecordService {
  @Autowired
  private LoginRecordDAO loginRecordDAO;

  @Autowired
  private UserService userService;

  public LoginRecord getByPK(String key) {
    LoginRecordDO entity = loginRecordDAO.selectByPrimaryKey(key);
    return LoginRecordData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return LoginRecordSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    LoginRecord modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build user data from local database;
    UserFilterMapper userFilterMapper = new UserFilterMapper();
    userFilterMapper.id = modelEntity.getUserId();
    userFilterMapper.page = 0L;
    userFilterMapper.rows = 0;
    Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return LoginRecordDetailMapper.buildMapExtra(modelEntity,userData);
  }

  public Long getCountByFilter(LoginRecordFilterMapper filterMapper) {
    return loginRecordDAO.countByExample(LoginRecordFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<LoginRecord> getListByFilter(LoginRecordFilterMapper filterMapper) {
    List<LoginRecord> entityList = new ArrayList<>();
    for (LoginRecordDO entity : loginRecordDAO.selectByExample(LoginRecordFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(LoginRecordData.convert(entity, new LoginRecord()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(LoginRecordFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(LoginRecordSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(LoginRecordFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query loginRecord data;
    List<LoginRecord> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> userIdList = new ArrayList<>();
    for (LoginRecord modelEntity : modelEntityList) {
      userIdList.add(modelEntity.getUserId());
    }
    // load data from local database or remote service;
    List<User> userList = userService.getListByRelatedId(userIdList);
    // loop assembly data;
    for (LoginRecord modelEntity : modelEntityList) {
      // filter, map, and form user data;
      Map<String, Object> userData = userList.stream()
          .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
          .map(UserSimpleMapper::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(LoginRecordDetailMapper.buildMapExtra(modelEntity,userData));
    }
    return entityMapList;
  }

  public LoginRecord post(LoginRecord postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      loginRecordDAO.insertSelective(LoginRecordData.convert(postEntity, new LoginRecordDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(LoginRecordPostMapper postMapper) {
    LoginRecord entity = post(postMapper.buildEntity());
    return LoginRecordDetailMapper.buildMap(entity);
  }

  public List<LoginRecord> postList(List<LoginRecord> postEntities) {
    List<LoginRecord> entityList = new ArrayList<>();
    for (LoginRecord postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<LoginRecordPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LoginRecordPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public LoginRecord update(LoginRecord updateEntity) {
    LoginRecord modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    loginRecordDAO.updateByPrimaryKeySelective(LoginRecordData.convert(updateEntity, new LoginRecordDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(LoginRecordUpdateMapper updateMapper) {
    LoginRecord entity = update(updateMapper.buildEntity());
    return LoginRecordDetailMapper.buildMap(entity);
  }

  public List<LoginRecord> updateList(List<LoginRecord> updateEntities) {
    List<LoginRecord> entityList = new ArrayList<>();
    for (LoginRecord updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<LoginRecordUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LoginRecordUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public LoginRecord put(LoginRecord putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    LoginRecord modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      loginRecordDAO.insertSelective(LoginRecordData.convert(putEntity, new LoginRecordDO()));
    }
    else {
      loginRecordDAO.updateByPrimaryKeySelective(LoginRecordData.convert(putEntity, new LoginRecordDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(LoginRecordUpdateMapper putMapper) {
    LoginRecord entity = put(putMapper.buildEntity());
    return LoginRecordDetailMapper.buildMap(entity);
  }

  public List<LoginRecord> putList(List<LoginRecord> putEntities) {
    List<LoginRecord> entityList = new ArrayList<>();
    for (LoginRecord putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<LoginRecordUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (LoginRecordUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(loginRecordDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(LoginRecordFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new LoginRecordFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<LoginRecordFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (LoginRecordFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(LoginRecordFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(LoginRecordFilterMapper filterMapper,
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
