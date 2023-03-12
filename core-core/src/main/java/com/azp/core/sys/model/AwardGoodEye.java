package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.AwardGoodEyeDO;
import com.azp.core.sys.domain.AwardGoodEyeTO;
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
    name = {"AwardGoodEyeData","AwardGoodEyeDomain","AwardGoodEyeFilterMapper","AwardGoodEyePostMapper","AwardGoodEyeUpdateMapper","AwardGoodEyeSimpleMapper","AwardGoodEyeDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter",},
    name = {"page","rows","orderBy","quarterIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo",},
    type = {Long.class,Integer.class,String.class,String.class,Long.class,Long.class,Long.class,Long.class,},
    typeArgs = {"null","null","null","null","null","null","null","null",},
    list = {false,false,true,true,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {AwardGoodEyeDO.class,AwardGoodEyeTO.class,}
)
public class AwardGoodEye {
  /**
   * 火眼金睛ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 年度 */
  private String year;

  /**
   * 季度 */
  private String quarter;

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
  private String userName;

  /**
   * 奖项名称 */
  private String awardName;

  /**
   * 获奖原因 */
  private String awardReason;

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
