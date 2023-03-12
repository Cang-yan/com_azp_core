package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContextHolder;
import com.azp.core.sys.model.ActivityTypeFiveRelationEnum;
import com.azp.core.sys.model.AwardRainAid;
import com.azp.core.sys.model.AwardRainAidFilterMapper;
import com.azp.core.sys.service.AwardRainAidService;
import com.azp.core.sys.service.ScanExcelToolService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:27
 */

@TaskScheduleComponent
public class AwardRainAidScheduleService {
    private static long lastCurrent = 0;
    private static long current;
    @Autowired
    AwardRainAidService awardRainAidService;
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

            AwardRainAidFilterMapper awardRainAidFilterMapper = new AwardRainAidFilterMapper();
            awardRainAidFilterMapper.gmtCreateFrom = lastCurrent;
            awardRainAidFilterMapper.gmtCreateTo = current;
            for (AwardRainAid awardRainAid : awardRainAidService.getListByFilter(awardRainAidFilterMapper)) {
                scanExcelToolService.postPointAndNotify(awardRainAid.getUserCode(),awardRainAid.getPoint(),8,awardRainAid.getId(),ActivityTypeFiveRelationEnum.RAIN_AID);
            }

        };
    }

}
