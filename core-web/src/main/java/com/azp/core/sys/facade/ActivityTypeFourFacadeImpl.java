package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeFourFacade;
import com.azp.core.sys.domain.ActivityTypeFourTO;
import com.azp.core.sys.model.ActivityTypeFour;
import com.azp.core.sys.model.ActivityTypeFourDomain;
import com.azp.core.sys.model.ActivityTypeFourFilterMapper;
import com.azp.core.sys.service.ActivityTypeFourService;
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
@RequestMapping("facade/sys/activity/type/four")
public class ActivityTypeFourFacadeImpl implements ActivityTypeFourFacade {
  @Autowired
  private ActivityTypeFourService activityTypeFourService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "groupDateFrom", required = false) Long groupDateFrom,
      @RequestParam(value = "groupDateTo", required = false) Long groupDateTo,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "rank", required = false) Integer rank,
      @RequestParam(value = "periodsNumber", required = false) Integer periodsNumber,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourFilterMapper mapper = new ActivityTypeFourFilterMapper();
    mapper.id = id;
    mapper.groupDateFrom = groupDateFrom;
    mapper.groupDateTo = groupDateTo;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.rank = rank;
    mapper.periodsNumber = periodsNumber;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourTO> toList = new ArrayList<>();
    activityTypeFourService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourDomain.convert(entity, new ActivityTypeFourTO())));
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
  public List<ActivityTypeFourTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "groupDateFrom", required = false) Long groupDateFrom,
      @RequestParam(value = "groupDateTo", required = false) Long groupDateTo,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "rank", required = false) Integer rank,
      @RequestParam(value = "periodsNumber", required = false) Integer periodsNumber,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFourFilterMapper mapper = new ActivityTypeFourFilterMapper();
    mapper.id = id;
    mapper.groupDateFrom = groupDateFrom;
    mapper.groupDateTo = groupDateTo;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.rank = rank;
    mapper.periodsNumber = periodsNumber;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFourTO> toList = new ArrayList<>();
    activityTypeFourService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFourDomain.convert(entity, new ActivityTypeFourTO())));
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
  public ActivityTypeFourTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeFour entity = activityTypeFourService.getByPK(id);
    return entity != null ? ActivityTypeFourDomain.convert(entity, new ActivityTypeFourTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeFourTO entityTO) {
    activityTypeFourService.post(ActivityTypeFourDomain.convert(entityTO, new ActivityTypeFour()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeFourTO entityTO) {
    activityTypeFourService.update(ActivityTypeFourDomain.convert(entityTO, new ActivityTypeFour()));
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
    activityTypeFourService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivitySubCategoryId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFourTO> getListByRelatedActivitySubCategoryId(@RequestBody ArrayList<String> activitySubCategoryIdList) {
    List<ActivityTypeFourTO> toList = new ArrayList<>();
    activityTypeFourService.getListByRelatedActivitySubCategoryId(activitySubCategoryIdList).forEach(entity -> toList.add(ActivityTypeFourDomain.convert(entity, new ActivityTypeFourTO())));
    return toList;
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
  public List<ActivityTypeFourTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<ActivityTypeFourTO> toList = new ArrayList<>();
    activityTypeFourService.getListByRelatedId(idList).forEach(entity -> toList.add(ActivityTypeFourDomain.convert(entity, new ActivityTypeFourTO())));
    return toList;
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
  public List<ActivityTypeFourTO> getListByRelatedPeriodsNumber(@RequestBody ArrayList<Integer> periodsNumberList) {
    List<ActivityTypeFourTO> toList = new ArrayList<>();
    activityTypeFourService.getListByRelatedPeriodsNumber(periodsNumberList).forEach(entity -> toList.add(ActivityTypeFourDomain.convert(entity, new ActivityTypeFourTO())));
    return toList;
  }
}
