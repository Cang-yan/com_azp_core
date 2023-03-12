package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeTwoUserFacade;
import com.azp.core.sys.domain.ActivityTypeTwoUserTO;
import com.azp.core.sys.model.ActivityTypeTwoUser;
import com.azp.core.sys.model.ActivityTypeTwoUserDomain;
import com.azp.core.sys.model.ActivityTypeTwoUserFilterMapper;
import com.azp.core.sys.service.ActivityTypeTwoUserService;
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
@RequestMapping("facade/sys/activity/type/two/user")
public class ActivityTypeTwoUserFacadeImpl implements ActivityTypeTwoUserFacade {
  @Autowired
  private ActivityTypeTwoUserService activityTypeTwoUserService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeTwoUserTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoId", required = false) String activityTypeTwoId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeTwoUserFilterMapper mapper = new ActivityTypeTwoUserFilterMapper();
    mapper.id = id;
    mapper.statusIn = statusIn;
    mapper.userId = userId;
    mapper.activityTypeTwoId = activityTypeTwoId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeTwoUserTO> toList = new ArrayList<>();
    activityTypeTwoUserService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeTwoUserDomain.convert(entity, new ActivityTypeTwoUserTO())));
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
  public List<ActivityTypeTwoUserTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "activityTypeTwoId", required = false) String activityTypeTwoId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeTwoUserFilterMapper mapper = new ActivityTypeTwoUserFilterMapper();
    mapper.id = id;
    mapper.statusIn = statusIn;
    mapper.userId = userId;
    mapper.activityTypeTwoId = activityTypeTwoId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeTwoUserTO> toList = new ArrayList<>();
    activityTypeTwoUserService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeTwoUserDomain.convert(entity, new ActivityTypeTwoUserTO())));
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
  public ActivityTypeTwoUserTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeTwoUser entity = activityTypeTwoUserService.getByPK(id);
    return entity != null ? ActivityTypeTwoUserDomain.convert(entity, new ActivityTypeTwoUserTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeTwoUserTO entityTO) {
    activityTypeTwoUserService.post(ActivityTypeTwoUserDomain.convert(entityTO, new ActivityTypeTwoUser()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeTwoUserTO entityTO) {
    activityTypeTwoUserService.update(ActivityTypeTwoUserDomain.convert(entityTO, new ActivityTypeTwoUser()));
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
    activityTypeTwoUserService.delete(id);
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
  public List<ActivityTypeTwoUserTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<ActivityTypeTwoUserTO> toList = new ArrayList<>();
    activityTypeTwoUserService.getListByRelatedId(idList).forEach(entity -> toList.add(ActivityTypeTwoUserDomain.convert(entity, new ActivityTypeTwoUserTO())));
    return toList;
  }
}
