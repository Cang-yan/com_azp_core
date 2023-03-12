package com.azp.core.sys.web;

import com.azp.core.sys.model.PointFilterMapper;
import com.azp.core.sys.model.PointPostMapper;
import com.azp.core.sys.model.PointUpdateMapper;
import com.azp.core.sys.service.PointService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.Map;
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
@Api(
    value = "point",
    tags = "积分管理"
)
@RestController
@RequestMapping("api/sys/point")
public class PointController {
  @Autowired
  private PointService pointService;

  @AuthGroup("admin")
  @ApiOperation(
      value = "获取实体",
      notes = "获取实体"
  )
  @RequestMapping(
      value = "single/simple",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getSimpleMapByPK(@RequestParam(value = "id", required = true) String id) {
    return Status.successBuilder()
        .addDataValue(pointService.getSimpleMapByPK(id))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "获取实体detail",
      notes = "获取实体detail"
  )
  @RequestMapping(
      value = "single",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getDetailMapByPK(@RequestParam(value = "id", required = true) String id) {
    return Status.successBuilder()
        .addDataValue(pointService.getDetailMapByPK(id))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "获取过滤列表",
      notes = "获取过滤列表"
  )
  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getFilterMapList(@RequestParam(value = "id", required = false) String id,
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
    return Status.successBuilder()
        .addDataValue(pointService.getFilterMapList(mapper))
        .addDataCount(pointService.getCountByFilter(mapper))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "获取过滤列表",
      notes = "获取过滤列表"
  )
  @RequestMapping(
      value = "filter/detail",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getFilterDetailMapList(@RequestParam(value = "id", required = false) String id,
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
    return Status.successBuilder()
        .addDataValue(pointService.getFilterDetailMapList(mapper))
        .addDataCount(pointService.getCountByFilter(mapper))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "发布实体",
      notes = "发布实体"
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> post(@RequestBody PointPostMapper postMapper) {
    return Status.successBuilder()
        .addDataValue(pointService.postMapping(postMapper))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "发布一组实体",
      notes = "发布一组实体"
  )
  @RequestMapping(
      value = "batch",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> postBatch(@RequestBody ArrayList<PointPostMapper> postMappers) {
    return Status.successBuilder()
        .addDataValue(pointService.postMappingList(postMappers))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "更新实体",
      notes = "更新实体"
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> patch(@RequestBody PointUpdateMapper updateMapper) {
    return Status.successBuilder()
        .addDataValue(pointService.updateMapping(updateMapper))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "更新一组实体",
      notes = "更新一组实体"
  )
  @RequestMapping(
      value = "batch",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> patchBatch(@RequestBody ArrayList<PointUpdateMapper> updateMappers) {
    return Status.successBuilder()
        .addDataValue(pointService.updateMappingList(updateMappers))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "插入或更新实体",
      notes = "插入或更新实体"
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.PUT
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> put(@RequestBody PointUpdateMapper putMapper) {
    return Status.successBuilder()
        .addDataValue(pointService.putMapping(putMapper))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "插入或更新一组实体",
      notes = "插入或更新一组实体"
  )
  @RequestMapping(
      value = "batch",
      method = RequestMethod.PUT
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> putBatch(@RequestBody ArrayList<PointUpdateMapper> putMappers) {
    return Status.successBuilder()
        .addDataValue(pointService.putMappingList(putMappers))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "删除实体",
      notes = "删除实体"
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.DELETE
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> delete(@RequestParam(value = "id", required = true) String id) {
    return Status.successBuilder()
        .addDataValue(pointService.delete(id))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "删除实体",
      notes = "删除实体"
  )
  @RequestMapping(
      value = "batch",
      method = RequestMethod.DELETE
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> deleteBatch(@RequestParam("entityKeys") ArrayList<String> entityKeys) {
    return Status.successBuilder()
        .addDataValue(pointService.deleteList(entityKeys))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "获取比率统计",
      notes = "获取比率统计"
  )
  @RequestMapping(
      value = "rate",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getRateAndCountFilterMap(@RequestParam(value = "id", required = false) String id,
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
    return Status.successBuilder()
        .addDataValue(pointService.getRateAndCountByFilter(mapper))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "批量获取比率统计",
      notes = "批量获取比率统计"
  )
  @RequestMapping(
      value = "rate/batch",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getRateAndCountFilterMapList(@RequestBody ArrayList<PointFilterMapper> filterMappers) {
    return Status.successBuilder()
        .addDataValue(pointService.getRateAndCountListByFilter(filterMappers))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "获取按组过滤列表",
      notes = "获取按组过滤列表"
  )
  @RequestMapping(
      value = "group",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getFilterListGroup(@RequestParam(value = "id", required = false) String id,
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
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy,
      @RequestParam(value = "groupBy", required = true) ArrayList<String> groupBy) {
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
    return Status.successBuilder()
        .addDataValue(pointService.getFilterListGroup(mapper, groupBy))
        .map();
  }

  @AuthGroup("admin")
  @ApiOperation(
      value = "获取按组过滤列表",
      notes = "获取按组过滤列表"
  )
  @RequestMapping(
      value = "group/detail",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getFilterDetailListGroup(@RequestParam(value = "id", required = false) String id,
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
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy,
      @RequestParam(value = "groupBy", required = true) ArrayList<String> groupBy) {
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
    return Status.successBuilder()
        .addDataValue(pointService.getFilterDetailListGroup(mapper, groupBy))
        .map();
  }
}
