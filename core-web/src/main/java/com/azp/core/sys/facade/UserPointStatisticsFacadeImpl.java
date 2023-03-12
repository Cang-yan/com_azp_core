package com.azp.core.sys.facade;

import com.azp.core.sys.domain.UserPointStatisticsFacade;
import com.azp.core.sys.domain.UserPointStatisticsTO;
import com.azp.core.sys.model.UserPointStatistics;
import com.azp.core.sys.model.UserPointStatisticsDomain;
import com.azp.core.sys.model.UserPointStatisticsFilterMapper;
import com.azp.core.sys.service.UserPointStatisticsService;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@RestController
@RequestMapping("facade/sys/user/point/statistics")
public class UserPointStatisticsFacadeImpl implements UserPointStatisticsFacade {
  @Autowired
  private UserPointStatisticsService userPointStatisticsService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<UserPointStatisticsTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "numberFrom", required = false) Integer numberFrom,
      @RequestParam(value = "numberTo", required = false) Integer numberTo,
      @RequestParam(value = "dateFrom", required = false) Long dateFrom,
      @RequestParam(value = "dateTo", required = false) Long dateTo,
      @RequestParam(value = "departmentName", required = false) String departmentName,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "countFrom", required = false) Integer countFrom,
      @RequestParam(value = "countTo", required = false) Integer countTo,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    UserPointStatisticsFilterMapper mapper = new UserPointStatisticsFilterMapper();
    mapper.id = id;
    mapper.numberFrom = numberFrom;
    mapper.numberTo = numberTo;
    mapper.dateFrom = dateFrom;
    mapper.dateTo = dateTo;
    mapper.departmentName = departmentName;
    mapper.userId = userId;
    mapper.countFrom = countFrom;
    mapper.countTo = countTo;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<UserPointStatisticsTO> toList = new ArrayList<>();
    userPointStatisticsService.getListByFilter(mapper).forEach(entity -> toList.add(UserPointStatisticsDomain.convert(entity, new UserPointStatisticsTO())));
    return toList;
  }

  @RequestMapping(
      value = "filter/detail",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<UserPointStatisticsTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "numberFrom", required = false) Integer numberFrom,
      @RequestParam(value = "numberTo", required = false) Integer numberTo,
      @RequestParam(value = "dateFrom", required = false) Long dateFrom,
      @RequestParam(value = "dateTo", required = false) Long dateTo,
      @RequestParam(value = "departmentName", required = false) String departmentName,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "countFrom", required = false) Integer countFrom,
      @RequestParam(value = "countTo", required = false) Integer countTo,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    UserPointStatisticsFilterMapper mapper = new UserPointStatisticsFilterMapper();
    mapper.id = id;
    mapper.numberFrom = numberFrom;
    mapper.numberTo = numberTo;
    mapper.dateFrom = dateFrom;
    mapper.dateTo = dateTo;
    mapper.departmentName = departmentName;
    mapper.userId = userId;
    mapper.countFrom = countFrom;
    mapper.countTo = countTo;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<UserPointStatisticsTO> toList = new ArrayList<>();
    userPointStatisticsService.getListByFilter(mapper).forEach(entity -> toList.add(UserPointStatisticsDomain.convert(entity, new UserPointStatisticsTO())));
    return toList;
  }

  @RequestMapping(
      value = "single",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public UserPointStatisticsTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    UserPointStatistics entity = userPointStatisticsService.getByPK(id);
    return entity != null ? UserPointStatisticsDomain.convert(entity, new UserPointStatisticsTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody UserPointStatisticsTO entityTO) {
    userPointStatisticsService.post(UserPointStatisticsDomain.convert(entityTO, new UserPointStatistics()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody UserPointStatisticsTO entityTO) {
    userPointStatisticsService.update(UserPointStatisticsDomain.convert(entityTO, new UserPointStatistics()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.DELETE
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void delete(@RequestParam(value = "id", required = true) String id) {
    userPointStatisticsService.delete(id);
  }
}
