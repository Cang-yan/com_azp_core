package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeFourUserFacade;
import com.azp.core.sys.domain.ActivityTypeFourUserTO;
import com.azp.core.sys.model.ActivityTypeFourUser;
import com.azp.core.sys.model.ActivityTypeFourUserDomain;
import com.azp.core.sys.model.ActivityTypeFourUserFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourUserService;
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
@RequestMapping("facade/sys/activity/type/four/user")
public class ActivityTypeFourUserFacadeImpl implements ActivityTypeFourUserFacade {
  @Autowired
  private ActivityTypeFourUserService activityTypeFourUserService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourUserTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activityTypeFourId", required = false) String activityTypeFourId,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "place", required = false) Integer place,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourUserFilterMapper mapper = new ActivityTypeFourUserFilterMapper();
    mapper.id = id;
    mapper.activityTypeFourId = activityTypeFourId;
    mapper.userId = userId;
    mapper.statusIn = statusIn;
    mapper.place = place;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourUserTO> toList = new ArrayList<>();
    activityTypeFourUserService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourUserDomain.convert(entity, new ActivityTypeFourUserTO())));
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
  public List<ActivityTypeFourUserTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activityTypeFourId", required = false) String activityTypeFourId,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "place", required = false) Integer place,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourUserFilterMapper mapper = new ActivityTypeFourUserFilterMapper();
    mapper.id = id;
    mapper.activityTypeFourId = activityTypeFourId;
    mapper.userId = userId;
    mapper.statusIn = statusIn;
    mapper.place = place;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourUserTO> toList = new ArrayList<>();
    activityTypeFourUserService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourUserDomain.convert(entity, new ActivityTypeFourUserTO())));
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
  public ActivityTypeFourUserTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeFourUser entity = activityTypeFourUserService.getByPK(id);
    return entity != null ? ActivityTypeFourUserDomain.convert(entity, new ActivityTypeFourUserTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeFourUserTO entityTO) {
    activityTypeFourUserService.post(ActivityTypeFourUserDomain.convert(entityTO, new ActivityTypeFourUser()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeFourUserTO entityTO) {
    activityTypeFourUserService.update(ActivityTypeFourUserDomain.convert(entityTO, new ActivityTypeFourUser()));
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
    activityTypeFourUserService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivityTypeFourId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourUserTO> getListByRelatedActivityTypeFourId(@RequestBody ArrayList<String> activityTypeFourIdList) {
    List<ActivityTypeFourUserTO> toList = new ArrayList<>();
    activityTypeFourUserService.getListByRelatedActivityTypeFourId(activityTypeFourIdList).forEach(entity -> toList.add(ActivityTypeFourUserDomain.convert(entity, new ActivityTypeFourUserTO())));
    return toList;
  }
}
