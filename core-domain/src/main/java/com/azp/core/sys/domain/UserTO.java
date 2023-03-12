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
public class UserTO implements Serializable {
  /**
   * 用户ID */
  private String id;

  /**
   * 员工编号 */
  private String userCode;

  /**
   * 姓名 */
  private String name;

  /**
   * 账号 */
  private String account;

  /**
   * 密码 */
  private String password;

  /**
   * 部门ID */
  private String departmentId;

  /**
   * 入职时间 */
  private String workDate;

  /**
   * 组别ID */
  private String groupId;

  /**
   * 用户信息ID */
  private String userInfoId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
