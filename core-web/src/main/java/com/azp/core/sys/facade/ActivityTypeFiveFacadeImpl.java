package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeFiveFacade;
import com.azp.core.sys.domain.ActivityTypeFiveTO;
import com.azp.core.sys.model.ActivityTypeFive;
import com.azp.core.sys.model.ActivityTypeFiveDomain;
import com.azp.core.sys.model.ActivityTypeFiveFilterMapper;
import com.azp.core.sys.service.ActivityTypeFiveService;
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
@RequestMapping("facade/sys/activity/type/five")
public class ActivityTypeFiveFacadeImpl implements ActivityTypeFiveFacade {
  @Autowired
  private ActivityTypeFiveService activityTypeFiveService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFiveTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activitySubCategory", required = false) String activitySubCategory,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFiveFilterMapper mapper = new ActivityTypeFiveFilterMapper();
    mapper.id = id;
    mapper.activitySubCategory = activitySubCategory;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFiveTO> toList = new ArrayList<>();
    activityTypeFiveService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFiveDomain.convert(entity, new ActivityTypeFiveTO())));
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
  public List<ActivityTypeFiveTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activitySubCategory", required = false) String activitySubCategory,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeFiveFilterMapper mapper = new ActivityTypeFiveFilterMapper();
    mapper.id = id;
    mapper.activitySubCategory = activitySubCategory;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeFiveTO> toList = new ArrayList<>();
    activityTypeFiveService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeFiveDomain.convert(entity, new ActivityTypeFiveTO())));
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
  public ActivityTypeFiveTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeFive entity = activityTypeFiveService.getByPK(id);
    return entity != null ? ActivityTypeFiveDomain.convert(entity, new ActivityTypeFiveTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeFiveTO entityTO) {
    activityTypeFiveService.post(ActivityTypeFiveDomain.convert(entityTO, new ActivityTypeFive()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeFiveTO entityTO) {
    activityTypeFiveService.update(ActivityTypeFiveDomain.convert(entityTO, new ActivityTypeFive()));
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
    activityTypeFiveService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivitySubCategory",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeFiveTO> getListByRelatedActivitySubCategory(@RequestBody ArrayList<String> activitySubCategoryList) {
    List<ActivityTypeFiveTO> toList = new ArrayList<>();
    activityTypeFiveService.getListByRelatedActivitySubCategory(activitySubCategoryList).forEach(entity -> toList.add(ActivityTypeFiveDomain.convert(entity, new ActivityTypeFiveTO())));
    return toList;
  }
}
