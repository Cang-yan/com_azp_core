package com.azp.core.sys.datafilter;

import com.azp.core.sys.dataobject.MonthExcellentDOExample;
import com.google.common.base.CaseFormat;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
public class MonthExcellentFilter {
  public static MonthExcellentDOExample initDOQueryFilter(Map<String, Object> filterMap) {
    MonthExcellentDOExample example = new MonthExcellentDOExample();
    if (filterMap.get("orderBy") != null) {
      List orderBy = (List) filterMap.get("orderBy");
      example.setOrderByClause(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, String.join(",", orderBy)));
    }
    else {
      example.setOrderByClause("gmt_create desc");
    }
    MonthExcellentDOExample.Criteria criteria = example.createCriteria();
    if (filterMap.get("id") != null) criteria.andIdEqualTo((String) filterMap.get("id"));
    if (filterMap.get("year") != null) criteria.andYearEqualTo((String) filterMap.get("year"));
    if (filterMap.get("month") != null) criteria.andMonthEqualTo((String) filterMap.get("month"));
    if (filterMap.get("department") != null) criteria.andDepartmentEqualTo((String) filterMap.get("department"));
    if (filterMap.get("group") != null) criteria.andGroupEqualTo((String) filterMap.get("group"));
    if (filterMap.get("userCode") != null) criteria.andUserCodeEqualTo((String) filterMap.get("userCode"));
    if (filterMap.get("type") != null) criteria.andTypeLike("%" + (String) filterMap.get("type") + "%");
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
}
