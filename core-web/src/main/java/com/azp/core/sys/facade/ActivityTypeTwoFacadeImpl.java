package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeTwoFacade;
import com.azp.core.sys.domain.ActivityTypeTwoTO;
import com.azp.core.sys.model.ActivityTypeTwo;
import com.azp.core.sys.model.ActivityTypeTwoDomain;
import com.azp.core.sys.model.ActivityTypeTwoFilterMapper;
import com.azp.core.sys.service.ActivityTypeTwoService;
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
@RequestMapping("facade/sys/activity/type/two")
public class ActivityTypeTwoFacadeImpl implements ActivityTypeTwoFacade {
  @Autowired
  private ActivityTypeTwoService activityTypeTwoService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeTwoTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeTwoFilterMapper mapper = new ActivityTypeTwoFilterMapper();
    mapper.id = id;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.departmentId = departmentId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeTwoTO> toList = new ArrayList<>();
    activityTypeTwoService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeTwoDomain.convert(entity, new ActivityTypeTwoTO())));
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
  public List<ActivityTypeTwoTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "statusIn", required = false) ArrayList<Integer> statusIn,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeTwoFilterMapper mapper = new ActivityTypeTwoFilterMapper();
    mapper.id = id;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.statusIn = statusIn;
    mapper.departmentId = departmentId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeTwoTO> toList = new ArrayList<>();
    activityTypeTwoService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeTwoDomain.convert(entity, new ActivityTypeTwoTO())));
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
  public ActivityTypeTwoTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeTwo entity = activityTypeTwoService.getByPK(id);
    return entity != null ? ActivityTypeTwoDomain.convert(entity, new ActivityTypeTwoTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeTwoTO entityTO) {
    activityTypeTwoService.post(ActivityTypeTwoDomain.convert(entityTO, new ActivityTypeTwo()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeTwoTO entityTO) {
    activityTypeTwoService.update(ActivityTypeTwoDomain.convert(entityTO, new ActivityTypeTwo()));
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
    activityTypeTwoService.delete(id);
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
  public List<ActivityTypeTwoTO> getListByRelatedActivitySubCategoryId(@RequestBody ArrayList<String> activitySubCategoryIdList) {
    List<ActivityTypeTwoTO> toList = new ArrayList<>();
    activityTypeTwoService.getListByRelatedActivitySubCategoryId(activitySubCategoryIdList).forEach(entity -> toList.add(ActivityTypeTwoDomain.convert(entity, new ActivityTypeTwoTO())));
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
  public List<ActivityTypeTwoTO> getListByRelatedId(@RequestBody ArrayList<String> idList) {
    List<ActivityTypeTwoTO> toList = new ArrayList<>();
    activityTypeTwoService.getListByRelatedId(idList).forEach(entity -> toList.add(ActivityTypeTwoDomain.convert(entity, new ActivityTypeTwoTO())));
    return toList;
  }
}
