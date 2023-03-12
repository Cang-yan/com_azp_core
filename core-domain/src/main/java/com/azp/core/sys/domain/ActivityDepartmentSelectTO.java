package com.azp.core.sys.domain;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDepartmentSelectTO implements Serializable {
  /**
   * 活动部门选择ID */
  private String id;

  /**
   * 活动关联ID */
  private String relationId;

  /**
   * 部门ID */
  private String departmentId;

  /**
   * 类型 */
  private Integer type;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
