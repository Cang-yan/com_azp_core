package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class LevelExtendsService {

    @Autowired
    private LevelRecordService levelRecordService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private UseInfoService useInfoService;

    @Autowired
    private DepartmentService departmentService;

    public List<Map<String, Object>> getDepartmentInLevelRecord(List<String> departmentIn) {
        LevelFilterMapper levelFilterMapper = new LevelFilterMapper();
        levelFilterMapper.orderBy = Collections.singletonList("idx asc");
        List<Level> levelList = levelService.getListByFilter(levelFilterMapper);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Level level : levelList) {
            Map<String, Object> levelMap = new HashMap<>();
            levelMap.put("level", level);
            LevelRecordFilterMapper levelRecordFilterMapper = new LevelRecordFilterMapper();
            levelRecordFilterMapper.levelId = level.getId();
            levelRecordFilterMapper.departmentIdIn = CollectionUtils.isEmpty(departmentIn) ? null : departmentIn;
            levelRecordFilterMapper.roleType = level.getMinPoint() + "-" + level.getMaxPoint();
            List<LevelRecord> levelRecordList = levelRecordService.getListByFilter(levelRecordFilterMapper);
            levelMap.put("levelRecords", levelRecordList);
            mapList.add(levelMap);
        }
        return mapList;
    }

    public List<Map<String, Object>> getDepartmentInLevelUser(String department, String levelId) {
        DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
        departmentFilterMapper.name = "Visitor";
        Department visitor = departmentService.getListByFilter(departmentFilterMapper).get(0);
        Level level = levelService.getByPK(levelId);
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.departmentId = department.equals("0") ? null : department;
        List<User> userList = userService.getListByFilter(userFilterMapper);
        List<String> userInfoId = userList.stream().filter(user -> !user.getDepartmentId().equals(visitor.getId())).map(User::getUserInfoId).collect(Collectors.toList());
        List<UseInfo> useInfos = useInfoService.getListByRelatedId(userInfoId);
        return useInfos.stream().filter(user -> {
            return user.getPointNumber() <= level.getMaxPoint() && user.getPointNumber() >= level.getMinPoint();
        }).map(UseInfo::getUserId).map(userService::getDetailMapByPK).collect(Collectors.toList());
    }

}
