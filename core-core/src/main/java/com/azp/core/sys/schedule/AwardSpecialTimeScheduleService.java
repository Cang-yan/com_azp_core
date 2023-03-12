package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContextHolder;
import com.azp.core.sys.model.ActivityTypeFiveRelationEnum;
import com.azp.core.sys.model.AwardSpecialTime;
import com.azp.core.sys.model.AwardSpecialTimeFilterMapper;
import com.azp.core.sys.service.AwardSpecialTimeService;
import com.azp.core.sys.service.ScanExcelToolService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:27
 */

@TaskScheduleComponent
public class AwardSpecialTimeScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    AwardSpecialTimeService awardSpecialTimeService;
    @Autowired
    ScanExcelToolService scanExcelToolService;
    @Autowired
    SchedulerTemplate schedulerTemplate;
    @Task(cron = "0 0/5 * * * ?")
    @Distributed
    public Runnable refresh() {
        return () -> {
            lastCurrent = schedulerTemplate.getLastScheduleTime();
            current = new Date().getTime();

            AwardSpecialTimeFilterMapper awardSpecialTimeFilterMapper = new AwardSpecialTimeFilterMapper();
            awardSpecialTimeFilterMapper.gmtCreateFrom = lastCurrent;
            awardSpecialTimeFilterMapper.gmtCreateTo = current;
            for (AwardSpecialTime awardSpecialTime:awardSpecialTimeService.getListByFilter(awardSpecialTimeFilterMapper)) {
                scanExcelToolService.postPointAndNotify(awardSpecialTime.getUserCode(), awardSpecialTime.getPoint(), 9, awardSpecialTime.getId(), ActivityTypeFiveRelationEnum.SPECIAL_TIME);
            }

        };
    }

}
