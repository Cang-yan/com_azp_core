package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivitySubCategoryFacade;
import com.azp.core.sys.domain.ActivitySubCategoryTO;
import com.azp.core.sys.model.ActivitySubCategory;
import com.azp.core.sys.model.ActivitySubCategoryDomain;
import com.azp.core.sys.model.ActivitySubCategoryFilterMapper;
import com.azp.core.sys.service.ActivitySubCategoryService;
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
@RequestMapping("facade/sys/activity/sub/category")
public class ActivitySubCategoryFacadeImpl implements ActivitySubCategoryFacade {
  @Autowired
  private ActivitySubCategoryService activitySubCategoryService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivitySubCategoryTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activityCategoryIdIn", required = false) ArrayList<String> activityCategoryIdIn,
      @RequestParam(value = "onTop", required = false) Integer onTop,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivitySubCategoryFilterMapper mapper = new ActivitySubCategoryFilterMapper();
    mapper.id = id;
    mapper.activityCategoryIdIn = activityCategoryIdIn;
    mapper.onTop = onTop;
    mapper.statusIn = statusIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivitySubCategoryTO> toList = new ArrayList<>();
    activitySubCategoryService.getListByFilter(mapper).forEach(entity -> toList.add(ActivitySubCategoryDomain.convert(entity, new ActivitySubCategoryTO())));
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
  public List<ActivitySubCategoryTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activityCategoryIdIn", required = false) ArrayList<String> activityCategoryIdIn,
      @RequestParam(value = "onTop", required = false) Integer onTop,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivitySubCategoryFilterMapper mapper = new ActivitySubCategoryFilterMapper();
    mapper.id = id;
    mapper.activityCategoryIdIn = activityCategoryIdIn;
    mapper.onTop = onTop;
    mapper.statusIn = statusIn;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivitySubCategoryTO> toList = new ArrayList<>();
    activitySubCategoryService.getListByFilter(mapper).forEach(entity -> toList.add(ActivitySubCategoryDomain.convert(entity, new ActivitySubCategoryTO())));
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
  public ActivitySubCategoryTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivitySubCategory entity = activitySubCategoryService.getByPK(id);
    return entity != null ? ActivitySubCategoryDomain.convert(entity, new ActivitySubCategoryTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivitySubCategoryTO entityTO) {
    activitySubCategoryService.post(ActivitySubCategoryDomain.convert(entityTO, new ActivitySubCategory()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivitySubCategoryTO entityTO) {
    activitySubCategoryService.update(ActivitySubCategoryDomain.convert(entityTO, new ActivitySubCategory()));
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
    activitySubCategoryService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivityCategoryId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivitySubCategoryTO> getListByRelatedActivityCategoryId(@RequestBody ArrayList<String> activityCategoryIdList) {
    List<ActivitySubCategoryTO> toList = new ArrayList<>();
    activitySubCategoryService.getListByRelatedActivityCategoryId(activityCategoryIdList).forEach(entity -> toList.add(ActivitySubCategoryDomain.convert(entity, new ActivitySubCategoryTO())));
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
  public List<ActivitySubCategoryTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<ActivitySubCategoryTO> toList = new ArrayList<>();
    activitySubCategoryService.getListByRelatedId(idList).forEach(entity -> toList.add(ActivitySubCategoryDomain.convert(entity, new ActivitySubCategoryTO())));
    return toList;
  }
}
