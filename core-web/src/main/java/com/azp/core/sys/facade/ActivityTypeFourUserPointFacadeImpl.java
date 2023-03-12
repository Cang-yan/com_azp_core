package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeFourUserPointFacade;
import com.azp.core.sys.domain.ActivityTypeFourUserPointTO;
import com.azp.core.sys.model.ActivityTypeFourUserPoint;
import com.azp.core.sys.model.ActivityTypeFourUserPointDomain;
import com.azp.core.sys.model.ActivityTypeFourUserPointFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourUserPointService;
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
@RequestMapping("facade/sys/activity/type/four/user/point")
public class ActivityTypeFourUserPointFacadeImpl implements ActivityTypeFourUserPointFacade {
  @Autowired
  private ActivityTypeFourUserPointService activityTypeFourUserPointService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourUserPointTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourUserPointFilterMapper mapper = new ActivityTypeFourUserPointFilterMapper();
    mapper.id = id;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourUserPointTO> toList = new ArrayList<>();
    activityTypeFourUserPointService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourUserPointDomain.convert(entity, new ActivityTypeFourUserPointTO())));
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
  public List<ActivityTypeFourUserPointTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourUserPointFilterMapper mapper = new ActivityTypeFourUserPointFilterMapper();
    mapper.id = id;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourUserPointTO> toList = new ArrayList<>();
    activityTypeFourUserPointService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourUserPointDomain.convert(entity, new ActivityTypeFourUserPointTO())));
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
  public ActivityTypeFourUserPointTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeFourUserPoint entity = activityTypeFourUserPointService.getByPK(id);
    return entity != null ? ActivityTypeFourUserPointDomain.convert(entity, new ActivityTypeFourUserPointTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeFourUserPointTO entityTO) {
    activityTypeFourUserPointService.post(ActivityTypeFourUserPointDomain.convert(entityTO, new ActivityTypeFourUserPoint()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeFourUserPointTO entityTO) {
    activityTypeFourUserPointService.update(ActivityTypeFourUserPointDomain.convert(entityTO, new ActivityTypeFourUserPoint()));
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
    activityTypeFourUserPointService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivityTypeFourUserId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourUserPointTO> getListByRelatedActivityTypeFourUserId(@RequestBody ArrayList<String> activityTypeFourUserIdList) {
    List<ActivityTypeFourUserPointTO> toList = new ArrayList<>();
    activityTypeFourUserPointService.getListByRelatedActivityTypeFourUserId(activityTypeFourUserIdList).forEach(entity -> toList.add(ActivityTypeFourUserPointDomain.convert(entity, new ActivityTypeFourUserPointTO())));
    return toList;
  }
}
