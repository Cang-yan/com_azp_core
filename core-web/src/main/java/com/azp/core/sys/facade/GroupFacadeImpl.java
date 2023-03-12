package com.azp.core.sys.facade;

import com.azp.core.sys.domain.GroupFacade;
import com.azp.core.sys.domain.GroupTO;
import com.azp.core.sys.model.Group;
import com.azp.core.sys.model.GroupDomain;
import com.azp.core.sys.model.GroupFilterMapper;
import com.azp.core.sys.service.GroupService;
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
@RequestMapping("facade/sys/group")
public class GroupFacadeImpl implements GroupFacade {
  @Autowired
  private GroupService groupService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<GroupTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "groupName", required = false) String groupName,
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    GroupFilterMapper mapper = new GroupFilterMapper();
    mapper.id = id;
    mapper.groupName = groupName;
    mapper.code = code;
    mapper.departmentId = departmentId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<GroupTO> toList = new ArrayList<>();
    groupService.getListByFilter(mapper).forEach(entity -> toList.add(GroupDomain.convert(entity, new GroupTO())));
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
  public List<GroupTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "groupName", required = false) String groupName,
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    GroupFilterMapper mapper = new GroupFilterMapper();
    mapper.id = id;
    mapper.groupName = groupName;
    mapper.code = code;
    mapper.departmentId = departmentId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<GroupTO> toList = new ArrayList<>();
    groupService.getListByFilter(mapper).forEach(entity -> toList.add(GroupDomain.convert(entity, new GroupTO())));
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
  public GroupTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    Group entity = groupService.getByPK(id);
    return entity != null ? GroupDomain.convert(entity, new GroupTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody GroupTO entityTO) {
    groupService.post(GroupDomain.convert(entityTO, new Group()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody GroupTO entityTO) {
    groupService.update(GroupDomain.convert(entityTO, new Group()));
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
    groupService.delete(id);
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
  public List<GroupTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<GroupTO> toList = new ArrayList<>();
    groupService.getListByRelatedId(idList).forEach(entity -> toList.add(GroupDomain.convert(entity, new GroupTO())));
    return toList;
  }

  @RequestMapping(
      value = "filter/relation/DepartmentId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<GroupTO> getListByRelatedDepartmentId(@RequestBody ArrayList<String> departmentIdList) {
    List<GroupTO> toList = new ArrayList<>();
    groupService.getListByRelatedDepartmentId(departmentIdList).forEach(entity -> toList.add(GroupDomain.convert(entity, new GroupTO())));
    return toList;
  }
}
