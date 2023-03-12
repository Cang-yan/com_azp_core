package com.azp.core.sys.service;

import com.azp.core.sys.model.ActivityTypeSix;
import com.azp.core.sys.model.ActivityTypeSixFilterMapper;
import com.azp.core.sys.model.ActivityTypeSixPostMapper;
import com.azp.core.sys.model.Point;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class ActivityTypeSixExtendsService {

    @Autowired
    private ActivityTypeSixService activityTypeSixService;

    @Autowired
    private PointService pointService;

    public Map<String, Object> postWithPointCalculate(ActivityTypeSixPostMapper activityTypeSix) {
        Assert.notNull(activityTypeSix.userId);
        int point;
        if (activityTypeSix.date == null) activityTypeSix.date = new Date().getTime();
        Date time = new Date(activityTypeSix.date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        // 每天签到一次，从今天0点到12点应该不存在签过到的情况
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Calendar calendarTodayEnd = Calendar.getInstance();
        calendarTodayEnd.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        ActivityTypeSixFilterMapper activityTypeSixFilterMapper = new ActivityTypeSixFilterMapper();
        activityTypeSixFilterMapper.dateFrom = calendarToday.getTimeInMillis();
        activityTypeSixFilterMapper.dateTo = calendarTodayEnd.getTimeInMillis() + 1000L;
        activityTypeSixFilterMapper.userId = activityTypeSix.userId;
        List<ActivityTypeSix> activityTypeSixes = activityTypeSixService.getListByFilter(activityTypeSixFilterMapper);
        if (!CollectionUtils.isEmpty(activityTypeSixes)) throw new StatusException("SIGN_FAILED");

        // 若为每月第一天
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth == 1) {
            point = 1;
            activityTypeSix.point = point;
            return activityTypeSixService.postMapping(activityTypeSix);
        }

        // 从昨天的0点到昨天12点是否签过到
        Calendar calendarYesterday = Calendar.getInstance();
        calendarYesterday.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1, 0, 0, 0);
        Calendar calendarYesterdayEnd = Calendar.getInstance();
        calendarYesterdayEnd.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1, 23, 59, 59);
        activityTypeSixFilterMapper = new ActivityTypeSixFilterMapper();
        activityTypeSixFilterMapper.dateFrom = calendarYesterday.getTimeInMillis();
        activityTypeSixFilterMapper.dateTo = calendarYesterdayEnd.getTimeInMillis() + 1000L;
        activityTypeSixFilterMapper.userId = activityTypeSix.userId;
        List<ActivityTypeSix> activityTypeSixesYesterday = activityTypeSixService.getListByFilter(activityTypeSixFilterMapper);

        // 昨天没打卡，重新开始
        if (CollectionUtils.isEmpty(activityTypeSixesYesterday)) {
            point = 1;
        } else {
            ActivityTypeSix yesterday = activityTypeSixesYesterday.get(0);
            Integer yesterdayPoint = yesterday.getPoint();
            point = yesterdayPoint == 7 ? 7 : yesterdayPoint + 1;
        }
        activityTypeSix.point = point;
        Point newPoint = new Point();
        newPoint.setPointNumber(point);
        newPoint.setType(1);
        newPoint.setGetTime(new Date());
        newPoint.setUserId(activityTypeSix.userId);
        pointService.post(newPoint);
        activityTypeSix.activitySubCategoryId = "7";
        return activityTypeSixService.postMapping(activityTypeSix);
    }

}
