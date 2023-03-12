package com.azp.core.sys.facade;

import com.azp.core.sys.domain.LevelFacade;
import com.azp.core.sys.domain.LevelTO;
import com.azp.core.sys.model.Level;
import com.azp.core.sys.model.LevelDomain;
import com.azp.core.sys.model.LevelFilterMapper;
import com.azp.core.sys.service.LevelService;
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
@RequestMapping("facade/sys/level")
public class LevelFacadeImpl implements LevelFacade {
  @Autowired
  private LevelService levelService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<LevelTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "idx", required = false) Integer idx,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    LevelFilterMapper mapper = new LevelFilterMapper();
    mapper.id = id;
    mapper.idx = idx;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<LevelTO> toList = new ArrayList<>();
    levelService.getListByFilter(mapper).forEach(entity -> toList.add(LevelDomain.convert(entity, new LevelTO())));
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
  public List<LevelTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "idx", required = false) Integer idx,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    LevelFilterMapper mapper = new LevelFilterMapper();
    mapper.id = id;
    mapper.idx = idx;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<LevelTO> toList = new ArrayList<>();
    levelService.getListByFilter(mapper).forEach(entity -> toList.add(LevelDomain.convert(entity, new LevelTO())));
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
  public LevelTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    Level entity = levelService.getByPK(id);
    return entity != null ? LevelDomain.convert(entity, new LevelTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody LevelTO entityTO) {
    levelService.post(LevelDomain.convert(entityTO, new Level()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody LevelTO entityTO) {
    levelService.update(LevelDomain.convert(entityTO, new Level()));
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
    levelService.delete(id);
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
  public List<LevelTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<LevelTO> toList = new ArrayList<>();
    levelService.getListByRelatedId(idList).forEach(entity -> toList.add(LevelDomain.convert(entity, new LevelTO())));
    return toList;
  }
}
