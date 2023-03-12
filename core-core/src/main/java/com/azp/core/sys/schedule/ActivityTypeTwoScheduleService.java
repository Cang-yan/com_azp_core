package com.azp.core.sys.schedule;


import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.model.ActivityTypeTwoUser;
import com.azp.core.sys.model.ActivityTypeTwoUserFilterMapper;
import com.azp.core.sys.service.ActivityTypeTwoService;
import com.azp.core.sys.service.ActivityTypeTwoUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@TaskScheduleComponent
public class ActivityTypeTwoScheduleService {

    private static long current = 0;

    @Autowired
    private ActivityTypeTwoService activityTypeTwoService;

    @Autowired
    private ActivityTypeTwoUserService activityTypeTwoUserService;

    @Task(cron = "59 * * * * ?")
    @Distributed
    public Runnable refresh() {
        return () -> {
            current = new Date().getTime();
            ActivityTypeTwoFilterMapper mapper = new ActivityTypeTwoFilterMapper();
            mapper.statusIn = Arrays.asList(1, 3);
            List<ActivityTypeTwo> activityTypeTwoEntityList = activityTypeTwoService.getListByFilter(mapper);
            for (ActivityTypeTwo activityTypeTwoEntity : activityTypeTwoEntityList) {
                if (activityTypeTwoEntity.getEndDate() == null) {
                    continue;
                }
                if (activityTypeTwoEntity.getEndDate().getTime() <= current) {
                    activityTypeTwoEntity.setStatus(2);
                    ActivityTypeTwoUserFilterMapper activityTypeTwoUserFilterMapper = new ActivityTypeTwoUserFilterMapper();
                    activityTypeTwoUserFilterMapper.activityTypeTwoId = activityTypeTwoEntity.getId();
                    List<ActivityTypeTwoUser> activityTypeTwoUserList = activityTypeTwoUserService.getListByFilter(activityTypeTwoUserFilterMapper);
                    for (ActivityTypeTwoUser activityTypeTwoUser : activityTypeTwoUserList) {
                        if (activityTypeTwoUser.getStatus() == 4) {
                            continue;
                        }
                        activityTypeTwoUser.setStatus(1);
                    }
                    activityTypeTwoUserService.updateList(activityTypeTwoUserList);
                }
                //修改这个活动对应的所有人的状态
            }
            activityTypeTwoService.updateList(activityTypeTwoEntityList);
        };
    }
}
