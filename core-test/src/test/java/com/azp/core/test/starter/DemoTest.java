package com.azp.core.test.starter;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/12/1 2:04 下午
 **/
public class DemoTest {

    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse("2022-11-01 00:00:00");
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(parse);
        startCalendar.add(Calendar.MONTH, 2);
        startCalendar.set(Calendar.DATE, 1);
        startCalendar.roll(Calendar.DATE, -1);
        startCalendar.set(Calendar.HOUR, 23);
        startCalendar.set(Calendar.MINUTE, 59);
        startCalendar.set(Calendar.SECOND, 59);
        System.out.println(sdf.format(startCalendar.getTime()));
    }

    @Test
    public void test2() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) / 3 * 3, 1, 0, 0, 0);
        System.out.println(new Date(calendar1.getTimeInMillis() / 1000 * 1000).getTime());
        calendar1.add(Calendar.MONTH, 3);
        calendar1.add(Calendar.SECOND, -1);
        System.out.println(new Date(calendar1.getTimeInMillis() / 1000 * 1000).getTime());
    }


}
