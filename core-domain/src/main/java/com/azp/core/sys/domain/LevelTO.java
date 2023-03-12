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
public class LevelTO implements Serializable {
  /**
   * 等级ID */
  private String id;

  /**
   * 等级名称 */
  private String name;

  /**
   * 最小积分数 */
  private Integer minPoint;

  /**
   * 最大积分数 */
  private Integer maxPoint;

  /**
   * 标识 */
  private String tag;

  /**
   * 索引 */
  private Integer idx;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
