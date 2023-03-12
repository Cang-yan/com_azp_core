package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeOneUserFacade;
import com.azp.core.sys.domain.ActivityTypeOneUserTO;
import com.azp.core.sys.model.ActivityTypeOneUser;
import com.azp.core.sys.model.ActivityTypeOneUserDomain;
import com.azp.core.sys.model.ActivityTypeOneUserFilterMapper;
import com.azp.core.sys.service.ActivityTypeOneUserService;
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
@RequestMapping("facade/sys/activity/type/one/user")
public class ActivityTypeOneUserFacadeImpl implements ActivityTypeOneUserFacade {
  @Autowired
  private ActivityTypeOneUserService activityTypeOneUserService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeOneUserTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeOneId", required = false) String activityTypeOneId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeOneUserFilterMapper mapper = new ActivityTypeOneUserFilterMapper();
    mapper.id = id;
    mapper.statusIn = statusIn;
    mapper.userId = userId;
    mapper.activityTypeOneId = activityTypeOneId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeOneUserTO> toList = new ArrayList<>();
    activityTypeOneUserService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeOneUserDomain.convert(entity, new ActivityTypeOneUserTO())));
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
  public List<ActivityTypeOneUserTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeOneId", required = false) String activityTypeOneId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeOneUserFilterMapper mapper = new ActivityTypeOneUserFilterMapper();
    mapper.id = id;
    mapper.statusIn = statusIn;
    mapper.userId = userId;
    mapper.activityTypeOneId = activityTypeOneId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeOneUserTO> toList = new ArrayList<>();
    activityTypeOneUserService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeOneUserDomain.convert(entity, new ActivityTypeOneUserTO())));
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
  public ActivityTypeOneUserTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeOneUser entity = activityTypeOneUserService.getByPK(id);
    return entity != null ? ActivityTypeOneUserDomain.convert(entity, new ActivityTypeOneUserTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeOneUserTO entityTO) {
    activityTypeOneUserService.post(ActivityTypeOneUserDomain.convert(entityTO, new ActivityTypeOneUser()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeOneUserTO entityTO) {
    activityTypeOneUserService.update(ActivityTypeOneUserDomain.convert(entityTO, new ActivityTypeOneUser()));
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
    activityTypeOneUserService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivityTypeOneId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeOneUserTO> getListByRelatedActivityTypeOneId(@RequestBody ArrayList<String> activityTypeOneIdList) {
    List<ActivityTypeOneUserTO> toList = new ArrayList<>();
    activityTypeOneUserService.getListByRelatedActivityTypeOneId(activityTypeOneIdList).forEach(entity -> toList.add(ActivityTypeOneUserDomain.convert(entity, new ActivityTypeOneUserTO())));
    return toList;
  }
}
