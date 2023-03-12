package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivityTypeFourUserDO;
import com.azp.core.sys.domain.ActivityTypeFourUserTO;
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
    name = {"ActivityTypeFourUserData","ActivityTypeFourUserDomain","ActivityTypeFourUserFilterMapper","ActivityTypeFourUserPostMapper","ActivityTypeFourUserUpdateMapper","ActivityTypeFourUserSimpleMapper","ActivityTypeFourUserDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","detail","detail","detail",},
    name = {"page","rows","orderBy","statusIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","activityTypeFour","user","activityTypeFourUserPointList",},
    type = {Long.class,Integer.class,String.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","String, Object","String, Object","String, Object",},
    list = {false,false,true,true,false,false,false,false,false,false,true,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivityTypeFourUserDO.class,ActivityTypeFourUserTO.class,}
)
public class ActivityTypeFourUser {
  /**
   * 类型4活动-人ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 类型4活动ID */
  private String activityTypeFourId;

  /**
   * 用户（队员） */
  private String userId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 位次 */
  private Integer place;

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
