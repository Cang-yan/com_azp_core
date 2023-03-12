package com.azp.core.sys.facade;

import com.azp.core.sys.domain.ActivityTypeThreeImageFacade;
import com.azp.core.sys.domain.ActivityTypeThreeImageTO;
import com.azp.core.sys.model.ActivityTypeThreeImage;
import com.azp.core.sys.model.ActivityTypeThreeImageDomain;
import com.azp.core.sys.model.ActivityTypeThreeImageFilterMapper;
import com.azp.core.sys.service.ActivityTypeThreeImageService;
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
@RequestMapping("facade/sys/activity/type/three/image")
public class ActivityTypeThreeImageFacadeImpl implements ActivityTypeThreeImageFacade {
  @Autowired
  private ActivityTypeThreeImageService activityTypeThreeImageService;

  @RequestMapping(
      value = "filter",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeThreeImageTO> getFilterList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activityTypeThreeId", required = false) String activityTypeThreeId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeThreeImageFilterMapper mapper = new ActivityTypeThreeImageFilterMapper();
    mapper.id = id;
    mapper.activityTypeThreeId = activityTypeThreeId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeThreeImageTO> toList = new ArrayList<>();
    activityTypeThreeImageService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeThreeImageDomain.convert(entity, new ActivityTypeThreeImageTO())));
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
  public List<ActivityTypeThreeImageTO> getFilterDetailList(@RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "activityTypeThreeId", required = false) String activityTypeThreeId,
      @RequestParam(value = "gmtUpdateFrom", required = false) Long gmtUpdateFrom,
      @RequestParam(value = "gmtUpdateTo", required = false) Long gmtUpdateTo,
      @RequestParam(value = "gmtCreateFrom", required = false) Long gmtCreateFrom,
      @RequestParam(value = "gmtCreateTo", required = false) Long gmtCreateTo,
      @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
      @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
      @RequestParam(value = "orderBy", required = false) ArrayList<String> orderBy) {
    ActivityTypeThreeImageFilterMapper mapper = new ActivityTypeThreeImageFilterMapper();
    mapper.id = id;
    mapper.activityTypeThreeId = activityTypeThreeId;
    mapper.gmtUpdateFrom = gmtUpdateFrom;
    mapper.gmtUpdateTo = gmtUpdateTo;
    mapper.gmtCreateFrom = gmtCreateFrom;
    mapper.gmtCreateTo = gmtCreateTo;
    mapper.page = page;
    mapper.rows = rows;
    mapper.orderBy = orderBy;
    List<ActivityTypeThreeImageTO> toList = new ArrayList<>();
    activityTypeThreeImageService.getListByFilter(mapper).forEach(entity -> toList.add(ActivityTypeThreeImageDomain.convert(entity, new ActivityTypeThreeImageTO())));
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
  public ActivityTypeThreeImageTO getSingleByPK(@RequestParam(value = "id", required = true) String id) {
    ActivityTypeThreeImage entity = activityTypeThreeImageService.getByPK(id);
    return entity != null ? ActivityTypeThreeImageDomain.convert(entity, new ActivityTypeThreeImageTO()) : null;
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void post(@RequestBody ActivityTypeThreeImageTO entityTO) {
    activityTypeThreeImageService.post(ActivityTypeThreeImageDomain.convert(entityTO, new ActivityTypeThreeImage()));
  }

  @RequestMapping(
      value = "",
      method = RequestMethod.PATCH
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @Override
  public void patch(@RequestBody ActivityTypeThreeImageTO entityTO) {
    activityTypeThreeImageService.update(ActivityTypeThreeImageDomain.convert(entityTO, new ActivityTypeThreeImage()));
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
    activityTypeThreeImageService.delete(id);
  }

  @RequestMapping(
      value = "filter/relation/ActivityTypeThreeId",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  @Override
  public List<ActivityTypeThreeImageTO> getListByRelatedActivityTypeThreeId(@RequestBody ArrayList<String> activityTypeThreeIdList) {
    List<ActivityTypeThreeImageTO> toList = new ArrayList<>();
    activityTypeThreeImageService.getListByRelatedActivityTypeThreeId(activityTypeThreeIdList).forEach(entity -> toList.add(ActivityTypeThreeImageDomain.convert(entity, new ActivityTypeThreeImageTO())));
    return toList;
  }
}
