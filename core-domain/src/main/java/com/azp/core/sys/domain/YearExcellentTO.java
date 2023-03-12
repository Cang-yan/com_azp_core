package com.azp.core.sys.domain;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.math.BigDecimal;
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
public class YearExcellentTO implements Serializable {
  /**
   * 年度优秀奖ID */
  private String id;

  /**
   * 年度 */
  private String year;

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
  private String name;

  /**
   * 类型 */
  private String type;

  /**
   * 得分 */
  private BigDecimal score;

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
