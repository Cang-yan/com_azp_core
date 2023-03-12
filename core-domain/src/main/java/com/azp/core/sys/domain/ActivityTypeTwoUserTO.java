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
public class ActivityTypeTwoUserTO implements Serializable {
  /**
   * 类型2活动-用户ID */
  private String id;

  /**
   * 状态 */
  private Integer status;

  /**
   * 完成时间 */
  private Date endDate;

  /**
   * 积分 */
  private Integer point;

  /**
   * 报名时间 */
  private Date signDate;

  /**
   * 审核时间 */
  private Date reviewDate;

  /**
   * 用户ID */
  private String userId;

  /**
   * 类型2活动ID */
  private String activityTypeTwoId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
