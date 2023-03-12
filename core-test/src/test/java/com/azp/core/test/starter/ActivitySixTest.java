package com.azp.core.test.starter;

import com.horsecoder.common.error.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/12/1 2:04 下午
 **/
public class ActivitySixTest {

    @Test
    public void test1() {
        Assert.isTrue(true);
    }


    @Test
    public void test2() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long time1 = calendar.getTime().getTime();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 1);
        long time2 = calendar.getTime().getTime();
        long l = (time1 - time2) / 1000 / 60 / 60;
        Assert.isTrue(l == 24);
    }


}
