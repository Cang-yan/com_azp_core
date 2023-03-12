package com.azp.core.sys.facade;

import com.azp.core.sys.domain.LevelRecordFacade;
import com.azp.core.sys.domain.LevelRecordTO;
import com.azp.core.sys.model.LevelRecord;
import com.azp.core.sys.model.LevelRecordDomain;
import com.azp.core.sys.model.LevelRecordFilterMapper;
import com.azp.core.sys.service.LevelRecordService;
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
@RequestMapping("facade/sys/level/record")
public class LevelRecordFacadeImpl implements LevelRecordFacade {
  @Autowired
  private LevelRecordService levelRecordService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<LevelRecordTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "roleType", required = false) String roleType,
      @RequestParam(value = "departmentIdIn", required = false) ArrayList<String> departmentIdIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    LevelRecordFilterMapper mapper = new LevelRecordFilterMapper();
    mapper.id = id;
    mapper.roleType = roleType;
    mapper.departmentIdIn = departmentIdIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<LevelRecordTO> toList = new ArrayList<>();
    levelRecordService.getListByFilter(mapper).forEach(entity -> toList.add(LevelRecordDomain.convert(entity, new LevelRecordTO())));
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
  public List<LevelRecordTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "roleType", required = false) String roleType,
      @RequestParam(value = "departmentIdIn", required = false) ArrayList<String> departmentIdIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    LevelRecordFilterMapper mapper = new LevelRecordFilterMapper();
    mapper.id = id;
    mapper.roleType = roleType;
    mapper.departmentIdIn = departmentIdIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<LevelRecordTO> toList = new ArrayList<>();
    levelRecordService.getListByFilter(mapper).forEach(entity -> toList.add(LevelRecordDomain.convert(entity, new LevelRecordTO())));
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
  public LevelRecordTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    LevelRecord entity = levelRecordService.getByPK(id);
    return entity != null ? LevelRecordDomain.convert(entity, new LevelRecordTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody LevelRecordTO entityTO) {
    levelRecordService.post(LevelRecordDomain.convert(entityTO, new LevelRecord()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody LevelRecordTO entityTO) {
    levelRecordService.update(LevelRecordDomain.convert(entityTO, new LevelRecord()));
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
    levelRecordService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/LevelId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<LevelRecordTO> getListByRelatedLevelId(@RequestBody ArrayList<String> levelIdList) {
    List<LevelRecordTO> toList = new ArrayList<>();
    levelRecordService.getListByRelatedLevelId(levelIdList).forEach(entity -> toList.add(LevelRecordDomain.convert(entity, new LevelRecordTO())));
    return toList;
  }
}
