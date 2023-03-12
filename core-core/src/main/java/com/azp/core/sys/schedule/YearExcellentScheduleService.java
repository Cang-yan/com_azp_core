package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import com.azp.core.sys.model.ActivityTypeFiveRelationEnum;
import com.azp.core.sys.model.YearExcellent;
import com.azp.core.sys.model.YearExcellentFilterMapper;
import com.azp.core.sys.service.ScanExcelToolService;
import com.azp.core.sys.service.YearExcellentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:16
 */
@TaskScheduleComponent
public class YearExcellentScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    YearExcellentService yearExcellentService;
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

            YearExcellentFilterMapper yearExcellentFilterMapper = new YearExcellentFilterMapper();
            yearExcellentFilterMapper.gmtCreateFrom = lastCurrent;
            yearExcellentFilterMapper.gmtCreateTo = current;
            for (YearExcellent yearExcellent:yearExcellentService.getListByFilter(yearExcellentFilterMapper)) {
                scanExcelToolService.postPointAndNotify(yearExcellent.getUserCode(),yearExcellent.getPoint(),9,yearExcellent.getId(),ActivityTypeFiveRelationEnum.YEAR);
            }

        };
    }

}
