package com.azp.core.sys.datafilter;

import com.azp.core.sys.dataobject.UserDOExample;
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
public class UserFilter {
  public static UserDOExample initDOQueryFilter(Map<String, Object> filterMap) {
    UserDOExample example = new UserDOExample();
    if (filterMap.get("orderBy") != null) {
      List orderBy = (List) filterMap.get("orderBy");
      example.setOrderByClause(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, String.join(",", orderBy)));
    }
    else {
      example.setOrderByClause("gmt_create desc");
    }
    UserDOExample.Criteria criteria = example.createCriteria();
    if (filterMap.get("id") != null) criteria.andIdEqualTo((String) filterMap.get("id"));
    if (filterMap.get("userCodeIn") != null) criteria.andUserCodeIn((List) filterMap.get("userCodeIn"));
    if (filterMap.get("name") != null) criteria.andNameEqualTo((String) filterMap.get("name"));
    if (filterMap.get("account") != null) criteria.andAccountEqualTo((String) filterMap.get("account"));
    if (filterMap.get("departmentId") != null) criteria.andDepartmentIdEqualTo((String) filterMap.get("departmentId"));
    if (filterMap.get("groupId") != null) criteria.andGroupIdEqualTo((String) filterMap.get("groupId"));
    if (filterMap.get("userInfoId") != null) criteria.andUserInfoIdEqualTo((String) filterMap.get("userInfoId"));
    if (filterMap.get("status") != null) criteria.andStatusEqualTo((Integer) filterMap.get("status"));
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

  public static UserDOExample initIdQueryFilter(List<String> idList) {
    UserDOExample example = new UserDOExample();
    example.setOrderByClause("gmt_create desc");
    UserDOExample.Criteria criteria = example.createCriteria();
    if (idList == null) idList = new ArrayList<>();
    criteria.andIdIn(idList);
    return example;
  }

  public static UserDOExample initDepartmentIdQueryFilter(List<String> departmentIdList) {
    UserDOExample example = new UserDOExample();
    example.setOrderByClause("gmt_create desc");
    UserDOExample.Criteria criteria = example.createCriteria();
    if (departmentIdList == null) departmentIdList = new ArrayList<>();
    criteria.andDepartmentIdIn(departmentIdList);
    return example;
  }
}
