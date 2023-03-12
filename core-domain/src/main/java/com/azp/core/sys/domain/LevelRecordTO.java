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
public class LevelRecordTO implements Serializable {
  /**
   * 等级排行ID */
  private String id;

  /**
   * 等级类型ID */
  private String levelId;

  /**
   * 人数 */
  private Integer personNumber;

  /**
   * 作用范围 */
  private String roleType;

  /**
   * 部门ID */
  private String departmentId;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
