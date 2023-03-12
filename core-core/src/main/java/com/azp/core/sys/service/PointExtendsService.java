package com.azp.core.sys.service;

import com.azp.core.sys.model.Point;
import com.azp.core.sys.model.PointFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PointExtendsService {
    @Autowired
    private PointService pointService;

    public Integer getUserTypeTotalPoint(String userId, Integer type)
    {
        PointFilterMapper pointFilterMapper = new PointFilterMapper();
        pointFilterMapper.userId = userId;
        pointFilterMapper.type = type;
        List<Point> pointList = pointService.getListByFilter(pointFilterMapper);
        int totalPoint = 0;
        for(Point point : pointList)
        {
            totalPoint += point.getPointNumber();
        }
        return totalPoint;
    }

    public List<Point> getUserTotalPointWithTime(String userId, Date beginDate, Date endDate)
    {
        PointFilterMapper pointFilterMapper = new PointFilterMapper();
        pointFilterMapper.userId = userId;
        pointFilterMapper.gmtCreateFrom = beginDate.getTime();
        pointFilterMapper.gmtCreateTo = endDate.getTime();
        return pointService.getListByFilter(pointFilterMapper);
    }
}
