package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContextHolder;
import com.azp.core.sys.model.ActivityTypeFiveRelationEnum;
import com.azp.core.sys.model.AwardSnowHeat;
import com.azp.core.sys.model.AwardSnowHeatFilterMapper;
import com.azp.core.sys.service.AwardSnowHeatService;
import com.azp.core.sys.service.ScanExcelToolService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:27
 */

@TaskScheduleComponent
public class AwardSnowHeatScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    AwardSnowHeatService awardSnowHeatService;
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

            AwardSnowHeatFilterMapper awardSnowHeatFilterMapper = new AwardSnowHeatFilterMapper();
            awardSnowHeatFilterMapper.gmtCreateFrom = lastCurrent;
            awardSnowHeatFilterMapper.gmtCreateTo = current;
            for (AwardSnowHeat awardSnowHeat:awardSnowHeatService.getListByFilter(awardSnowHeatFilterMapper)) {
                scanExcelToolService.postPointAndNotify(awardSnowHeat.getUserCode(),awardSnowHeat.getPoint(),8,awardSnowHeat.getId(),ActivityTypeFiveRelationEnum.SNOW_HEAT);
            }

        };
    }

}
