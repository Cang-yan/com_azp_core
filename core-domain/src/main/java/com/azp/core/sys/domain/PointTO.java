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
public class PointTO implements Serializable {
  /**
   * 积分ID */
  private String id;

  /**
   * 积分类型 */
  private Integer type;

  /**
   * 用户ID */
  private String userId;

  /**
   * 积分数量 */
  private Integer pointNumber;

  /**
   * 积分来源ID */
  private String relationId;

  /**
   * 积分获取时间 */
  private Date getTime;

  /**
   * 积分标题 */
  private String title;

  /**
   * 模板ID */
  private String templateId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
