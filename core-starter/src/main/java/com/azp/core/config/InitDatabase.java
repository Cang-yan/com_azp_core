package com.azp.core.config;

import com.azp.core.sys.model.ActivityTypeFourPeriods;
import com.azp.core.sys.model.ActivityTypeFourPeriodsFilterMapper;
import com.azp.core.sys.model.ActivityTypeFourPeriodsPostMapper;
import com.azp.core.sys.service.ActivityTypeFourExtendsService;
import com.azp.core.sys.service.ActivityTypeFourPeriodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class InitDatabase implements ApplicationRunner {
    @Autowired
    ActivityTypeFourPeriodsService activityTypeFourPeriodsService = new ActivityTypeFourPeriodsService();

    @Override
    public void run(ApplicationArguments args) {
        ActivityTypeFourPeriodsFilterMapper activityTypeFourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
        activityTypeFourPeriodsFilterMapper.orderBy = new ArrayList<>();
        activityTypeFourPeriodsFilterMapper.orderBy.add("periods_number desc");
        List<ActivityTypeFourPeriods> activityTypeFourPeriodsList = activityTypeFourPeriodsService.getListByFilter(activityTypeFourPeriodsFilterMapper);
        if (activityTypeFourPeriodsList.size() == 0) {
            ActivityTypeFourPeriodsPostMapper activityTypeFourPeriodsPostMapper = new ActivityTypeFourPeriodsPostMapper();
            activityTypeFourPeriodsPostMapper.periodsNumber = 1;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) / 3 * 3, 1, 0, 0, 0);
            activityTypeFourPeriodsPostMapper.beginDate = calendar1.getTimeInMillis() / 1000 * 1000;
            calendar1.add(Calendar.MONTH, 3);
            calendar1.add(Calendar.SECOND, -1);
            activityTypeFourPeriodsPostMapper.endDate = calendar1.getTimeInMillis() / 1000 * 1000;
            activityTypeFourPeriodsService.postMapping(activityTypeFourPeriodsPostMapper);
        } else {
            ActivityTypeFourExtendsService.setPeriodCount(activityTypeFourPeriodsList.get(0).getPeriodsNumber());
        }
    }
}
