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
public class ActivityTypeFiveTO implements Serializable {
  /**
   * 类型5活动ID */
  private String id;

  /**
   * 用户ID */
  private String userId;

  /**
   * 得分 */
  private Integer score;

  /**
   * 活动小类ID */
  private String activitySubCategory;

  /**
   * 积分 */
  private Integer point;

  /**
   * 时间 */
  private String duration;

  /**
   * 类型 */
  private Integer type;

  /**
   * 年度 */
  private String year;

  /**
   * 月度 */
  private String month;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
