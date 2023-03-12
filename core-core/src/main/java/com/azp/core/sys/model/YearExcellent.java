package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.YearExcellentDO;
import com.azp.core.sys.domain.YearExcellentTO;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
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
    name = {"YearExcellentData","YearExcellentDomain","YearExcellentFilterMapper","YearExcellentPostMapper","YearExcellentUpdateMapper","YearExcellentSimpleMapper","YearExcellentDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter",},
    name = {"page","rows","orderBy","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo",},
    type = {Long.class,Integer.class,String.class,Long.class,Long.class,Long.class,Long.class,},
    typeArgs = {"null","null","null","null","null","null","null",},
    list = {false,false,true,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {YearExcellentDO.class,YearExcellentTO.class,}
)
public class YearExcellent {
  /**
   * 年度优秀奖ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 年度 */
  private String year;

  /**
   * 部门 */
  private String department;

  /**
   * 组别 */
  private String group;

  /**
   * 工号 */
  private String userCode;

  /**
   * 姓名 */
  private String name;

  /**
   * 类型 */
  private String type;

  /**
   * 得分 */
  private BigDecimal score;

  /**
   * 积分 */
  private Integer point;

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
