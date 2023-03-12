package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeSixFacade;
import com.azp.core.sys.domain.ActivityTypeSixTO;
import com.azp.core.sys.model.ActivityTypeSix;
import com.azp.core.sys.model.ActivityTypeSixDomain;
import com.azp.core.sys.model.ActivityTypeSixFilterMapper;
import com.azp.core.sys.service.ActivityTypeSixService;
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
@RequestMapping("facade/sys/activity/type/six")
public class ActivityTypeSixFacadeImpl implements ActivityTypeSixFacade {
  @Autowired
  private ActivityTypeSixService activityTypeSixService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeSixTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "dateFrom", required = false) Long dateFrom,
      @RequestParam(value = "dateTo", required = false) Long dateTo,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeSixFilterMapper mapper = new ActivityTypeSixFilterMapper();
    mapper.id = id;
    mapper.dateFrom = dateFrom;
    mapper.dateTo = dateTo;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.userId = userId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeSixTO> toList = new ArrayList<>();
    activityTypeSixService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeSixDomain.convert(entity, new ActivityTypeSixTO())));
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
  public List<ActivityTypeSixTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "dateFrom", required = false) Long dateFrom,
      @RequestParam(value = "dateTo", required = false) Long dateTo,
      @RequestParam(value = "activitySubCategoryId", required = false) String activitySubCategoryId,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeSixFilterMapper mapper = new ActivityTypeSixFilterMapper();
    mapper.id = id;
    mapper.dateFrom = dateFrom;
    mapper.dateTo = dateTo;
    mapper.activitySubCategoryId = activitySubCategoryId;
    mapper.userId = userId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeSixTO> toList = new ArrayList<>();
    activityTypeSixService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeSixDomain.convert(entity, new ActivityTypeSixTO())));
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
  public ActivityTypeSixTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeSix entity = activityTypeSixService.getByPK(id);
    return entity != null ? ActivityTypeSixDomain.convert(entity, new ActivityTypeSixTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeSixTO entityTO) {
    activityTypeSixService.post(ActivityTypeSixDomain.convert(entityTO, new ActivityTypeSix()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeSixTO entityTO) {
    activityTypeSixService.update(ActivityTypeSixDomain.convert(entityTO, new ActivityTypeSix()));
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
    activityTypeSixService.delete(id);
  }
}
