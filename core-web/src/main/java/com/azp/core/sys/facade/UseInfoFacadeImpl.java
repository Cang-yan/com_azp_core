package com.azp.core.sys.facade;

import com.azp.core.sys.domain.UseInfoFacade;
import com.azp.core.sys.domain.UseInfoTO;
import com.azp.core.sys.model.UseInfo;
import com.azp.core.sys.model.UseInfoDomain;
import com.azp.core.sys.model.UseInfoFilterMapper;
import com.azp.core.sys.service.UseInfoService;
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
@RequestMapping("facade/sys/use/info")
public class UseInfoFacadeImpl implements UseInfoFacade {
  @Autowired
  private UseInfoService useInfoService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<UseInfoTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "pointNumberFrom", required = false) Integer pointNumberFrom,
      @RequestParam(value = "pointNumberTo", required = false) Integer pointNumberTo,
      @RequestParam(value = "levelId", required = false) String levelId,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    UseInfoFilterMapper mapper = new UseInfoFilterMapper();
    mapper.id = id;
    mapper.pointNumberFrom = pointNumberFrom;
    mapper.pointNumberTo = pointNumberTo;
    mapper.levelId = levelId;
    mapper.userId = userId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<UseInfoTO> toList = new ArrayList<>();
    useInfoService.getListByFilter(mapper).forEach(entity -> toList.add(UseInfoDomain.convert(entity, new UseInfoTO())));
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
  public List<UseInfoTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "pointNumberFrom", required = false) Integer pointNumberFrom,
      @RequestParam(value = "pointNumberTo", required = false) Integer pointNumberTo,
      @RequestParam(value = "levelId", required = false) String levelId,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    UseInfoFilterMapper mapper = new UseInfoFilterMapper();
    mapper.id = id;
    mapper.pointNumberFrom = pointNumberFrom;
    mapper.pointNumberTo = pointNumberTo;
    mapper.levelId = levelId;
    mapper.userId = userId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<UseInfoTO> toList = new ArrayList<>();
    useInfoService.getListByFilter(mapper).forEach(entity -> toList.add(UseInfoDomain.convert(entity, new UseInfoTO())));
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
  public UseInfoTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    UseInfo entity = useInfoService.getByPK(id);
    return entity != null ? UseInfoDomain.convert(entity, new UseInfoTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody UseInfoTO entityTO) {
    useInfoService.post(UseInfoDomain.convert(entityTO, new UseInfo()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody UseInfoTO entityTO) {
    useInfoService.update(UseInfoDomain.convert(entityTO, new UseInfo()));
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
    useInfoService.delete(id);
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
  public List<UseInfoTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<UseInfoTO> toList = new ArrayList<>();
    useInfoService.getListByRelatedId(idList).forEach(entity -> toList.add(UseInfoDomain.convert(entity, new UseInfoTO())));
    return toList;
  }
}
