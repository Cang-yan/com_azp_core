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
public class ActivityTypeFourPeriodsTO implements Serializable {
  /**
   * 活动4-期数ID */
  private String id;

  /**
   * 期数 */
  private Integer periodsNumber;

  /**
   * 开始时间 */
  private Date beginDate;

  /**
   * 结束时间 */
  private Date endDate;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
