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
public class AwardSkillExcellenceTO implements Serializable {
  /**
   * 技能突出奖ID */
  private String id;

  /**
   * 年度 */
  private String year;

  /**
   * 月别 */
  private String month;

  /**
   * 部门 */
  private String department;

  /**
   * 组别 */
  private String group;

  /**
   * 工号 */
  private String userCode;

  /**
   * 姓名 */
  private String userName;

  /**
   * 类型 */
  private String type;

  /**
   * 奖项名称 */
  private String awardName;

  /**
   * 获奖原因 */
  private String awardReason;

  /**
   * 积分 */
  private Integer point;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
