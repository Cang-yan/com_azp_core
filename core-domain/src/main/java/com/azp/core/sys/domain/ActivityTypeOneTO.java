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
public class ActivityTypeOneTO implements Serializable {
  /**
   * 类型1活动ID */
  private String id;

  /**
   * 名称 */
  private String name;

  /**
   * 开始日期 */
  private Date beginDate;

  /**
   * 结束日期 */
  private Date endDate;

  /**
   * 展示图 */
  private String image;

  /**
   * 详情描述 */
  private String description;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 参加人数 */
  private Integer participantsNumber;

  /**
   * 获得积分 */
  private Integer getPoint;

  /**
   * 扣减积分 */
  private Integer dePoint;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
