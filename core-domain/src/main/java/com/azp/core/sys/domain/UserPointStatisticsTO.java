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
public class UserPointStatisticsTO implements Serializable {
  /**
   * 员工积分统计ID */
  private String id;

  /**
   * 积分数量 */
  private Integer number;

  /**
   * 日期 */
  private Date date;

  /**
   * 部门名称 */
  private String departmentName;

  /**
   * 用户ID */
  private String userId;

  /**
   * 参与活动次数 */
  private Integer count;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
