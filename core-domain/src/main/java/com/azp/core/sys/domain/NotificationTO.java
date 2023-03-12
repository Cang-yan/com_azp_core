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
public class NotificationTO implements Serializable {
  /**
   * 通知ID */
  private String id;

  /**
   * 内容 */
  private String content;

  /**
   * 标题 */
  private String title;

  /**
   * 类型 */
  private Integer type;

  /**
   * 模板ID */
  private String templateId;

  /**
   * 关联ID */
  private String relationId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
