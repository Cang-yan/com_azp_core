package com.azp.core.sys.facade;

import com.azp.core.sys.domain.AwardGoodEyeFacade;
import com.azp.core.sys.domain.AwardGoodEyeTO;
import com.azp.core.sys.model.AwardGoodEye;
import com.azp.core.sys.model.AwardGoodEyeDomain;
import com.azp.core.sys.model.AwardGoodEyeFilterMapper;
import com.azp.core.sys.service.AwardGoodEyeService;
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
@RequestMapping("facade/sys/award/good/eye")
public class AwardGoodEyeFacadeImpl implements AwardGoodEyeFacade {
  @Autowired
  private AwardGoodEyeService awardGoodEyeService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<AwardGoodEyeTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "quarterIn", required = false) ArrayList<String> quarterIn,
      @RequestParam(value = "department", required = false) String department,
      @RequestParam(value = "group", required = false) String group,
      @RequestParam(value = "userCode", required = false) String userCode,
      @RequestParam(value = "awardName", required = false) String awardName,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    AwardGoodEyeFilterMapper mapper = new AwardGoodEyeFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.quarterIn = quarterIn;
    mapper.department = department;
    mapper.group = group;
    mapper.userCode = userCode;
    mapper.awardName = awardName;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<AwardGoodEyeTO> toList = new ArrayList<>();
    awardGoodEyeService.getListByFilter(mapper).forEach(entity -> toList.add(AwardGoodEyeDomain.convert(entity, new AwardGoodEyeTO())));
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
  public List<AwardGoodEyeTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "year", required = false) String year,
      @RequestParam(value = "quarterIn", required = false) ArrayList<String> quarterIn,
      @RequestParam(value = "department", required = false) String department,
      @RequestParam(value = "group", required = false) String group,
      @RequestParam(value = "userCode", required = false) String userCode,
      @RequestParam(value = "awardName", required = false) String awardName,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    AwardGoodEyeFilterMapper mapper = new AwardGoodEyeFilterMapper();
    mapper.id = id;
    mapper.year = year;
    mapper.quarterIn = quarterIn;
    mapper.department = department;
    mapper.group = group;
    mapper.userCode = userCode;
    mapper.awardName = awardName;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<AwardGoodEyeTO> toList = new ArrayList<>();
    awardGoodEyeService.getListByFilter(mapper).forEach(entity -> toList.add(AwardGoodEyeDomain.convert(entity, new AwardGoodEyeTO())));
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
  public AwardGoodEyeTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    AwardGoodEye entity = awardGoodEyeService.getByPK(id);
    return entity != null ? AwardGoodEyeDomain.convert(entity, new AwardGoodEyeTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody AwardGoodEyeTO entityTO) {
    awardGoodEyeService.post(AwardGoodEyeDomain.convert(entityTO, new AwardGoodEye()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody AwardGoodEyeTO entityTO) {
    awardGoodEyeService.update(AwardGoodEyeDomain.convert(entityTO, new AwardGoodEye()));
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
    awardGoodEyeService.delete(id);
  }
}
