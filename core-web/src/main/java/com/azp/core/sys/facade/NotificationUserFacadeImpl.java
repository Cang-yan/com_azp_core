package com.azp.core.sys.facade;

import com.azp.core.sys.domain.NotificationUserFacade;
import com.azp.core.sys.domain.NotificationUserTO;
import com.azp.core.sys.model.NotificationUser;
import com.azp.core.sys.model.NotificationUserDomain;
import com.azp.core.sys.model.NotificationUserFilterMapper;
import com.azp.core.sys.service.NotificationUserService;
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
@RequestMapping("facade/sys/notification/user")
public class NotificationUserFacadeImpl implements NotificationUserFacade {
  @Autowired
  private NotificationUserService notificationUserService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<NotificationUserTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "notificationId", required = false) String notificationId,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "senderId", required = false) String senderId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    NotificationUserFilterMapper mapper = new NotificationUserFilterMapper();
    mapper.id = id;
    mapper.notificationId = notificationId;
    mapper.typeIn = typeIn;
    mapper.userId = userId;
    mapper.statusIn = statusIn;
    mapper.senderId = senderId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<NotificationUserTO> toList = new ArrayList<>();
    notificationUserService.getListByFilter(mapper).forEach(entity -> toList.add(NotificationUserDomain.convert(entity, new NotificationUserTO())));
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
  public List<NotificationUserTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "notificationId", required = false) String notificationId,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "senderId", required = false) String senderId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    NotificationUserFilterMapper mapper = new NotificationUserFilterMapper();
    mapper.id = id;
    mapper.notificationId = notificationId;
    mapper.typeIn = typeIn;
    mapper.userId = userId;
    mapper.statusIn = statusIn;
    mapper.senderId = senderId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<NotificationUserTO> toList = new ArrayList<>();
    notificationUserService.getListByFilter(mapper).forEach(entity -> toList.add(NotificationUserDomain.convert(entity, new NotificationUserTO())));
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
  public NotificationUserTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    NotificationUser entity = notificationUserService.getByPK(id);
    return entity != null ? NotificationUserDomain.convert(entity, new NotificationUserTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody NotificationUserTO entityTO) {
    notificationUserService.post(NotificationUserDomain.convert(entityTO, new NotificationUser()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody NotificationUserTO entityTO) {
    notificationUserService.update(NotificationUserDomain.convert(entityTO, new NotificationUser()));
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
    notificationUserService.delete(id);
  }
}
