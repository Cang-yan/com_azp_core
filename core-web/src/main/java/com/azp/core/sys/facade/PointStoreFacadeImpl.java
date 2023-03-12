package com.azp.core.sys.facade;

import com.azp.core.sys.domain.PointStoreFacade;
import com.azp.core.sys.domain.PointStoreTO;
import com.azp.core.sys.model.PointStore;
import com.azp.core.sys.model.PointStoreDomain;
import com.azp.core.sys.model.PointStoreFilterMapper;
import com.azp.core.sys.service.PointStoreService;
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
@RequestMapping("facade/sys/point/store")
public class PointStoreFacadeImpl implements PointStoreFacade {
  @Autowired
  private PointStoreService pointStoreService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<PointStoreTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "productType", required = false) Integer productType,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    PointStoreFilterMapper mapper = new PointStoreFilterMapper();
    mapper.id = id;
    mapper.productType = productType;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<PointStoreTO> toList = new ArrayList<>();
    pointStoreService.getListByFilter(mapper).forEach(entity -> toList.add(PointStoreDomain.convert(entity, new PointStoreTO())));
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
  public List<PointStoreTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "productType", required = false) Integer productType,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    PointStoreFilterMapper mapper = new PointStoreFilterMapper();
    mapper.id = id;
    mapper.productType = productType;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<PointStoreTO> toList = new ArrayList<>();
    pointStoreService.getListByFilter(mapper).forEach(entity -> toList.add(PointStoreDomain.convert(entity, new PointStoreTO())));
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
  public PointStoreTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    PointStore entity = pointStoreService.getByPK(id);
    return entity != null ? PointStoreDomain.convert(entity, new PointStoreTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody PointStoreTO entityTO) {
    pointStoreService.post(PointStoreDomain.convert(entityTO, new PointStore()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody PointStoreTO entityTO) {
    pointStoreService.update(PointStoreDomain.convert(entityTO, new PointStore()));
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
    pointStoreService.delete(id);
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
  public List<PointStoreTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<PointStoreTO> toList = new ArrayList<>();
    pointStoreService.getListByRelatedId(idList).forEach(entity -> toList.add(PointStoreDomain.convert(entity, new PointStoreTO())));
    return toList;
  }
}
