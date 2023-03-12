package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivityTypeFourDO;
import com.azp.core.sys.domain.ActivityTypeFourTO;
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
    value = {"data","domain","filter","post","update","simple","detail","team",},
    name = {"ActivityTypeFourData","ActivityTypeFourDomain","ActivityTypeFourFilterMapper","ActivityTypeFourPostMapper","ActivityTypeFourUpdateMapper","ActivityTypeFourSimpleMapper","ActivityTypeFourDetailMapper","ActivityTypeFourTeamMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","filter","filter","detail","detail","team"},
    name = {"page","rows","orderBy","groupDateFrom","groupDateTo","statusIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","activityTypeFourUserList","activityTypeFourPeriods","userList"},
    type = {Long.class,Integer.class,String.class,Long.class,Long.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class, Map.class},
    typeArgs = {"null","null","null","null","null","null","null","null","null","null","String, Object","String, Object","String, Object"},
    list = {false,false,true,false,false,true,false,false,false,false,true,false,true}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivityTypeFourDO.class,ActivityTypeFourTO.class,}
)
public class ActivityTypeFour {
  /**
   * 类型4活动ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 名称 */
  private String name;

  /**
   * 组队日期 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date groupDate;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 积分奖励 */
  private Integer point;

  /**
   * 排名 */
  private Integer rank;

  /**
   * 队伍积分 */
  private Integer groupPoint;

  /**
   * 期数 */
  private Integer periodsNumber;

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
