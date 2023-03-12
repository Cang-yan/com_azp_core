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
public class ActivityTypeSixTO implements Serializable {
  /**
   * 类型6活动ID */
  private String id;

  /**
   * 积分 */
  private Integer point;

  /**
   * 日期 */
  private Date date;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 用户ID */
  private String userId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
