package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityDepartmentSelectFacade;
import com.azp.core.sys.domain.ActivityDepartmentSelectTO;
import com.azp.core.sys.model.ActivityDepartmentSelect;
import com.azp.core.sys.model.ActivityDepartmentSelectDomain;
import com.azp.core.sys.model.ActivityDepartmentSelectFilterMapper;
import com.azp.core.sys.service.ActivityDepartmentSelectService;
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
@RequestMapping("facade/sys/activity/department/select")
public class ActivityDepartmentSelectFacadeImpl implements ActivityDepartmentSelectFacade {
  @Autowired
  private ActivityDepartmentSelectService activityDepartmentSelectService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityDepartmentSelectTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "relationId", required = false) String relationId,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityDepartmentSelectFilterMapper mapper = new ActivityDepartmentSelectFilterMapper();
    mapper.id = id;
    mapper.relationId = relationId;
    mapper.departmentId = departmentId;
    mapper.typeIn = typeIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityDepartmentSelectTO> toList = new ArrayList<>();
    activityDepartmentSelectService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityDepartmentSelectDomain.convert(entity, new ActivityDepartmentSelectTO())));
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
  public List<ActivityDepartmentSelectTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "relationId", required = false) String relationId,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityDepartmentSelectFilterMapper mapper = new ActivityDepartmentSelectFilterMapper();
    mapper.id = id;
    mapper.relationId = relationId;
    mapper.departmentId = departmentId;
    mapper.typeIn = typeIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityDepartmentSelectTO> toList = new ArrayList<>();
    activityDepartmentSelectService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityDepartmentSelectDomain.convert(entity, new ActivityDepartmentSelectTO())));
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
  public ActivityDepartmentSelectTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityDepartmentSelect entity = activityDepartmentSelectService.getByPK(id);
    return entity != null ? ActivityDepartmentSelectDomain.convert(entity, new ActivityDepartmentSelectTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityDepartmentSelectTO entityTO) {
    activityDepartmentSelectService.post(ActivityDepartmentSelectDomain.convert(entityTO, new ActivityDepartmentSelect()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityDepartmentSelectTO entityTO) {
    activityDepartmentSelectService.update(ActivityDepartmentSelectDomain.convert(entityTO, new ActivityDepartmentSelect()));
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
    activityDepartmentSelectService.delete(id);
  }
}
