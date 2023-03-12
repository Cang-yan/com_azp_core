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
public class UseInfoTO implements Serializable {
  /**
   * 用户信息ID */
  private String id;

  /**
   * 积分数量 */
  private Integer pointNumber;

  /**
   * 头像 */
  private String head;

  /**
   * 积分等级 */
  private String levelId;

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
