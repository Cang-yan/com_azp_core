package com.azp.core.sys.facade;

import com.azp.core.sys.domain.PointExchangeFacade;
import com.azp.core.sys.domain.PointExchangeTO;
import com.azp.core.sys.model.PointExchange;
import com.azp.core.sys.model.PointExchangeDomain;
import com.azp.core.sys.model.PointExchangeFilterMapper;
import com.azp.core.sys.service.PointExchangeService;
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
@RequestMapping("facade/sys/point/exchange")
public class PointExchangeFacadeImpl implements PointExchangeFacade {
  @Autowired
  private PointExchangeService pointExchangeService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<PointExchangeTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "productId", required = false) String productId,
      @RequestParam(value = "pointNum", required = false) Integer pointNum,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    PointExchangeFilterMapper mapper = new PointExchangeFilterMapper();
    mapper.id = id;
    mapper.productId = productId;
    mapper.pointNum = pointNum;
    mapper.userId = userId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<PointExchangeTO> toList = new ArrayList<>();
    pointExchangeService.getListByFilter(mapper).forEach(entity -> toList.add(PointExchangeDomain.convert(entity, new PointExchangeTO())));
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
  public List<PointExchangeTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "productId", required = false) String productId,
      @RequestParam(value = "pointNum", required = false) Integer pointNum,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    PointExchangeFilterMapper mapper = new PointExchangeFilterMapper();
    mapper.id = id;
    mapper.productId = productId;
    mapper.pointNum = pointNum;
    mapper.userId = userId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<PointExchangeTO> toList = new ArrayList<>();
    pointExchangeService.getListByFilter(mapper).forEach(entity -> toList.add(PointExchangeDomain.convert(entity, new PointExchangeTO())));
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
  public PointExchangeTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    PointExchange entity = pointExchangeService.getByPK(id);
    return entity != null ? PointExchangeDomain.convert(entity, new PointExchangeTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody PointExchangeTO entityTO) {
    pointExchangeService.post(PointExchangeDomain.convert(entityTO, new PointExchange()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody PointExchangeTO entityTO) {
    pointExchangeService.update(PointExchangeDomain.convert(entityTO, new PointExchange()));
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
    pointExchangeService.delete(id);
  }
}
