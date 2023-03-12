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
public class ActivitySubCategoryTO implements Serializable {
  /**
   * 活动小类ID */
  private String id;

  /**
   * 大类ID */
  private String activityCategoryId;

  /**
   * 名称 */
  private String name;

  /**
   * 规则说明 */
  private String rule;

  /**
   * 获得积分数（只有类型3需要） */
  private Integer point;

  /**
   * 是否置顶 */
  private Integer onTop;

  /**
   * 置顶时间 */
  private Date onTopDate;

  /**
   * 状态 */
  private Integer status;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
