package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.AwardSkillExcellenceFilter;
import com.azp.core.sys.datainterface.AwardSkillExcellenceDAO;
import com.azp.core.sys.dataobject.AwardSkillExcellenceDO;
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
public class AwardSkillExcellenceService {
  @Autowired
  private AwardSkillExcellenceDAO awardSkillExcellenceDAO;

  public AwardSkillExcellence getByPK(String key) {
    AwardSkillExcellenceDO entity = awardSkillExcellenceDAO.selectByPrimaryKey(key);
    return AwardSkillExcellenceData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return AwardSkillExcellenceSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    AwardSkillExcellence modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    return AwardSkillExcellenceDetailMapper.buildMap(modelEntity);
  }

  public Long getCountByFilter(AwardSkillExcellenceFilterMapper filterMapper) {
    return awardSkillExcellenceDAO.countByExample(AwardSkillExcellenceFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<AwardSkillExcellence> getListByFilter(AwardSkillExcellenceFilterMapper filterMapper) {
    List<AwardSkillExcellence> entityList = new ArrayList<>();
    for (AwardSkillExcellenceDO entity : awardSkillExcellenceDAO.selectByExample(AwardSkillExcellenceFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(AwardSkillExcellenceData.convert(entity, new AwardSkillExcellence()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(AwardSkillExcellenceFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(AwardSkillExcellenceSimpleMapper.buildMap(entity)));
    return entityMapList;
  }
  @Autowired
  UserService userService;
  @Autowired
  UseInfoService useInfoService;
  public List<Map<String, Object>> getFilterDetailMapList(AwardSkillExcellenceFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query awardSkillExcellence data;
    List<AwardSkillExcellence> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    for (AwardSkillExcellence modelEntity : modelEntityList) {
    }
    // load data from local database or remote service;
    // loop assembly data;
    for (AwardSkillExcellence modelEntity : modelEntityList) {
      Map<String,Object> detailMapper = new HashMap<>();
      detailMapper.putAll(AwardSkillExcellenceDetailMapper.buildMap(modelEntity));

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

  public AwardSkillExcellence post(AwardSkillExcellence postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      awardSkillExcellenceDAO.insertSelective(AwardSkillExcellenceData.convert(postEntity, new AwardSkillExcellenceDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(AwardSkillExcellencePostMapper postMapper) {
    AwardSkillExcellence entity = post(postMapper.buildEntity());
    return AwardSkillExcellenceDetailMapper.buildMap(entity);
  }

  public List<AwardSkillExcellence> postList(List<AwardSkillExcellence> postEntities) {
    List<AwardSkillExcellence> entityList = new ArrayList<>();
    for (AwardSkillExcellence postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<AwardSkillExcellencePostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSkillExcellencePostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public AwardSkillExcellence update(AwardSkillExcellence updateEntity) {
    AwardSkillExcellence modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    awardSkillExcellenceDAO.updateByPrimaryKeySelective(AwardSkillExcellenceData.convert(updateEntity, new AwardSkillExcellenceDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(AwardSkillExcellenceUpdateMapper updateMapper) {
    AwardSkillExcellence entity = update(updateMapper.buildEntity());
    return AwardSkillExcellenceDetailMapper.buildMap(entity);
  }

  public List<AwardSkillExcellence> updateList(List<AwardSkillExcellence> updateEntities) {
    List<AwardSkillExcellence> entityList = new ArrayList<>();
    for (AwardSkillExcellence updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<AwardSkillExcellenceUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSkillExcellenceUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public AwardSkillExcellence put(AwardSkillExcellence putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    AwardSkillExcellence modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      awardSkillExcellenceDAO.insertSelective(AwardSkillExcellenceData.convert(putEntity, new AwardSkillExcellenceDO()));
    }
    else {
      awardSkillExcellenceDAO.updateByPrimaryKeySelective(AwardSkillExcellenceData.convert(putEntity, new AwardSkillExcellenceDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(AwardSkillExcellenceUpdateMapper putMapper) {
    AwardSkillExcellence entity = put(putMapper.buildEntity());
    return AwardSkillExcellenceDetailMapper.buildMap(entity);
  }

  public List<AwardSkillExcellence> putList(List<AwardSkillExcellence> putEntities) {
    List<AwardSkillExcellence> entityList = new ArrayList<>();
    for (AwardSkillExcellence putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<AwardSkillExcellenceUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (AwardSkillExcellenceUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(awardSkillExcellenceDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  public Map<String, Object> getRateAndCountByFilter(AwardSkillExcellenceFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new AwardSkillExcellenceFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<AwardSkillExcellenceFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (AwardSkillExcellenceFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(AwardSkillExcellenceFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(AwardSkillExcellenceFilterMapper filterMapper,
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
