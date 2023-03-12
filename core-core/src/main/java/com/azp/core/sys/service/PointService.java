package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.PointFilter;
import com.azp.core.sys.datainterface.PointDAO;
import com.azp.core.sys.dataobject.PointDO;
import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class PointService {
  @Autowired
  private PointDAO pointDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityTypeThreeService activityTypeThreeService;

  @Autowired
  private ActivityTypeTwoUserService activityTypeTwoUserService;

  @Autowired
  private ActivityTypeTwoService activityTypeTwoService;

  @Autowired
  private ActivityTypeOneUserService activityTypeOneUserService;

  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  public Point getByPK(String key) {
    PointDO entity = pointDAO.selectByPrimaryKey(key);
    return PointData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return PointSimpleMapper.buildMap(getByPK(key));
  }

    public Map<String, Object> getDetailMapByPK(String key) {
        Point modelEntity = getByPK(key);
        if (modelEntity == null) return null;
        // build user data from local database;
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.id = modelEntity.getUserId();
        userFilterMapper.page = 0L;
        userFilterMapper.rows = 0;
        Map<String, Object> userData = userService.getFilterMapList(userFilterMapper).stream().findFirst().orElse(new HashMap<>());
        return PointDetailMapper.buildMapExtra(modelEntity, userData, parseActivity(modelEntity));
    }

  public Long getCountByFilter(PointFilterMapper filterMapper) {
    return pointDAO.countByExample(PointFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<Point> getListByFilter(PointFilterMapper filterMapper) {
    List<Point> entityList = new ArrayList<>();
    for (PointDO entity : pointDAO.selectByExample(PointFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(PointData.convert(entity, new Point()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(PointFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(PointSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

    public List<Map<String, Object>> getFilterDetailMapList(PointFilterMapper filterMapper) {
        List<Map<String, Object>> entityMapList = new ArrayList<>();
        // query point data;
        List<Point> modelEntityList = getListByFilter(filterMapper);
        // loop & batch find to release database pressure;
        ArrayList<String> userIdList = new ArrayList<>();
        for (Point modelEntity : modelEntityList) {
            userIdList.add(modelEntity.getUserId());
        }
        // load data from local database or remote service;
        List<User> userList = userService.getListByRelatedId(userIdList);
        // loop assembly data;
        for (Point modelEntity : modelEntityList) {
            // filter, map, and form user data;
            Map<String, Object> userData = userList.stream()
                    .filter(item -> modelEntity.getUserId() != null && modelEntity.getUserId().equals(item.getId()))
                    .map(UserSimpleMapper::buildMap)
                    .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
            entityMapList.add(PointDetailMapper.buildMapExtra(modelEntity, userData, parseActivity(modelEntity)));
        }
        return entityMapList;
    }

  public Point post(Point postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      pointDAO.insertSelective(PointData.convert(postEntity, new PointDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(PointPostMapper postMapper) {
    Point entity = post(postMapper.buildEntity());
    return PointDetailMapper.buildMap(entity);
  }

  public List<Point> postList(List<Point> postEntities) {
    List<Point> entityList = new ArrayList<>();
    for (Point postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<PointPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public Point update(Point updateEntity) {
    Point modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    pointDAO.updateByPrimaryKeySelective(PointData.convert(updateEntity, new PointDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(PointUpdateMapper updateMapper) {
    Point entity = update(updateMapper.buildEntity());
    return PointDetailMapper.buildMap(entity);
  }

  public List<Point> updateList(List<Point> updateEntities) {
    List<Point> entityList = new ArrayList<>();
    for (Point updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<PointUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public Point put(Point putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    Point modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      pointDAO.insertSelective(PointData.convert(putEntity, new PointDO()));
    }
    else {
      pointDAO.updateByPrimaryKeySelective(PointData.convert(putEntity, new PointDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(PointUpdateMapper putMapper) {
    Point entity = put(putMapper.buildEntity());
    return PointDetailMapper.buildMap(entity);
  }

  public List<Point> putList(List<Point> putEntities) {
    List<Point> entityList = new ArrayList<>();
    for (Point putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<PointUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (PointUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(pointDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  private Map<String, Object> parseActivity(Point point) {
    String name = "";
    Map<String, Object> map = new HashMap<>();
    map.put("name", name);
    String relationId = point.getRelationId();
    if (point.getType() == null || relationId == null) return map;
    switch (point.getType()) {
      case 2:
      case 3: {
        ActivityTypeOneUser activityTypeOneUser = activityTypeOneUserService.getByPK(relationId);
        if (activityTypeOneUser == null) return map;
        String activityTypeOneId = activityTypeOneUser.getActivityTypeOneId();
        ActivityTypeOne activityTypeOne = activityTypeOneService.getByPK(activityTypeOneId);
        if (activityTypeOne == null) return map;
        name = activityTypeOne.getName();
        break;
      }
      case 4:
      case 5: {
        ActivityTypeTwoUser activityTypeTwoUser = activityTypeTwoUserService.getByPK(relationId);
        if (activityTypeTwoUser == null) return map;
        String activityTypeTwoId = activityTypeTwoUser.getActivityTypeTwoId();
        ActivityTypeTwo activityTypeTwo = activityTypeTwoService.getByPK(activityTypeTwoId);
        if (activityTypeTwo == null) return map;
        name = activityTypeTwo.getName();
        break;
      }
      case 6:
      case 7: {
        ActivityTypeThree activityTypeThree = activityTypeThreeService.getByPK(relationId);
        if (activityTypeThree == null) return map;
        name = activityTypeThree.getName();
        break;
      }
      case 8:
      case 9: {
        name = ActivityTypeFiveRelationEnum.getNameByRelationId(point.getTemplateId());
        break;
      }
      default:
        return map;
    }
    map.put("name", name);
    return map;
  }

  public Map<String, Object> getRateAndCountByFilter(PointFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new PointFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<PointFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (PointFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(PointFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(PointFilterMapper filterMapper,
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
