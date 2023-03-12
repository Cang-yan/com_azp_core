package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDAO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContext;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContextHolder;
import com.azp.core.sys.model.ActivityTypeFiveRelationEnum;
import com.azp.core.sys.model.AwardGoodEye;
import com.azp.core.sys.model.AwardGoodEyeFilterMapper;
import com.azp.core.sys.service.AwardGoodEyeService;
import com.azp.core.sys.service.ScanExcelToolService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:27
 */

@TaskScheduleComponent
public class AwardGoodEyeScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    AwardGoodEyeService awardGoodEyeService;
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
            AwardGoodEyeFilterMapper awardGoodEyeFilterMapper = new AwardGoodEyeFilterMapper();
            awardGoodEyeFilterMapper.gmtCreateFrom = lastCurrent;
            awardGoodEyeFilterMapper.gmtCreateTo = current;
            for (AwardGoodEye awardGoodEye:awardGoodEyeService.getListByFilter(awardGoodEyeFilterMapper)) {
                scanExcelToolService.postPointAndNotify(awardGoodEye.getUserCode(),awardGoodEye.getPoint(),8, awardGoodEye.getId(), ActivityTypeFiveRelationEnum.GOOD_EYE);
            }
        };
    }

}
