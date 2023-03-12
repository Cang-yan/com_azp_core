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
public class ActivityTypeFourUserTO implements Serializable {
  /**
   * 类型4活动-人ID */
  private String id;

  /**
   * 类型4活动ID */
  private String activityTypeFourId;

  /**
   * 用户（队员） */
  private String userId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 位次 */
  private Integer place;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
