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
public class ActivityTypeTwoTO implements Serializable {
  /**
   * 类型2活动ID */
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
   * 积分 */
  private Integer point;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 参与人数 */
  private Integer participantsNumber;

  /**
   * 限定人数 */
  private Integer limitNumber;

  /**
   * 部门 */
  private String departmentId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
