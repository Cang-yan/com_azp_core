package com.azp.core.sys.domain;

import java.io.Serializable;
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
public class LoginRecordTO implements Serializable {
  /**
   * 登录记录ID */
  private String id;

  /**
   * 用户ID */
  private String userId;

  /**
   * 时间 */
  private Date time;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
