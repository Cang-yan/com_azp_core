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
public class ActivityTypeFourUserPointTO implements Serializable {
  /**
   * 类型4活动-用户-积分ID */
  private String id;

  /**
   * 积分类型 */
  private Integer pointType;

  /**
   * 积分 */
  private Integer point;

  /**
   * 类型4-人管理ID */
  private String activityTypeFourUserId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
