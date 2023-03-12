package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivityTypeSixDO;
import com.azp.core.sys.domain.ActivityTypeSixTO;
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
    name = {"ActivityTypeSixData","ActivityTypeSixDomain","ActivityTypeSixFilterMapper","ActivityTypeSixPostMapper","ActivityTypeSixUpdateMapper","ActivityTypeSixSimpleMapper","ActivityTypeSixDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","filter","detail","detail",},
    name = {"page","rows","orderBy","dateFrom","dateTo","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","activitySubCategory","user",},
    type = {Long.class,Integer.class,String.class,Long.class,Long.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","null","String, Object","String, Object",},
    list = {false,false,true,false,false,false,false,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivityTypeSixDO.class,ActivityTypeSixTO.class,}
)
public class ActivityTypeSix {
  /**
   * 类型6活动ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 积分 */
  private Integer point;

  /**
   * 日期 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date date;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 用户ID */
  private String userId;

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
