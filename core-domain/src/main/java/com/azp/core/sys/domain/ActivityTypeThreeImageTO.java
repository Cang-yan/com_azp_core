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
public class ActivityTypeThreeImageTO implements Serializable {
  /**
   * 活动类型3图片ID */
  private String id;

  /**
   * 类型3活动ID */
  private String activityTypeThreeId;

  /**
   * 图片url */
  private String url;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
