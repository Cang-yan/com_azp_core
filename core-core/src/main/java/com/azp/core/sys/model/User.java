package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.UserDO;
import com.azp.core.sys.domain.UserTO;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.Map;
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
@Mapper(
    value = {"data","domain","filter","post","update","simple","detail","return",},
    name = {"UserData","UserDomain","UserFilterMapper","UserPostMapper","UserUpdateMapper","UserSimpleMapper","UserDetailMapper","UserReturnMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","detail","detail","detail",},
    name = {"page","rows","orderBy","userCodeIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","department","group","useInfo",},
    type = {Long.class,Integer.class,String.class,String.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","String, Object","String, Object","String, Object",},
    list = {false,false,true,true,false,false,false,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {UserDO.class,UserTO.class,}
)
public class User {
  /**
   * 用户ID */
  @MapperIgnore({"post",})
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

  @MapperIgnore({"return",})
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
  @MapperIgnore({"post", "update","filter","return", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date gmtUpdate;

  /**
   * 创建时间 */
  @MapperIgnore({"post", "update","filter","return", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date gmtCreate;

  Long modifyTime(Date date) {
    if (date == null) return null;
    return date.getTime();
  }

  Date recoverTime(Long time) {
    if (time == null) return null;
    return new Date(time);
  }
}
