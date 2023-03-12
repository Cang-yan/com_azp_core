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
public class GroupTO implements Serializable {
  /**
   * 组别ID */
  private String id;

  /**
   * 组别名称 */
  private String groupName;

  /**
   * 组别编号 */
  private String code;

  /**
   * 部门ID */
  private String departmentId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
