package com.azp.core.sys.datafilter;

import com.azp.core.sys.dataobject.ActivityTypeFourDOExample;
import com.google.common.base.CaseFormat;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
public class ActivityTypeFourFilter {
  public static ActivityTypeFourDOExample initDOQueryFilter(Map<String, Object> filterMap) {
    ActivityTypeFourDOExample example = new ActivityTypeFourDOExample();
    if (filterMap.get("orderBy") != null) {
      List orderBy = (List) filterMap.get("orderBy");
      example.setOrderByClause(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, String.join(",", orderBy)));
    }
    else {
      example.setOrderByClause("gmt_create desc");
    }
    ActivityTypeFourDOExample.Criteria criteria = example.createCriteria();
    if (filterMap.get("id") != null) criteria.andIdEqualTo((String) filterMap.get("id"));
    if (filterMap.get("groupDateFrom") != null) criteria.andGroupDateGreaterThan(new Date((Long) filterMap.get("groupDateFrom")));
    if (filterMap.get("groupDateTo") != null) criteria.andGroupDateLessThan(new Date((Long) filterMap.get("groupDateTo")));
    if (filterMap.get("activitySubCategoryId") != null) criteria.andActivitySubCategoryIdEqualTo((String) filterMap.get("activitySubCategoryId"));
    if (filterMap.get("statusIn") != null) criteria.andStatusIn((List) filterMap.get("statusIn"));
    if (filterMap.get("rank") != null) criteria.andRankEqualTo((Integer) filterMap.get("rank"));
    if (filterMap.get("periodsNumber") != null) criteria.andPeriodsNumberEqualTo((Integer) filterMap.get("periodsNumber"));
    if (filterMap.get("gmtUpdateFrom") != null) criteria.andGmtUpdateGreaterThan(new Date((Long) filterMap.get("gmtUpdateFrom")));
    if (filterMap.get("gmtUpdateTo") != null) criteria.andGmtUpdateLessThan(new Date((Long) filterMap.get("gmtUpdateTo")));
    if (filterMap.get("gmtCreateFrom") != null) criteria.andGmtCreateGreaterThan(new Date((Long) filterMap.get("gmtCreateFrom")));
    if (filterMap.get("gmtCreateTo") != null) criteria.andGmtCreateLessThan(new Date((Long) filterMap.get("gmtCreateTo")));
    Long page = 0L;
    Integer rows = 0;
    if (filterMap.get("page") != null && filterMap.get("rows") != null) {
      page = (Long) filterMap.get("page");
      rows = (Integer) filterMap.get("rows");
    }
    if (page > 0) {
      Long offset = (page - 1L) * rows;
      example.setLimit(rows);
      example.setOffset(offset);
    }
    return example;
  }

  public static ActivityTypeFourDOExample initActivitySubCategoryIdQueryFilter(List<String> activitySubCategoryIdList) {
    ActivityTypeFourDOExample example = new ActivityTypeFourDOExample();
    example.setOrderByClause("gmt_create desc");
    ActivityTypeFourDOExample.Criteria criteria = example.createCriteria();
    if (activitySubCategoryIdList == null) activitySubCategoryIdList = new ArrayList<>();
    criteria.andActivitySubCategoryIdIn(activitySubCategoryIdList);
    return example;
  }

  public static ActivityTypeFourDOExample initIdQueryFilter(List<String> idList) {
    ActivityTypeFourDOExample example = new ActivityTypeFourDOExample();
    example.setOrderByClause("gmt_create desc");
    ActivityTypeFourDOExample.Criteria criteria = example.createCriteria();
    if (idList == null) idList = new ArrayList<>();
    criteria.andIdIn(idList);
    return example;
  }

  public static ActivityTypeFourDOExample initPeriodsNumberQueryFilter(List<Integer> periodsNumberList) {
    ActivityTypeFourDOExample example = new ActivityTypeFourDOExample();
    example.setOrderByClause("gmt_create desc");
    ActivityTypeFourDOExample.Criteria criteria = example.createCriteria();
    if (periodsNumberList == null) periodsNumberList = new ArrayList<>();
    criteria.andPeriodsNumberIn(periodsNumberList);
    return example;
  }
}
