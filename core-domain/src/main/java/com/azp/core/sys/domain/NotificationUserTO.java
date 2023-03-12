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
public class NotificationUserTO implements Serializable {
  /**
   * 通知用户状态ID */
  private String id;

  /**
   * 通知ID */
  private String notificationId;

  /**
   * 发送类型 */
  private Integer type;

  /**
   * 用户ID */
  private String userId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 发送人ID */
  private String senderId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
