package com.azp.core.sys.facade;

import com.azp.core.sys.domain.LoginRecordFacade;
import com.azp.core.sys.domain.LoginRecordTO;
import com.azp.core.sys.model.LoginRecord;
import com.azp.core.sys.model.LoginRecordDomain;
import com.azp.core.sys.model.LoginRecordFilterMapper;
import com.azp.core.sys.service.LoginRecordService;
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
@RequestMapping("facade/sys/login/record")
public class LoginRecordFacadeImpl implements LoginRecordFacade {
  @Autowired
  private LoginRecordService loginRecordService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<LoginRecordTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    LoginRecordFilterMapper mapper = new LoginRecordFilterMapper();
    mapper.id = id;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<LoginRecordTO> toList = new ArrayList<>();
    loginRecordService.getListByFilter(mapper).forEach(entity -> toList.add(LoginRecordDomain.convert(entity, new LoginRecordTO())));
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
  public List<LoginRecordTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    LoginRecordFilterMapper mapper = new LoginRecordFilterMapper();
    mapper.id = id;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<LoginRecordTO> toList = new ArrayList<>();
    loginRecordService.getListByFilter(mapper).forEach(entity -> toList.add(LoginRecordDomain.convert(entity, new LoginRecordTO())));
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
  public LoginRecordTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    LoginRecord entity = loginRecordService.getByPK(id);
    return entity != null ? LoginRecordDomain.convert(entity, new LoginRecordTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody LoginRecordTO entityTO) {
    loginRecordService.post(LoginRecordDomain.convert(entityTO, new LoginRecord()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody LoginRecordTO entityTO) {
    loginRecordService.update(LoginRecordDomain.convert(entityTO, new LoginRecord()));
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
    loginRecordService.delete(id);
  }
}
