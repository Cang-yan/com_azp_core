package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import com.azp.core.sys.model.ActivityTypeFiveRelationEnum;
import com.azp.core.sys.model.ThreeMonthExcellent;
import com.azp.core.sys.model.ThreeMonthExcellentFilterMapper;
import com.azp.core.sys.service.ScanExcelToolService;
import com.azp.core.sys.service.ThreeMonthExcellentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:13
 */
@TaskScheduleComponent
public class ThreeMonthExcellentScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    ThreeMonthExcellentService threeMonthExcellentService;
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

            ThreeMonthExcellentFilterMapper threeMonthExcellentFilterMapper = new ThreeMonthExcellentFilterMapper();
            threeMonthExcellentFilterMapper.gmtCreateFrom = lastCurrent;
            threeMonthExcellentFilterMapper.gmtCreateTo = current;
            for (ThreeMonthExcellent threeMonthExcellent:threeMonthExcellentService.getListByFilter(threeMonthExcellentFilterMapper)) {
                scanExcelToolService.postPointAndNotify(threeMonthExcellent.getUserCode(),threeMonthExcellent.getPoint(),9,threeMonthExcellent.getId(),ActivityTypeFiveRelationEnum.QUARTER);

            }
        };
    }
}
