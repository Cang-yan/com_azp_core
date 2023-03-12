package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class GroupPointStatisticsExtendsService {

    @Autowired
    private GroupPointStatisticsService groupPointStatisticsService;

    @Autowired
    private ActivityTypeFourService activityTypeFourService;

    @Autowired
    private ActivityTypeFourUserService activityTypeFourUserService;

    /**
     * 获取当期组队列表及其userList
     *
     * @param filterMapper
     * @return
     */
    public List<Map<String, Object>> getFilterDetailMapList(GroupPointStatisticsFilterMapper filterMapper) {
        List<Map<String, Object>> entityMapList = new ArrayList<>();
        // query groupPointStatistics data;
        filterMapper.status = 0;
        List<GroupPointStatistics> modelEntityList = groupPointStatisticsService.getListByFilter(filterMapper);
        // loop & batch find to release database pressure;
        ArrayList<String> groupIdList = new ArrayList<>();
        for (GroupPointStatistics modelEntity : modelEntityList) {
            groupIdList.add(modelEntity.getGroupId());
        }
        // load data from local database or remote service;
        List<ActivityTypeFour> activityTypeFourList = activityTypeFourService.getListByRelatedId(groupIdList);
        activityTypeFourList = activityTypeFourList.stream().filter((a) -> a.getStatus() == 3).collect(Collectors.toList());
        // loop assembly data;
        for (GroupPointStatistics modelEntity : modelEntityList) {
            // filter, map, and form activityTypeFour data;
            Map<String, Object> activityTypeFourData = activityTypeFourList.stream()
                    .filter(item -> modelEntity.getGroupId() != null && modelEntity.getGroupId().equals(item.getId()))
                    .map(this::buildMap)
                    .collect(Collectors.toList()).stream().findFirst().orElse(new HashMap<>());
            entityMapList.add(GroupPointStatisticsDetailMapper.buildMapExtra(modelEntity, activityTypeFourData));
        }
        return entityMapList;
    }

    public Map<String, Object> buildMap(ActivityTypeFour group) {
        Map<String, Object> activityTypeFourMap = ActivityTypeFourSimpleMapper.buildMap(group);
        ActivityTypeFourUserFilterMapper userFilter = new ActivityTypeFourUserFilterMapper();
        userFilter.activityTypeFourId = group.getId();
        userFilter.statusIn = Collections.singletonList(5);
        List<ActivityTypeFourUser> userList = activityTypeFourUserService.getListByFilter(userFilter);
        activityTypeFourMap.put("userList", userList);
        return activityTypeFourMap;
    }
}
