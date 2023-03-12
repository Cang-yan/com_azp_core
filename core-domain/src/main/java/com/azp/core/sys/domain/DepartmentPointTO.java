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
public class DepartmentPointTO implements Serializable {
  /**
   * 部门积分ID */
  private String id;

  /**
   * 积分余额 */
  private Integer pointLegacy;

  /**
   * 部门ID */
  private String departmentId;

  /**
   * 积分限额 */
  private Integer pointLimit;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
