package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ThreeMonthExcellentFacade;
import com.azp.core.sys.domain.ThreeMonthExcellentTO;
import com.azp.core.sys.model.ThreeMonthExcellent;
import com.azp.core.sys.model.ThreeMonthExcellentDomain;
import com.azp.core.sys.model.ThreeMonthExcellentFilterMapper;
import com.azp.core.sys.service.ThreeMonthExcellentService;
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
@RequestMapping("facade/sys/three/month/excellent")
public class ThreeMonthExcellentFacadeImpl implements ThreeMonthExcellentFacade {
  @Autowired
  private ThreeMonthExcellentService threeMonthExcellentService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ThreeMonthExcellentTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "threeMonth", required = false) String threeMonth,
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
    ThreeMonthExcellentFilterMapper mapper = new ThreeMonthExcellentFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.threeMonth = threeMonth;
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
    List<ThreeMonthExcellentTO> toList = new ArrayList<>();
    threeMonthExcellentService.getListByFilter(mapper).forEach(entity -> toList.add(ThreeMonthExcellentDomain.convert(entity, new ThreeMonthExcellentTO())));
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
  public List<ThreeMonthExcellentTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "threeMonth", required = false) String threeMonth,
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
    ThreeMonthExcellentFilterMapper mapper = new ThreeMonthExcellentFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.threeMonth = threeMonth;
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
    List<ThreeMonthExcellentTO> toList = new ArrayList<>();
    threeMonthExcellentService.getListByFilter(mapper).forEach(entity -> toList.add(ThreeMonthExcellentDomain.convert(entity, new ThreeMonthExcellentTO())));
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
  public ThreeMonthExcellentTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ThreeMonthExcellent entity = threeMonthExcellentService.getByPK(id);
    return entity != null ? ThreeMonthExcellentDomain.convert(entity, new ThreeMonthExcellentTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ThreeMonthExcellentTO entityTO) {
    threeMonthExcellentService.post(ThreeMonthExcellentDomain.convert(entityTO, new ThreeMonthExcellent()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ThreeMonthExcellentTO entityTO) {
    threeMonthExcellentService.update(ThreeMonthExcellentDomain.convert(entityTO, new ThreeMonthExcellent()));
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
    threeMonthExcellentService.delete(id);
  }
}
