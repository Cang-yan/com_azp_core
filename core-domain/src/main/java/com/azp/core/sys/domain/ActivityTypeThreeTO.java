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
public class ActivityTypeThreeTO implements Serializable {
  /**
   * 类型3活动ID */
  private String id;

  /**
   * 名称 */
  private String name;

  /**
   * 开始时间 */
  private Date beginDate;

  /**
   * 结束日期 */
  private Date endDate;

  /**
   * 编号 */
  private String serial;

  /**
   * 品牌 */
  private String brand;

  /**
   * 详情描述 */
  private String description;

  /**
   * 积分 */
  private Integer point;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 是否优秀 */
  private Integer isOutstanding;

  /**
   * 活动创建用户 */
  private String createUserId;

  /**
   * 审核意见 */
  private String reviewIdea;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
