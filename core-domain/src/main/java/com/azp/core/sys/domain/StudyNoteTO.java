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
public class StudyNoteTO implements Serializable {
  /**
   * 学习心得ID */
  private String id;

  /**
   * 用户ID */
  private String userId;

  /**
   * 内容 */
  private String content;

  /**
   * 类型2活动用户ID */
  private String activityTypeTwoUserId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
