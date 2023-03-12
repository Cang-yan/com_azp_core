package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.NotificationDO;
import com.azp.core.sys.domain.NotificationTO;
import java.lang.Integer;
import java.lang.Long;
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
@Mapper(
    value = {"data","domain","filter","post","update","simple","detail",},
    name = {"NotificationData","NotificationDomain","NotificationFilterMapper","NotificationPostMapper","NotificationUpdateMapper","NotificationSimpleMapper","NotificationDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","filter","filter",},
    name = {"page","rows","orderBy","typeIn","templateIdIn","relationIdIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo",},
    type = {Long.class,Integer.class,String.class,Integer.class,String.class,String.class,Long.class,Long.class,Long.class,Long.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","null","null",},
    list = {false,false,true,true,true,true,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {NotificationDO.class,NotificationTO.class,}
)
public class Notification {
  /**
   * 通知ID */
  @MapperIgnore({"post",})
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
