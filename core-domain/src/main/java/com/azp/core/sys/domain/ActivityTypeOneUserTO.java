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
public class ActivityTypeOneUserTO implements Serializable {
  /**
   * 类型1活动-用户ID */
  private String id;

  /**
   * 状态 */
  private Integer status;

  /**
   * 开始时间 */
  private Date beginDate;

  /**
   * 完成时间 */
  private Date endDate;

  /**
   * 用户ID */
  private String userId;

  /**
   * 类型1活动ID */
  private String activityTypeOneId;

  /**
   * 积分变更数 */
  private Integer point;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
