package com.azp.core.sys.facade;

import com.azp.core.sys.domain.DepartmentPointFacade;
import com.azp.core.sys.domain.DepartmentPointTO;
import com.azp.core.sys.model.DepartmentPoint;
import com.azp.core.sys.model.DepartmentPointDomain;
import com.azp.core.sys.model.DepartmentPointFilterMapper;
import com.azp.core.sys.service.DepartmentPointService;
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
@RequestMapping("facade/sys/department/point")
public class DepartmentPointFacadeImpl implements DepartmentPointFacade {
  @Autowired
  private DepartmentPointService departmentPointService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<DepartmentPointTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    DepartmentPointFilterMapper mapper = new DepartmentPointFilterMapper();
    mapper.id = id;
    mapper.departmentId = departmentId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<DepartmentPointTO> toList = new ArrayList<>();
    departmentPointService.getListByFilter(mapper).forEach(entity -> toList.add(DepartmentPointDomain.convert(entity, new DepartmentPointTO())));
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
  public List<DepartmentPointTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    DepartmentPointFilterMapper mapper = new DepartmentPointFilterMapper();
    mapper.id = id;
    mapper.departmentId = departmentId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<DepartmentPointTO> toList = new ArrayList<>();
    departmentPointService.getListByFilter(mapper).forEach(entity -> toList.add(DepartmentPointDomain.convert(entity, new DepartmentPointTO())));
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
  public DepartmentPointTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    DepartmentPoint entity = departmentPointService.getByPK(id);
    return entity != null ? DepartmentPointDomain.convert(entity, new DepartmentPointTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody DepartmentPointTO entityTO) {
    departmentPointService.post(DepartmentPointDomain.convert(entityTO, new DepartmentPoint()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody DepartmentPointTO entityTO) {
    departmentPointService.update(DepartmentPointDomain.convert(entityTO, new DepartmentPoint()));
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
    departmentPointService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/Id",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<DepartmentPointTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<DepartmentPointTO> toList = new ArrayList<>();
    departmentPointService.getListByRelatedId(idList).forEach(entity -> toList.add(DepartmentPointDomain.convert(entity, new DepartmentPointTO())));
    return toList;
  }
}
