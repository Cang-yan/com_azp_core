package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeFourPeriodsFacade;
import com.azp.core.sys.domain.ActivityTypeFourPeriodsTO;
import com.azp.core.sys.model.ActivityTypeFourPeriods;
import com.azp.core.sys.model.ActivityTypeFourPeriodsDomain;
import com.azp.core.sys.model.ActivityTypeFourPeriodsFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourPeriodsService;
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
@RequestMapping("facade/sys/activity/type/four/periods")
public class ActivityTypeFourPeriodsFacadeImpl implements ActivityTypeFourPeriodsFacade {
  @Autowired
  private ActivityTypeFourPeriodsService activityTypeFourPeriodsService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourPeriodsTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "periodsNumber", required = false) Integer periodsNumber,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourPeriodsFilterMapper mapper = new ActivityTypeFourPeriodsFilterMapper();
    mapper.id = id;
    mapper.periodsNumber = periodsNumber;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourPeriodsTO> toList = new ArrayList<>();
    activityTypeFourPeriodsService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourPeriodsDomain.convert(entity, new ActivityTypeFourPeriodsTO())));
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
  public List<ActivityTypeFourPeriodsTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "periodsNumber", required = false) Integer periodsNumber,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourPeriodsFilterMapper mapper = new ActivityTypeFourPeriodsFilterMapper();
    mapper.id = id;
    mapper.periodsNumber = periodsNumber;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourPeriodsTO> toList = new ArrayList<>();
    activityTypeFourPeriodsService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourPeriodsDomain.convert(entity, new ActivityTypeFourPeriodsTO())));
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
  public ActivityTypeFourPeriodsTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeFourPeriods entity = activityTypeFourPeriodsService.getByPK(id);
    return entity != null ? ActivityTypeFourPeriodsDomain.convert(entity, new ActivityTypeFourPeriodsTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeFourPeriodsTO entityTO) {
    activityTypeFourPeriodsService.post(ActivityTypeFourPeriodsDomain.convert(entityTO, new ActivityTypeFourPeriods()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeFourPeriodsTO entityTO) {
    activityTypeFourPeriodsService.update(ActivityTypeFourPeriodsDomain.convert(entityTO, new ActivityTypeFourPeriods()));
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
    activityTypeFourPeriodsService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/PeriodsNumber",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourPeriodsTO> getListByRelatedPeriodsNumber(@RequestBody ArrayList<Integer> periodsNumberList) {
    List<ActivityTypeFourPeriodsTO> toList = new ArrayList<>();
    activityTypeFourPeriodsService.getListByRelatedPeriodsNumber(periodsNumberList).forEach(entity -> toList.add(ActivityTypeFourPeriodsDomain.convert(entity, new ActivityTypeFourPeriodsTO())));
    return toList;
  }
}
