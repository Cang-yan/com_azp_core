package com.azp.core.sys.web;

import com.azp.core.sys.model.StudyNoteFilterMapper;
import com.azp.core.sys.model.StudyNotePostMapper;
import com.azp.core.sys.model.StudyNoteUpdateMapper;
import com.azp.core.sys.service.StudyNoteService;
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
    value = "study_note",
    tags = "学习心得管理"
)
@RestController
@RequestMapping("api/sys/study/note")
public class StudyNoteController {
  @Autowired
  private StudyNoteService studyNoteService;

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
        .addDataValue(studyNoteService.getSimpleMapByPK(id))
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
        .addDataValue(studyNoteService.getDetailMapByPK(id))
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
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoUserId", required = false) String activityTypeTwoUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    StudyNoteFilterMapper mapper = new StudyNoteFilterMapper();
    mapper.id = id;
    mapper.userId = userId;
    mapper.activityTypeTwoUserId = activityTypeTwoUserId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    return Status.successBuilder()
        .addDataValue(studyNoteService.getFilterMapList(mapper))
        .addDataCount(studyNoteService.getCountByFilter(mapper))
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
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoUserId", required = false) String activityTypeTwoUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    StudyNoteFilterMapper mapper = new StudyNoteFilterMapper();
    mapper.id = id;
    mapper.userId = userId;
    mapper.activityTypeTwoUserId = activityTypeTwoUserId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    return Status.successBuilder()
        .addDataValue(studyNoteService.getFilterDetailMapList(mapper))
        .addDataCount(studyNoteService.getCountByFilter(mapper))
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
  public Map<String, Object> post(@RequestBody StudyNotePostMapper postMapper) {
    return Status.successBuilder()
        .addDataValue(studyNoteService.postMapping(postMapper))
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
  public Map<String, Object> postBatch(@RequestBody ArrayList<StudyNotePostMapper> postMappers) {
    return Status.successBuilder()
        .addDataValue(studyNoteService.postMappingList(postMappers))
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
  public Map<String, Object> patch(@RequestBody StudyNoteUpdateMapper updateMapper) {
    return Status.successBuilder()
        .addDataValue(studyNoteService.updateMapping(updateMapper))
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
  public Map<String, Object> patchBatch(@RequestBody ArrayList<StudyNoteUpdateMapper> updateMappers) {
    return Status.successBuilder()
        .addDataValue(studyNoteService.updateMappingList(updateMappers))
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
  public Map<String, Object> put(@RequestBody StudyNoteUpdateMapper putMapper) {
    return Status.successBuilder()
        .addDataValue(studyNoteService.putMapping(putMapper))
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
  public Map<String, Object> putBatch(@RequestBody ArrayList<StudyNoteUpdateMapper> putMappers) {
    return Status.successBuilder()
        .addDataValue(studyNoteService.putMappingList(putMappers))
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
        .addDataValue(studyNoteService.delete(id))
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
        .addDataValue(studyNoteService.deleteList(entityKeys))
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
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoUserId", required = false) String activityTypeTwoUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    StudyNoteFilterMapper mapper = new StudyNoteFilterMapper();
    mapper.id = id;
    mapper.userId = userId;
    mapper.activityTypeTwoUserId = activityTypeTwoUserId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    return Status.successBuilder()
        .addDataValue(studyNoteService.getRateAndCountByFilter(mapper))
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
  public Map<String, Object> getRateAndCountFilterMapList(@RequestBody ArrayList<StudyNoteFilterMapper> filterMappers) {
    return Status.successBuilder()
        .addDataValue(studyNoteService.getRateAndCountListByFilter(filterMappers))
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
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoUserId", required = false) String activityTypeTwoUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy,
      @RequestParam(value = "groupBy", required = true) ArrayList<String> groupBy) {
    StudyNoteFilterMapper mapper = new StudyNoteFilterMapper();
    mapper.id = id;
    mapper.userId = userId;
    mapper.activityTypeTwoUserId = activityTypeTwoUserId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    return Status.successBuilder()
        .addDataValue(studyNoteService.getFilterListGroup(mapper, groupBy))
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
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoUserId", required = false) String activityTypeTwoUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy,
      @RequestParam(value = "groupBy", required = true) ArrayList<String> groupBy) {
    StudyNoteFilterMapper mapper = new StudyNoteFilterMapper();
    mapper.id = id;
    mapper.userId = userId;
    mapper.activityTypeTwoUserId = activityTypeTwoUserId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    return Status.successBuilder()
        .addDataValue(studyNoteService.getFilterDetailListGroup(mapper, groupBy))
        .map();
  }
}