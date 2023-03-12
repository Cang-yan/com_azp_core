package com.azp.core.sys.facade;

import com.azp.core.sys.domain.MonthExcellentFacade;
import com.azp.core.sys.domain.MonthExcellentTO;
import com.azp.core.sys.model.MonthExcellent;
import com.azp.core.sys.model.MonthExcellentDomain;
import com.azp.core.sys.model.MonthExcellentFilterMapper;
import com.azp.core.sys.service.MonthExcellentService;
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
@RequestMapping("facade/sys/month/excellent")
public class MonthExcellentFacadeImpl implements MonthExcellentFacade {
  @Autowired
  private MonthExcellentService monthExcellentService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<MonthExcellentTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "month", required = false) String month,
      @RequestParam(value = "department", required = false) String department,
      @RequestParam(value = "group", required = false) String group,
      @RequestParam(value = "userCode", required = false) String userCode,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    MonthExcellentFilterMapper mapper = new MonthExcellentFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.month = month;
    mapper.department = department;
    mapper.group = group;
    mapper.userCode = userCode;
    mapper.type = type;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<MonthExcellentTO> toList = new ArrayList<>();
    monthExcellentService.getListByFilter(mapper).forEach(entity -> toList.add(MonthExcellentDomain.convert(entity, new MonthExcellentTO())));
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
  public List<MonthExcellentTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "month", required = false) String month,
      @RequestParam(value = "department", required = false) String department,
      @RequestParam(value = "group", required = false) String group,
      @RequestParam(value = "userCode", required = false) String userCode,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    MonthExcellentFilterMapper mapper = new MonthExcellentFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.month = month;
    mapper.department = department;
    mapper.group = group;
    mapper.userCode = userCode;
    mapper.type = type;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<MonthExcellentTO> toList = new ArrayList<>();
    monthExcellentService.getListByFilter(mapper).forEach(entity -> toList.add(MonthExcellentDomain.convert(entity, new MonthExcellentTO())));
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
  public MonthExcellentTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    MonthExcellent entity = monthExcellentService.getByPK(id);
    return entity != null ? MonthExcellentDomain.convert(entity, new MonthExcellentTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody MonthExcellentTO entityTO) {
    monthExcellentService.post(MonthExcellentDomain.convert(entityTO, new MonthExcellent()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody MonthExcellentTO entityTO) {
    monthExcellentService.update(MonthExcellentDomain.convert(entityTO, new MonthExcellent()));
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
    monthExcellentService.delete(id);
  }
}
