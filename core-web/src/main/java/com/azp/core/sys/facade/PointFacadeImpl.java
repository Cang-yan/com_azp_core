package com.azp.core.sys.facade;

import com.azp.core.sys.domain.PointFacade;
import com.azp.core.sys.domain.PointTO;
import com.azp.core.sys.model.Point;
import com.azp.core.sys.model.PointDomain;
import com.azp.core.sys.model.PointFilterMapper;
import com.azp.core.sys.service.PointService;
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
@RequestMapping("facade/sys/point")
public class PointFacadeImpl implements PointFacade {
  @Autowired
  private PointService pointService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<PointTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "userIdIn", required = false) ArrayList<String> userIdIn,
      @RequestParam(value = "relationId", required = false) String relationId,
      @RequestParam(value = "relationIdIn", required = false) ArrayList<String> relationIdIn,
      @RequestParam(value = "getTimeFrom", required = false) Long getTimeFrom,
      @RequestParam(value = "getTimeTo", required = false) Long getTimeTo,
      @RequestParam(value = "templateId", required = false) String templateId,
      @RequestParam(value = "templateIdIn", required = false) ArrayList<String> templateIdIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    PointFilterMapper mapper = new PointFilterMapper();
    mapper.id = id;
    mapper.typeIn = typeIn;
    mapper.userId = userId;
    mapper.userIdIn = userIdIn;
    mapper.relationId = relationId;
    mapper.relationIdIn = relationIdIn;
    mapper.getTimeFrom = getTimeFrom;
    mapper.getTimeTo = getTimeTo;
    mapper.templateId = templateId;
    mapper.templateIdIn = templateIdIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<PointTO> toList = new ArrayList<>();
    pointService.getListByFilter(mapper).forEach(entity -> toList.add(PointDomain.convert(entity, new PointTO())));
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
  public List<PointTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "typeIn", required = false) ArrayList<Integer> typeIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "userIdIn", required = false) ArrayList<String> userIdIn,
      @RequestParam(value = "relationId", required = false) String relationId,
      @RequestParam(value = "relationIdIn", required = false) ArrayList<String> relationIdIn,
      @RequestParam(value = "getTimeFrom", required = false) Long getTimeFrom,
      @RequestParam(value = "getTimeTo", required = false) Long getTimeTo,
      @RequestParam(value = "templateId", required = false) String templateId,
      @RequestParam(value = "templateIdIn", required = false) ArrayList<String> templateIdIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    PointFilterMapper mapper = new PointFilterMapper();
    mapper.id = id;
    mapper.typeIn = typeIn;
    mapper.userId = userId;
    mapper.userIdIn = userIdIn;
    mapper.relationId = relationId;
    mapper.relationIdIn = relationIdIn;
    mapper.getTimeFrom = getTimeFrom;
    mapper.getTimeTo = getTimeTo;
    mapper.templateId = templateId;
    mapper.templateIdIn = templateIdIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<PointTO> toList = new ArrayList<>();
    pointService.getListByFilter(mapper).forEach(entity -> toList.add(PointDomain.convert(entity, new PointTO())));
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
  public PointTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    Point entity = pointService.getByPK(id);
    return entity != null ? PointDomain.convert(entity, new PointTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody PointTO entityTO) {
    pointService.post(PointDomain.convert(entityTO, new Point()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody PointTO entityTO) {
    pointService.update(PointDomain.convert(entityTO, new Point()));
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
    pointService.delete(id);
  }
}
