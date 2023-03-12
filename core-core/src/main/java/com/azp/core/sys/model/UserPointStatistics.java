package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.UserPointStatisticsDO;
import com.azp.core.sys.domain.UserPointStatisticsTO;
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
    name = {"UserPointStatisticsData","UserPointStatisticsDomain","UserPointStatisticsFilterMapper","UserPointStatisticsPostMapper","UserPointStatisticsUpdateMapper","UserPointStatisticsSimpleMapper","UserPointStatisticsDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","filter","filter","filter","filter","filter","detail",},
    name = {"page","rows","orderBy","numberFrom","numberTo","dateFrom","dateTo","countFrom","countTo","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","user",},
    type = {Long.class,Integer.class,String.class,Integer.class,Integer.class,Long.class,Long.class,Integer.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","null","null","null","null","null","String, Object",},
    list = {false,false,true,false,false,false,false,false,false,false,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {UserPointStatisticsDO.class,UserPointStatisticsTO.class,}
)
public class UserPointStatistics {
  /**
   * 员工积分统计ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 积分数量 */
  private Integer number;

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
   * 部门名称 */
  private String departmentName;

  /**
   * 用户ID */
  private String userId;

  /**
   * 参与活动次数 */
  private Integer count;

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
