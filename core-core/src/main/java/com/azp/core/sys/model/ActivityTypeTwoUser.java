package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivityTypeTwoUserDO;
import com.azp.core.sys.domain.ActivityTypeTwoUserTO;
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
    name = {"ActivityTypeTwoUserData","ActivityTypeTwoUserDomain","ActivityTypeTwoUserFilterMapper","ActivityTypeTwoUserPostMapper","ActivityTypeTwoUserUpdateMapper","ActivityTypeTwoUserSimpleMapper","ActivityTypeTwoUserDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","detail","detail","detail",},
    name = {"page","rows","orderBy","statusIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","user","activityTypeTwo","studyNote",},
    type = {Long.class,Integer.class,String.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","String, Object","String, Object","String, Object",},
    list = {false,false,true,true,false,false,false,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivityTypeTwoUserDO.class,ActivityTypeTwoUserTO.class,}
)
public class ActivityTypeTwoUser {
  /**
   * 类型2活动-用户ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 状态 */
  private Integer status;

  /**
   * 完成时间 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date endDate;

  /**
   * 积分 */
  private Integer point;

  /**
   * 报名时间 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date signDate;

  /**
   * 审核时间 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date reviewDate;

  /**
   * 用户ID */
  private String userId;

  /**
   * 类型2活动ID */
  private String activityTypeTwoId;

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
