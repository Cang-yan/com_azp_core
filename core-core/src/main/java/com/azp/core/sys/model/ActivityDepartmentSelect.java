package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivityDepartmentSelectDO;
import com.azp.core.sys.domain.ActivityDepartmentSelectTO;
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
    name = {"ActivityDepartmentSelectData","ActivityDepartmentSelectDomain","ActivityDepartmentSelectFilterMapper","ActivityDepartmentSelectPostMapper","ActivityDepartmentSelectUpdateMapper","ActivityDepartmentSelectSimpleMapper","ActivityDepartmentSelectDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","detail",},
    name = {"page","rows","orderBy","typeIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","department",},
    type = {Long.class,Integer.class,String.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","String, Object",},
    list = {false,false,true,true,false,false,false,false,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivityDepartmentSelectDO.class,ActivityDepartmentSelectTO.class,}
)
public class ActivityDepartmentSelect {
  /**
   * 活动部门选择ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 活动关联ID */
  private String relationId;

  /**
   * 部门ID */
  private String departmentId;

  /**
   * 类型 */
  private Integer type;

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
