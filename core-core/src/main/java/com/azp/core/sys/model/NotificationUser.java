package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.NotificationUserDO;
import com.azp.core.sys.domain.NotificationUserTO;
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
    value = {"data","domain","filter","post","update","simple","detail",},
    name = {"NotificationUserData","NotificationUserDomain","NotificationUserFilterMapper","NotificationUserPostMapper","NotificationUserUpdateMapper","NotificationUserSimpleMapper","NotificationUserDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","filter","detail","detail",},
    name = {"page","rows","orderBy","typeIn","statusIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","notification","user",},
    type = {Long.class,Integer.class,String.class,Integer.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","null","String, Object","String, Object",},
    list = {false,false,true,true,true,false,false,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {NotificationUserDO.class,NotificationUserTO.class,}
)
public class NotificationUser {
  /**
   * 通知用户状态ID */
  @MapperIgnore({"post",})
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
  @MapperIgnore({"post", "update","filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date gmtUpdate;

  /**
   * 创建时间 */
  @MapperIgnore({"post", "update","filter", })
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
