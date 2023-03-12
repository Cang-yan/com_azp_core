package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import com.azp.core.sys.model.ActivityTypeFiveRelationEnum;
import com.azp.core.sys.model.AwardSkillExcellence;
import com.azp.core.sys.model.AwardSkillExcellenceFilterMapper;
import com.azp.core.sys.service.AwardSkillExcellenceService;
import com.azp.core.sys.service.ScanExcelToolService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/10 9:24
 */
@TaskScheduleComponent
public class AwardSkillExcellenceScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    AwardSkillExcellenceService awardSkillExcellenceService;
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

            AwardSkillExcellenceFilterMapper awardSkillExcellenceFilterMapper = new AwardSkillExcellenceFilterMapper();
            awardSkillExcellenceFilterMapper.gmtCreateFrom = lastCurrent;
            awardSkillExcellenceFilterMapper.gmtCreateTo = current;
            for (AwardSkillExcellence awardSkillExcellence:awardSkillExcellenceService.getListByFilter(awardSkillExcellenceFilterMapper)) {
                scanExcelToolService.postPointAndNotify(awardSkillExcellence.getUserCode(),awardSkillExcellence.getPoint(),9,awardSkillExcellence.getId(),ActivityTypeFiveRelationEnum.SKILL_EXCELLENCE);

            }

        };
    }

}
