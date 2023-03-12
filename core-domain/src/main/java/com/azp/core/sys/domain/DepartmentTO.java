package com.azp.core.sys.domain;

import java.io.Serializable;
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
public class DepartmentTO implements Serializable {
  /**
   * 部门ID */
  private String id;

  /**
   * 部门积分管理ID */
  private String departmentPointId;

  /**
   * 名称 */
  private String name;

  /**
   * 部门编号 */
  private String code;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
