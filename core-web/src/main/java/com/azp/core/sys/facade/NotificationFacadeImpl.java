package com.azp.core.sys.facade;

import com.azp.core.sys.domain.NotificationFacade;
import com.azp.core.sys.domain.NotificationTO;
import com.azp.core.sys.model.Notification;
import com.azp.core.sys.model.NotificationDomain;
import com.azp.core.sys.model.NotificationFilterMapper;
import com.azp.core.sys.service.NotificationService;
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
@RequestMapping("facade/sys/notification")
public class NotificationFacadeImpl implements NotificationFacade {
  @Autowired
  private NotificationService notificationService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<NotificationTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "templateId", required = false) String templateId,
      @RequestParam(value = "templateIdIn", required = false) ArrayList<String> templateIdIn,
      @RequestParam(value = "relationId", required = false) String relationId,
      @RequestParam(value = "relationIdIn", required = false) ArrayList<String> relationIdIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    NotificationFilterMapper mapper = new NotificationFilterMapper();
    mapper.id = id;
    mapper.title = title;
    mapper.typeIn = typeIn;
    mapper.templateId = templateId;
    mapper.templateIdIn = templateIdIn;
    mapper.relationId = relationId;
    mapper.relationIdIn = relationIdIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<NotificationTO> toList = new ArrayList<>();
    notificationService.getListByFilter(mapper).forEach(entity -> toList.add(NotificationDomain.convert(entity, new NotificationTO())));
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
  public List<NotificationTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "templateId", required = false) String templateId,
      @RequestParam(value = "templateIdIn", required = false) ArrayList<String> templateIdIn,
      @RequestParam(value = "relationId", required = false) String relationId,
      @RequestParam(value = "relationIdIn", required = false) ArrayList<String> relationIdIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    NotificationFilterMapper mapper = new NotificationFilterMapper();
    mapper.id = id;
    mapper.title = title;
    mapper.typeIn = typeIn;
    mapper.templateId = templateId;
    mapper.templateIdIn = templateIdIn;
    mapper.relationId = relationId;
    mapper.relationIdIn = relationIdIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<NotificationTO> toList = new ArrayList<>();
    notificationService.getListByFilter(mapper).forEach(entity -> toList.add(NotificationDomain.convert(entity, new NotificationTO())));
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
  public NotificationTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    Notification entity = notificationService.getByPK(id);
    return entity != null ? NotificationDomain.convert(entity, new NotificationTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody NotificationTO entityTO) {
    notificationService.post(NotificationDomain.convert(entityTO, new Notification()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody NotificationTO entityTO) {
    notificationService.update(NotificationDomain.convert(entityTO, new Notification()));
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
    notificationService.delete(id);
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
  public List<NotificationTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<NotificationTO> toList = new ArrayList<>();
    notificationService.getListByRelatedId(idList).forEach(entity -> toList.add(NotificationDomain.convert(entity, new NotificationTO())));
    return toList;
  }
}
