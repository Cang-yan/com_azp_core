package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContextHolder;
import com.azp.core.sys.model.*;
import com.azp.core.sys.service.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:04
 */
@TaskScheduleComponent
public class MonthExcellentScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    MonthExcellentService monthExcellentService;
    @Autowired
    ScanExcelToolService scanExcelToolService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationUserService notificationUserService;
    @Autowired
    UserService userService;
    @Autowired
    SchedulerTemplate schedulerTemplate;
    @Task(cron = "0 0/5 * * * ?")
    @Distributed
    public Runnable refresh() {
        return () -> {
            lastCurrent = schedulerTemplate.getLastScheduleTime();
            current = new Date().getTime();

            MonthExcellentFilterMapper monthExcellentFilterMapper = new MonthExcellentFilterMapper();
            monthExcellentFilterMapper.gmtCreateFrom = lastCurrent;
            monthExcellentFilterMapper.gmtCreateTo = current;
            for (MonthExcellent monthExcellent:monthExcellentService.getListByFilter(monthExcellentFilterMapper)) {
                scanExcelToolService.postPointAndNotify(monthExcellent.getUserCode(),monthExcellent.getPoint(),9,monthExcellent.getId(),ActivityTypeFiveRelationEnum.MONTH);
            }

        };
    }
}
