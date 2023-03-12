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
public class ActivityTypeFourTO implements Serializable {
  /**
   * 类型4活动ID */
  private String id;

  /**
   * 名称 */
  private String name;

  /**
   * 组队日期 */
  private Date groupDate;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 积分奖励 */
  private Integer point;

  /**
   * 排名 */
  private Integer rank;

  /**
   * 队伍积分 */
  private Integer groupPoint;

  /**
   * 期数 */
  private Integer periodsNumber;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
