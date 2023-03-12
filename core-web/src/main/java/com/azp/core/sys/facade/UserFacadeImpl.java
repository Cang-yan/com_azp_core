package com.azp.core.sys.facade;

import com.azp.core.sys.domain.UserFacade;
import com.azp.core.sys.domain.UserTO;
import com.azp.core.sys.model.User;
import com.azp.core.sys.model.UserDomain;
import com.azp.core.sys.model.UserFilterMapper;
import com.azp.core.sys.service.UserService;
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
@RequestMapping("facade/sys/user")
public class UserFacadeImpl implements UserFacade {
  @Autowired
  private UserService userService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<UserTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "userCodeIn", required = false) ArrayList<String> userCodeIn,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "account", required = false) String account,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "groupId", required = false) String groupId,
      @RequestParam(value = "userInfoId", required = false) String userInfoId,
      @RequestParam(value = "status", required = false) Integer status,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    UserFilterMapper mapper = new UserFilterMapper();
    mapper.id = id;
    mapper.userCodeIn = userCodeIn;
    mapper.name = name;
    mapper.account = account;
    mapper.departmentId = departmentId;
    mapper.groupId = groupId;
    mapper.userInfoId = userInfoId;
    mapper.status = status;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<UserTO> toList = new ArrayList<>();
    userService.getListByFilter(mapper).forEach(entity -> toList.add(UserDomain.convert(entity, new UserTO())));
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
  public List<UserTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "userCodeIn", required = false) ArrayList<String> userCodeIn,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "account", required = false) String account,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "groupId", required = false) String groupId,
      @RequestParam(value = "userInfoId", required = false) String userInfoId,
      @RequestParam(value = "status", required = false) Integer status,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    UserFilterMapper mapper = new UserFilterMapper();
    mapper.id = id;
    mapper.userCodeIn = userCodeIn;
    mapper.name = name;
    mapper.account = account;
    mapper.departmentId = departmentId;
    mapper.groupId = groupId;
    mapper.userInfoId = userInfoId;
    mapper.status = status;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<UserTO> toList = new ArrayList<>();
    userService.getListByFilter(mapper).forEach(entity -> toList.add(UserDomain.convert(entity, new UserTO())));
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
  public UserTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    User entity = userService.getByPK(id);
    return entity != null ? UserDomain.convert(entity, new UserTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody UserTO entityTO) {
    userService.post(UserDomain.convert(entityTO, new User()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody UserTO entityTO) {
    userService.update(UserDomain.convert(entityTO, new User()));
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
    userService.delete(id);
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
  public List<UserTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<UserTO> toList = new ArrayList<>();
    userService.getListByRelatedId(idList).forEach(entity -> toList.add(UserDomain.convert(entity, new UserTO())));
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
  public List<UserTO> getListByRelatedDepartmentId(@RequestBody ArrayList<String> departmentIdList) {
    List<UserTO> toList = new ArrayList<>();
    userService.getListByRelatedDepartmentId(departmentIdList).forEach(entity -> toList.add(UserDomain.convert(entity, new UserTO())));
    return toList;
  }
}
