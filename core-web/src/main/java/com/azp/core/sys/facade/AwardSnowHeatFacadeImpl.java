package com.azp.core.sys.facade;

import com.azp.core.sys.domain.AwardSnowHeatFacade;
import com.azp.core.sys.domain.AwardSnowHeatTO;
import com.azp.core.sys.model.AwardSnowHeat;
import com.azp.core.sys.model.AwardSnowHeatDomain;
import com.azp.core.sys.model.AwardSnowHeatFilterMapper;
import com.azp.core.sys.service.AwardSnowHeatService;
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
@RequestMapping("facade/sys/award/snow/heat")
public class AwardSnowHeatFacadeImpl implements AwardSnowHeatFacade {
  @Autowired
  private AwardSnowHeatService awardSnowHeatService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<AwardSnowHeatTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "monthIn", required = false) ArrayList<String> monthIn,
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
    AwardSnowHeatFilterMapper mapper = new AwardSnowHeatFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.monthIn = monthIn;
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
    List<AwardSnowHeatTO> toList = new ArrayList<>();
    awardSnowHeatService.getListByFilter(mapper).forEach(entity -> toList.add(AwardSnowHeatDomain.convert(entity, new AwardSnowHeatTO())));
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
  public List<AwardSnowHeatTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "monthIn", required = false) ArrayList<String> monthIn,
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
    AwardSnowHeatFilterMapper mapper = new AwardSnowHeatFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.monthIn = monthIn;
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
    List<AwardSnowHeatTO> toList = new ArrayList<>();
    awardSnowHeatService.getListByFilter(mapper).forEach(entity -> toList.add(AwardSnowHeatDomain.convert(entity, new AwardSnowHeatTO())));
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
  public AwardSnowHeatTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    AwardSnowHeat entity = awardSnowHeatService.getByPK(id);
    return entity != null ? AwardSnowHeatDomain.convert(entity, new AwardSnowHeatTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody AwardSnowHeatTO entityTO) {
    awardSnowHeatService.post(AwardSnowHeatDomain.convert(entityTO, new AwardSnowHeat()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody AwardSnowHeatTO entityTO) {
    awardSnowHeatService.update(AwardSnowHeatDomain.convert(entityTO, new AwardSnowHeat()));
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
    awardSnowHeatService.delete(id);
  }
}
