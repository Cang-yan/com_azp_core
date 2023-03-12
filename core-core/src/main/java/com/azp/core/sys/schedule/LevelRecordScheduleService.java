package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import com.azp.core.sys.model.*;
import com.azp.core.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@TaskScheduleComponent
public class LevelRecordScheduleService {

    @Autowired
    private UseInfoService infoService;

    @Autowired
    private LevelRecordService levelRecordService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private DepartmentExtendsService departmentExtendsService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Task(cron = "0 0/1 * * * ?")
    @Distributed
    public Runnable refresh() {
        return this::buildLevelRecord;
    }


    /**
     * 刷新积分等级区间
     *
     * @return
     */
    public void buildLevelRecord() {
        List<Level> levelList = getLevelList();
        DepartmentFilterMapper departmentFilterMapper = new DepartmentFilterMapper();
        departmentFilterMapper.name = "Visitor";
        Department visitor = departmentService.getListByFilter(departmentFilterMapper).stream().findFirst().orElse(null);
        int visitorNumber = 0;
        if (visitor != null) {
            UserFilterMapper userFilterMapper1 = new UserFilterMapper();
            userFilterMapper1.departmentId = visitor.getId();
            visitorNumber = userService.getCountByFilter(userFilterMapper1).intValue();
        }
        for (Level level : levelList) {
            UseInfoFilterMapper infoFilterMapper = new UseInfoFilterMapper();
            infoFilterMapper.pointNumberFrom = level.getMinPoint();
            infoFilterMapper.pointNumberTo = level.getMaxPoint();
            List<LevelRecord> levelRecords = levelRecordService.getListByRelatedLevelId(Collections.singletonList(level.getId()));
            LevelRecord levelRecord = levelRecords.stream().filter(l -> l.getDepartmentId().equals("0")).findFirst().orElse(null);
            // insert all
            if (levelRecord == null) {
                levelRecord = new LevelRecord();
                levelRecord.setLevelId(level.getId());
                levelRecord.setRoleType(level.getMinPoint() + "-" + level.getMaxPoint());
            }
            if (level.getId().equals("1")) {
                levelRecord.setPersonNumber(infoService.getCountByFilter(infoFilterMapper).intValue() - visitorNumber);
            } else {
                levelRecord.setPersonNumber(infoService.getCountByFilter(infoFilterMapper).intValue());
            }
            if (levelRecord.getId() == null) {
                levelRecordService.post(levelRecord);
            } else {
                levelRecord.setRoleType(level.getMinPoint() + "-" + level.getMaxPoint());
                levelRecordService.update(levelRecord);
            }
            // insert department
            List<Department> departmentList = departmentExtendsService.getList();
            List<UseInfo> useInfos = infoService.getListByFilter(infoFilterMapper);
            for (Department department : departmentList) {
                levelRecord = levelRecords.stream().filter(l -> l.getDepartmentId().equals(department.getId())).findFirst().orElse(null);
                if (levelRecord == null) {
                    levelRecord = new LevelRecord();
                    levelRecord.setLevelId(level.getId());
                    levelRecord.setRoleType(level.getMinPoint() + "-" + level.getMaxPoint());
                }
                List<UseInfo> useInfoList = useInfos.stream().filter((u) -> {
                    UserFilterMapper userFilterMapper = new UserFilterMapper();
                    userFilterMapper.userInfoId = u.getId();
                    String departmentId = userService.getListByFilter(userFilterMapper).stream().map(User::getDepartmentId).findFirst().orElse(null);
                    if (departmentId == null) return false;
                    return department.getId().equals(departmentId);
                }).collect(Collectors.toList());
                levelRecord.setPersonNumber(useInfoList.size());
                levelRecord.setDepartmentId(department.getId());
                if (levelRecord.getId() == null) {
                    levelRecordService.post(levelRecord);
                } else {
                    levelRecord.setRoleType(level.getMinPoint() + "-" + level.getMaxPoint());
                    levelRecordService.update(levelRecord);
                }
            }
        }
    }

    private List<Level> getLevelList() {
        LevelFilterMapper levelFilterMapper = new LevelFilterMapper();
        levelFilterMapper.page = 0L;
        levelFilterMapper.rows = 0;
        return levelService.getListByFilter(levelFilterMapper);
    }

}
