package com.azp.core.sys.schedule;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import com.azp.core.sys.model.*;
import com.azp.core.sys.service.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@TaskScheduleComponent
public class GroupPointStatisticsScheduleService {
    @Autowired
    private GroupPointStatisticsService groupPointStatisticsService;

    @Autowired
    private ActivityTypeFourPeriodsService periodsService;

    @Autowired
    private ActivityTypeFourService activityTypeFourService;

    @Autowired
    private ActivityTypeFourUserService activityTypeFourUserService;

    @Autowired
    private PointService pointService;


    @Task(cron = "0 * * * * ?")
    @Distributed
    public Runnable refresh() {
        return this::buildGroupPointStatistics;
    }

    /**
     * 实时计算组队排名
     *
     * @return
     */
    public void buildGroupPointStatistics() {
        ActivityTypeFourFilterMapper activityTypeFourFilterMapper = new ActivityTypeFourFilterMapper();
        activityTypeFourFilterMapper.statusIn = Collections.singletonList(3);
        List<ActivityTypeFour> groups = activityTypeFourService.getListByFilter(activityTypeFourFilterMapper);
        if (CollectionUtils.isEmpty(groups)) return;
        for (ActivityTypeFour group : groups) {
            // 组队成功日期
            // 查询所有队伍下的人
            ActivityTypeFourUserFilterMapper userFilter = new ActivityTypeFourUserFilterMapper();
            userFilter.activityTypeFourId = group.getId();
            userFilter.statusIn = Collections.singletonList(5);
            List<ActivityTypeFourUser> userList = activityTypeFourUserService.getListByFilter(userFilter);
            if (CollectionUtils.isEmpty(userList)) throw new StatusException("GROUP_ERROR");
            List<String> userIds = userList.stream().map(ActivityTypeFourUser::getUserId).collect(Collectors.toList());
            // 查询上次该组队统计的数据，可能为Null
            GroupPointStatisticsFilterMapper groupPointStatisticsFilterMapper = new GroupPointStatisticsFilterMapper();
            groupPointStatisticsFilterMapper.groupId = group.getId();
            GroupPointStatistics statistics = groupPointStatisticsService.getListByFilter(groupPointStatisticsFilterMapper).stream().findFirst().orElse(null);
            int groupPoint = 0;
            int groupCount = 0;
            for (String userId : userIds) {
                // 筛选组队成功之后&上次计算之后&活动结束之前获取的积分
                PointFilterMapper pointFilterMapper = new PointFilterMapper();
                pointFilterMapper.gmtCreateFrom = group.getGroupDate().getTime();
                ActivityTypeFourPeriodsFilterMapper fourPeriodsFilterMapper = new ActivityTypeFourPeriodsFilterMapper();
                fourPeriodsFilterMapper.periodsNumber = ActivityTypeFourExtendsService.getPeriodCount();
                pointFilterMapper.gmtCreateTo = periodsService.getListByFilter(fourPeriodsFilterMapper).stream().findFirst().get().getEndDate().getTime();
                pointFilterMapper.userId = userId;
                List<Point> pointList = pointService.getListByFilter(pointFilterMapper);
                if (pointList.isEmpty()) continue;
                groupPoint += pointList.stream().mapToInt(Point::getPointNumber).sum();
                groupCount += pointList.size();
            }

            // new
            if (statistics == null) {
                GroupPointStatistics groupPointStatistics = new GroupPointStatistics();
                groupPointStatistics.setDate(new Date());
                groupPointStatistics.setGroupId(group.getId());
                groupPointStatistics.setPointNum(groupPoint);
                groupPointStatistics.setCount(groupCount);
                groupPointStatisticsService.post(groupPointStatistics);
            } else {
                statistics.setPointNum(groupPoint);
                statistics.setCount(groupCount);
                statistics.setDate(new Date());
                groupPointStatisticsService.update(statistics);
            }
        }
    }

}
