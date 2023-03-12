package com.azp.core.sys.facade;

import com.azp.core.sys.domain.StudyNoteFacade;
import com.azp.core.sys.domain.StudyNoteTO;
import com.azp.core.sys.model.StudyNote;
import com.azp.core.sys.model.StudyNoteDomain;
import com.azp.core.sys.model.StudyNoteFilterMapper;
import com.azp.core.sys.service.StudyNoteService;
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
@RequestMapping("facade/sys/study/note")
public class StudyNoteFacadeImpl implements StudyNoteFacade {
  @Autowired
  private StudyNoteService studyNoteService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<StudyNoteTO> getFilterList(@RequestParam(value = "id", required = false) String id,
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
    List<StudyNoteTO> toList = new ArrayList<>();
    studyNoteService.getListByFilter(mapper).forEach(entity -> toList.add(StudyNoteDomain.convert(entity, new StudyNoteTO())));
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
  public List<StudyNoteTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
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
    List<StudyNoteTO> toList = new ArrayList<>();
    studyNoteService.getListByFilter(mapper).forEach(entity -> toList.add(StudyNoteDomain.convert(entity, new StudyNoteTO())));
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
  public StudyNoteTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    StudyNote entity = studyNoteService.getByPK(id);
    return entity != null ? StudyNoteDomain.convert(entity, new StudyNoteTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody StudyNoteTO entityTO) {
    studyNoteService.post(StudyNoteDomain.convert(entityTO, new StudyNote()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody StudyNoteTO entityTO) {
    studyNoteService.update(StudyNoteDomain.convert(entityTO, new StudyNote()));
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
    studyNoteService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivityTypeTwoUserId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<StudyNoteTO> getListByRelatedActivityTypeTwoUserId(@RequestBody ArrayList<String> activityTypeTwoUserIdList) {
    List<StudyNoteTO> toList = new ArrayList<>();
    studyNoteService.getListByRelatedActivityTypeTwoUserId(activityTypeTwoUserIdList).forEach(entity -> toList.add(StudyNoteDomain.convert(entity, new StudyNoteTO())));
    return toList;
  }
}
