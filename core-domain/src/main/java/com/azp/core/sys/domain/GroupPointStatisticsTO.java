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
public class GroupPointStatisticsTO implements Serializable {
  /**
   * 组队积分统计ID */
  private String id;

  /**
   * 积分数量 */
  private Integer pointNum;

  /**
   * 队伍ID */
  private String groupId;

  /**
   * 日期 */
  private Date date;

  /**
   * 参与活动次数 */
  private Integer count;

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
