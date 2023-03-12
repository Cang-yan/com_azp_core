package com.azp.core.sys.facade;

import com.azp.core.sys.domain.DepartmentFacade;
import com.azp.core.sys.domain.DepartmentTO;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentDomain;
import com.azp.core.sys.model.DepartmentFilterMapper;
import com.azp.core.sys.service.DepartmentService;
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
@RequestMapping("facade/sys/department")
public class DepartmentFacadeImpl implements DepartmentFacade {
  @Autowired
  private DepartmentService departmentService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<DepartmentTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    DepartmentFilterMapper mapper = new DepartmentFilterMapper();
    mapper.id = id;
    mapper.name = name;
    mapper.code = code;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<DepartmentTO> toList = new ArrayList<>();
    departmentService.getListByFilter(mapper).forEach(entity -> toList.add(DepartmentDomain.convert(entity, new DepartmentTO())));
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
  public List<DepartmentTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    DepartmentFilterMapper mapper = new DepartmentFilterMapper();
    mapper.id = id;
    mapper.name = name;
    mapper.code = code;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<DepartmentTO> toList = new ArrayList<>();
    departmentService.getListByFilter(mapper).forEach(entity -> toList.add(DepartmentDomain.convert(entity, new DepartmentTO())));
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
  public DepartmentTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    Department entity = departmentService.getByPK(id);
    return entity != null ? DepartmentDomain.convert(entity, new DepartmentTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody DepartmentTO entityTO) {
    departmentService.post(DepartmentDomain.convert(entityTO, new Department()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody DepartmentTO entityTO) {
    departmentService.update(DepartmentDomain.convert(entityTO, new Department()));
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
    departmentService.delete(id);
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
  public List<DepartmentTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<DepartmentTO> toList = new ArrayList<>();
    departmentService.getListByRelatedId(idList).forEach(entity -> toList.add(DepartmentDomain.convert(entity, new DepartmentTO())));
    return toList;
  }
}
