package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeOneFacade;
import com.azp.core.sys.domain.ActivityTypeOneTO;
import com.azp.core.sys.model.ActivityTypeOne;
import com.azp.core.sys.model.ActivityTypeOneDomain;
import com.azp.core.sys.model.ActivityTypeOneFilterMapper;
import com.azp.core.sys.service.ActivityTypeOneService;
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
@RequestMapping("facade/sys/activity/type/one")
public class ActivityTypeOneFacadeImpl implements ActivityTypeOneFacade {
  @Autowired
  private ActivityTypeOneService activityTypeOneService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeOneTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeOneFilterMapper mapper = new ActivityTypeOneFilterMapper();
    mapper.id = id;
    mapper.name = name;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeOneTO> toList = new ArrayList<>();
    activityTypeOneService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeOneDomain.convert(entity, new ActivityTypeOneTO())));
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
  public List<ActivityTypeOneTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeOneFilterMapper mapper = new ActivityTypeOneFilterMapper();
    mapper.id = id;
    mapper.name = name;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeOneTO> toList = new ArrayList<>();
    activityTypeOneService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeOneDomain.convert(entity, new ActivityTypeOneTO())));
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
  public ActivityTypeOneTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeOne entity = activityTypeOneService.getByPK(id);
    return entity != null ? ActivityTypeOneDomain.convert(entity, new ActivityTypeOneTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeOneTO entityTO) {
    activityTypeOneService.post(ActivityTypeOneDomain.convert(entityTO, new ActivityTypeOne()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeOneTO entityTO) {
    activityTypeOneService.update(ActivityTypeOneDomain.convert(entityTO, new ActivityTypeOne()));
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
    activityTypeOneService.delete(id);
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
  public List<ActivityTypeOneTO> getListByRelatedActivitySubCategoryId(@RequestBody ArrayList<String> activitySubCategoryIdList) {
    List<ActivityTypeOneTO> toList = new ArrayList<>();
    activityTypeOneService.getListByRelatedActivitySubCategoryId(activitySubCategoryIdList).forEach(entity -> toList.add(ActivityTypeOneDomain.convert(entity, new ActivityTypeOneTO())));
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
  public List<ActivityTypeOneTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<ActivityTypeOneTO> toList = new ArrayList<>();
    activityTypeOneService.getListByRelatedId(idList).forEach(entity -> toList.add(ActivityTypeOneDomain.convert(entity, new ActivityTypeOneTO())));
    return toList;
  }
}
