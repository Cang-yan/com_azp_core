package com.azp.core.sys.service;

import com.azp.core.sys.datafilter.GroupPointStatisticsFilter;
import com.azp.core.sys.datainterface.GroupPointStatisticsDAO;
import com.azp.core.sys.dataobject.GroupPointStatisticsDO;
import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class GroupPointStatisticsService {
  @Autowired
  private GroupPointStatisticsDAO groupPointStatisticsDAO;

  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @Autowired
  private ActivityTypeFourUserService activityTypeFourUserService;

  @Autowired
  private UserService userService;

  public GroupPointStatistics getByPK(String key) {
    GroupPointStatisticsDO entity = groupPointStatisticsDAO.selectByPrimaryKey(key);
    return GroupPointStatisticsData.convert(entity, null);
  }

  public Map<String, Object> getSimpleMapByPK(String key) {
    return GroupPointStatisticsSimpleMapper.buildMap(getByPK(key));
  }

  public Map<String, Object> getDetailMapByPK(String key) {
    GroupPointStatistics modelEntity = getByPK(key);
    if (modelEntity == null) return null;
    // build activityTypeFour data from local database;
    ActivityTypeFourFilterMapper activityTypeFourFilterMapper = new ActivityTypeFourFilterMapper();
    activityTypeFourFilterMapper.id = modelEntity.getGroupId();
    activityTypeFourFilterMapper.page = 0L;
    activityTypeFourFilterMapper.rows = 0;
    Map<String, Object> activityTypeFourData = activityTypeFourService.getFilterMapList(activityTypeFourFilterMapper).stream().findFirst().orElse(new HashMap<>());
    return GroupPointStatisticsDetailMapper.buildMapExtra(modelEntity,activityTypeFourData);
  }

  public Long getCountByFilter(GroupPointStatisticsFilterMapper filterMapper) {
    return groupPointStatisticsDAO.countByExample(GroupPointStatisticsFilter.initDOQueryFilter(filterMapper.buildMap()));
  }

  public List<GroupPointStatistics> getListByFilter(GroupPointStatisticsFilterMapper filterMapper) {
    List<GroupPointStatistics> entityList = new ArrayList<>();
    for (GroupPointStatisticsDO entity : groupPointStatisticsDAO.selectByExample(GroupPointStatisticsFilter.initDOQueryFilter(filterMapper.buildMap()))) {
      entityList.add(GroupPointStatisticsData.convert(entity, new GroupPointStatistics()));
    }
    return entityList;
  }

  public List<Map<String, Object>> getFilterMapList(GroupPointStatisticsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    getListByFilter(filterMapper).forEach(entity -> entityMapList.add(GroupPointStatisticsSimpleMapper.buildMap(entity)));
    return entityMapList;
  }

  public List<Map<String, Object>> getFilterDetailMapList(GroupPointStatisticsFilterMapper filterMapper) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // query groupPointStatistics data;
    List<GroupPointStatistics> modelEntityList = getListByFilter(filterMapper);
    // loop & batch find to release database pressure;
    ArrayList<String> groupIdList = new ArrayList<>();
    for (GroupPointStatistics modelEntity : modelEntityList) {
      groupIdList.add(modelEntity.getGroupId());
    }
    // load data from local database or remote service;
    List<ActivityTypeFour> activityTypeFourList = activityTypeFourService.getListByRelatedId(groupIdList);
    // loop assembly data;
    for (GroupPointStatistics modelEntity : modelEntityList) {
      // filter, map, and form activityTypeFour data;
      Map<String, Object> activityTypeFourData = activityTypeFourList.stream()
          .filter(item -> modelEntity.getGroupId() != null && modelEntity.getGroupId().equals(item.getId()))
          .map(this::buildMap)
          .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
      entityMapList.add(GroupPointStatisticsDetailMapper.buildMapExtra(modelEntity,activityTypeFourData));
    }
    return entityMapList;
  }

  public GroupPointStatistics post(GroupPointStatistics postEntity) {
    postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
    try {
      groupPointStatisticsDAO.insertSelective(GroupPointStatisticsData.convert(postEntity, new GroupPointStatisticsDO()));
    }
    catch(DuplicateKeyException e) {
      throw new StatusException("POST_ENTITY_DUPLICATE");
    }
    return getByPK(postEntity.getId());
  }

  public Map<String, Object> postMapping(GroupPointStatisticsPostMapper postMapper) {
    GroupPointStatistics entity = post(postMapper.buildEntity());
    return GroupPointStatisticsDetailMapper.buildMap(entity);
  }

  public List<GroupPointStatistics> postList(List<GroupPointStatistics> postEntities) {
    List<GroupPointStatistics> entityList = new ArrayList<>();
    for (GroupPointStatistics postEntity : postEntities) {
      entityList.add(post(postEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> postMappingList(List<GroupPointStatisticsPostMapper> postMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (GroupPointStatisticsPostMapper postMapper : postMappers) {
      entityMapList.add(postMapping(postMapper));
    }
    return entityMapList;
  }

  public GroupPointStatistics update(GroupPointStatistics updateEntity) {
    GroupPointStatistics modelEntity = getByPK(updateEntity.getId());
    Assert.notNull(modelEntity, "UPDATE_ENTITY_NULL");
    groupPointStatisticsDAO.updateByPrimaryKeySelective(GroupPointStatisticsData.convert(updateEntity, new GroupPointStatisticsDO()));
    return getByPK(updateEntity.getId());
  }

  public Map<String, Object> updateMapping(GroupPointStatisticsUpdateMapper updateMapper) {
    GroupPointStatistics entity = update(updateMapper.buildEntity());
    return GroupPointStatisticsDetailMapper.buildMap(entity);
  }

  public List<GroupPointStatistics> updateList(List<GroupPointStatistics> updateEntities) {
    List<GroupPointStatistics> entityList = new ArrayList<>();
    for (GroupPointStatistics updateEntity : updateEntities) {
      entityList.add(update(updateEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> updateMappingList(List<GroupPointStatisticsUpdateMapper> updateMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (GroupPointStatisticsUpdateMapper updateMapper : updateMappers) {
      entityMapList.add(updateMapping(updateMapper));
    }
    return entityMapList;
  }

  public GroupPointStatistics put(GroupPointStatistics putEntity) {
    Assert.notNull(putEntity.getId(), "PUT_ENTITY_ID_NULL");
    GroupPointStatistics modelEntity = getByPK(putEntity.getId());
    if (modelEntity == null) {
      groupPointStatisticsDAO.insertSelective(GroupPointStatisticsData.convert(putEntity, new GroupPointStatisticsDO()));
    }
    else {
      groupPointStatisticsDAO.updateByPrimaryKeySelective(GroupPointStatisticsData.convert(putEntity, new GroupPointStatisticsDO()));
    }
    return getByPK(putEntity.getId());
  }

  public Map<String, Object> putMapping(GroupPointStatisticsUpdateMapper putMapper) {
    GroupPointStatistics entity = put(putMapper.buildEntity());
    return GroupPointStatisticsDetailMapper.buildMap(entity);
  }

  public List<GroupPointStatistics> putList(List<GroupPointStatistics> putEntities) {
    List<GroupPointStatistics> entityList = new ArrayList<>();
    for (GroupPointStatistics putEntity : putEntities) {
      entityList.add(put(putEntity));
    }
    return entityList;
  }

  public List<Map<String, Object>> putMappingList(List<GroupPointStatisticsUpdateMapper> putMappers) {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (GroupPointStatisticsUpdateMapper putMapper : putMappers) {
      entityMapList.add(putMapping(putMapper));
    }
    return entityMapList;
  }

  public Integer delete(String key) {
    AtomicInteger count = new AtomicInteger();
    count.addAndGet(groupPointStatisticsDAO.deleteByPrimaryKey(key));
    return count.get();
  }

  public Integer deleteList(List<String> keys) {
    AtomicInteger count = new AtomicInteger();
    for (String key: keys) {
      count.addAndGet(delete(key));
    }
    return count.get();
  }

  private Map<String, Object> buildMap(ActivityTypeFour obj) {
    Map<String, Object> map = ActivityTypeFourSimpleMapper.buildMap(obj);
    if (CollectionUtils.isEmpty(map)) return map;
    ActivityTypeFourUserFilterMapper activityTypeFourUserFilterMapper = new ActivityTypeFourUserFilterMapper();
    activityTypeFourUserFilterMapper.activityTypeFourId = obj.getId();
    activityTypeFourUserFilterMapper.statusIn = Collections.singletonList(5);
    List<Map<String, Object>> userMapList = activityTypeFourUserService.getListByFilter(activityTypeFourUserFilterMapper)
            .stream().sorted(new Comparator<ActivityTypeFourUser>() {
              @Override
              public int compare(ActivityTypeFourUser o1, ActivityTypeFourUser o2) {
                return o1.getPlace() - o2.getPlace();
              }
            }).map(ActivityTypeFourUser::getUserId)
            .map(userService::getDetailMapByPK)
            .collect(Collectors.toList());
    map.put("userList", userMapList);
    return map;
  }

  public Map<String, Object> getRateAndCountByFilter(GroupPointStatisticsFilterMapper filterMapper) {
    Map<String, Object> map = new HashMap<>();
    Long countByFilter = getCountByFilter(filterMapper);
    Long totalCount = getCountByFilter(new GroupPointStatisticsFilterMapper());
    map.put("filter", filterMapper);
    map.put("count", countByFilter);
    if (totalCount > 0L) map.put("rate", countByFilter / totalCount.doubleValue());
    else map.put("rate", 0.0);
    return map;
  }

  public List<Map<String, Object>> getRateAndCountListByFilter(List<GroupPointStatisticsFilterMapper> filterMappers) {
    List<Map<String, Object>> rateAndCountMapList = new ArrayList<>();
    for (GroupPointStatisticsFilterMapper filterMapper : filterMappers) {
      rateAndCountMapList.add(getRateAndCountByFilter(filterMapper));
    }
    return rateAndCountMapList;
  }

  public Map<String, Map<String, Object>> getFilterListGroup(GroupPointStatisticsFilterMapper filterMapper,
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

  public Map<String, Map<String, Object>> getFilterDetailListGroup(GroupPointStatisticsFilterMapper filterMapper,
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
