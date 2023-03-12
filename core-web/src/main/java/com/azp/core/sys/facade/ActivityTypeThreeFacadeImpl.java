package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeThreeFacade;
import com.azp.core.sys.domain.ActivityTypeThreeTO;
import com.azp.core.sys.model.ActivityTypeThree;
import com.azp.core.sys.model.ActivityTypeThreeDomain;
import com.azp.core.sys.model.ActivityTypeThreeFilterMapper;
import com.azp.core.sys.service.ActivityTypeThreeService;
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
@RequestMapping("facade/sys/activity/type/three")
public class ActivityTypeThreeFacadeImpl implements ActivityTypeThreeFacade {
  @Autowired
  private ActivityTypeThreeService activityTypeThreeService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeThreeTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "isOutstanding", required = false) Integer isOutstanding,
      @RequestParam(value = "createUserId", required = false) String createUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeThreeFilterMapper mapper = new ActivityTypeThreeFilterMapper();
    mapper.id = id;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.isOutstanding = isOutstanding;
    mapper.createUserId = createUserId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeThreeTO> toList = new ArrayList<>();
    activityTypeThreeService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeThreeDomain.convert(entity, new ActivityTypeThreeTO())));
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
  public List<ActivityTypeThreeTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "isOutstanding", required = false) Integer isOutstanding,
      @RequestParam(value = "createUserId", required = false) String createUserId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeThreeFilterMapper mapper = new ActivityTypeThreeFilterMapper();
    mapper.id = id;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.isOutstanding = isOutstanding;
    mapper.createUserId = createUserId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeThreeTO> toList = new ArrayList<>();
    activityTypeThreeService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeThreeDomain.convert(entity, new ActivityTypeThreeTO())));
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
  public ActivityTypeThreeTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeThree entity = activityTypeThreeService.getByPK(id);
    return entity != null ? ActivityTypeThreeDomain.convert(entity, new ActivityTypeThreeTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeThreeTO entityTO) {
    activityTypeThreeService.post(ActivityTypeThreeDomain.convert(entityTO, new ActivityTypeThree()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeThreeTO entityTO) {
    activityTypeThreeService.update(ActivityTypeThreeDomain.convert(entityTO, new ActivityTypeThree()));
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
    activityTypeThreeService.delete(id);
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
  public List<ActivityTypeThreeTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<ActivityTypeThreeTO> toList = new ArrayList<>();
    activityTypeThreeService.getListByRelatedId(idList).forEach(entity -> toList.add(ActivityTypeThreeDomain.convert(entity, new ActivityTypeThreeTO())));
    return toList;
  }
}
