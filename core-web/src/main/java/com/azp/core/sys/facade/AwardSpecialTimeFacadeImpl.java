package com.azp.core.sys.facade;

import com.azp.core.sys.domain.AwardSpecialTimeFacade;
import com.azp.core.sys.domain.AwardSpecialTimeTO;
import com.azp.core.sys.model.AwardSpecialTime;
import com.azp.core.sys.model.AwardSpecialTimeDomain;
import com.azp.core.sys.model.AwardSpecialTimeFilterMapper;
import com.azp.core.sys.service.AwardSpecialTimeService;
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
@RequestMapping("facade/sys/award/special/time")
public class AwardSpecialTimeFacadeImpl implements AwardSpecialTimeFacade {
  @Autowired
  private AwardSpecialTimeService awardSpecialTimeService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<AwardSpecialTimeTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "monthIn", required = false) ArrayList<String> monthIn,
      @RequestParam(value = "department", required = false) String department,
      @RequestParam(value = "group", required = false) String group,
      @RequestParam(value = "userCode", required = false) String userCode,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "awardName", required = false) String awardName,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    AwardSpecialTimeFilterMapper mapper = new AwardSpecialTimeFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.monthIn = monthIn;
    mapper.department = department;
    mapper.group = group;
    mapper.userCode = userCode;
    mapper.type = type;
    mapper.awardName = awardName;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<AwardSpecialTimeTO> toList = new ArrayList<>();
    awardSpecialTimeService.getListByFilter(mapper).forEach(entity -> toList.add(AwardSpecialTimeDomain.convert(entity, new AwardSpecialTimeTO())));
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
  public List<AwardSpecialTimeTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "monthIn", required = false) ArrayList<String> monthIn,
      @RequestParam(value = "department", required = false) String department,
      @RequestParam(value = "group", required = false) String group,
      @RequestParam(value = "userCode", required = false) String userCode,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "awardName", required = false) String awardName,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    AwardSpecialTimeFilterMapper mapper = new AwardSpecialTimeFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.monthIn = monthIn;
    mapper.department = department;
    mapper.group = group;
    mapper.userCode = userCode;
    mapper.type = type;
    mapper.awardName = awardName;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<AwardSpecialTimeTO> toList = new ArrayList<>();
    awardSpecialTimeService.getListByFilter(mapper).forEach(entity -> toList.add(AwardSpecialTimeDomain.convert(entity, new AwardSpecialTimeTO())));
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
  public AwardSpecialTimeTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    AwardSpecialTime entity = awardSpecialTimeService.getByPK(id);
    return entity != null ? AwardSpecialTimeDomain.convert(entity, new AwardSpecialTimeTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody AwardSpecialTimeTO entityTO) {
    awardSpecialTimeService.post(AwardSpecialTimeDomain.convert(entityTO, new AwardSpecialTime()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody AwardSpecialTimeTO entityTO) {
    awardSpecialTimeService.update(AwardSpecialTimeDomain.convert(entityTO, new AwardSpecialTime()));
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
    awardSpecialTimeService.delete(id);
  }
}
