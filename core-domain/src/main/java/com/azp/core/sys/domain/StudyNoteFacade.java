package com.azp.core.sys.domain;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FeignClient(
    value = "app-core",
    path = "/facade/sys/study/note"
)
public interface StudyNoteFacade {
  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @ResponseBody
  List<StudyNoteTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoUserId", required = false) String activityTypeTwoUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy);

  @RequestMapping(
      value = "filter/detail",
      method = RequestMethod.GET
  )
  @ResponseBody
  List<StudyNoteTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoUserId", required = false) String activityTypeTwoUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy);

  @RequestMapping(
      value = "single",
      method = RequestMethod.GET
  )
  StudyNoteTO getSingleByPK(@RequestParam(value = "id", required = true) String id);

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  void post(@RequestBody StudyNoteTO entityTO);

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  void patch(@RequestBody StudyNoteTO entityTO);

  @RequestMapping(
      value = "",
      method = RequestMethod.DELETE
  )
  void delete(@RequestParam(value = "id", required = true) String id);

  @RequestMapping(
      value = "filter/relation/ActivityTypeTwoUserId",
      method = RequestMethod.POST
  )
  @ResponseBody
  List<StudyNoteTO> getListByRelatedActivityTypeTwoUserId(@RequestBody ArrayList<String> activityTypeTwoUserIdList);
}
