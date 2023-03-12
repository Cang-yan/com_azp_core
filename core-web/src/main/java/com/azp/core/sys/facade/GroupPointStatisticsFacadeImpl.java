package com.azp.core.sys.facade;

import com.azp.core.sys.domain.GroupPointStatisticsFacade;
import com.azp.core.sys.domain.GroupPointStatisticsTO;
import com.azp.core.sys.model.GroupPointStatistics;
import com.azp.core.sys.model.GroupPointStatisticsDomain;
import com.azp.core.sys.model.GroupPointStatisticsFilterMapper;
import com.azp.core.sys.service.GroupPointStatisticsService;
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
@RequestMapping("facade/sys/group/point/statistics")
public class GroupPointStatisticsFacadeImpl implements GroupPointStatisticsFacade {
  @Autowired
  private GroupPointStatisticsService groupPointStatisticsService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<GroupPointStatisticsTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "groupId", required = false) String groupId,
      @RequestParam(value = "dateFrom", required = false) Long dateFrom,
      @RequestParam(value = "dateTo", required = false) Long dateTo,
      @RequestParam(value = "status", required = false) Integer status,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    GroupPointStatisticsFilterMapper mapper = new GroupPointStatisticsFilterMapper();
    mapper.id = id;
    mapper.groupId = groupId;
    mapper.dateFrom = dateFrom;
    mapper.dateTo = dateTo;
    mapper.status = status;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<GroupPointStatisticsTO> toList = new ArrayList<>();
    groupPointStatisticsService.getListByFilter(mapper).forEach(entity -> toList.add(GroupPointStatisticsDomain.convert(entity, new GroupPointStatisticsTO())));
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
  public List<GroupPointStatisticsTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "groupId", required = false) String groupId,
      @RequestParam(value = "dateFrom", required = false) Long dateFrom,
      @RequestParam(value = "dateTo", required = false) Long dateTo,
      @RequestParam(value = "status", required = false) Integer status,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    GroupPointStatisticsFilterMapper mapper = new GroupPointStatisticsFilterMapper();
    mapper.id = id;
    mapper.groupId = groupId;
    mapper.dateFrom = dateFrom;
    mapper.dateTo = dateTo;
    mapper.status = status;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<GroupPointStatisticsTO> toList = new ArrayList<>();
    groupPointStatisticsService.getListByFilter(mapper).forEach(entity -> toList.add(GroupPointStatisticsDomain.convert(entity, new GroupPointStatisticsTO())));
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
  public GroupPointStatisticsTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    GroupPointStatistics entity = groupPointStatisticsService.getByPK(id);
    return entity != null ? GroupPointStatisticsDomain.convert(entity, new GroupPointStatisticsTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody GroupPointStatisticsTO entityTO) {
    groupPointStatisticsService.post(GroupPointStatisticsDomain.convert(entityTO, new GroupPointStatistics()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody GroupPointStatisticsTO entityTO) {
    groupPointStatisticsService.update(GroupPointStatisticsDomain.convert(entityTO, new GroupPointStatistics()));
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
    groupPointStatisticsService.delete(id);
  }
}
