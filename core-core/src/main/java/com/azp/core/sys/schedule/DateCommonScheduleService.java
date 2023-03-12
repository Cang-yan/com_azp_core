package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import com.azp.core.sys.service.ActivityTypeFourPeriodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/1/22 8:48 下午
 **/
@TaskScheduleComponent
public class DateCommonScheduleService {

    public static Date start;

    private static String startString;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${activity.four.start}")
    public void setStartString(String startString) throws ParseException {
        DateCommonScheduleService.startString = startString;
        start = sdf.parse(startString);
    }

    @Task(cron = "0 0 0 1 1/3 ?")
    public Runnable changeStart() {
        return () -> {
            start = new Date();
        };
    }

}
