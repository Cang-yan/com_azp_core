package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivityCategoryDO;
import com.azp.core.sys.domain.ActivityCategoryTO;
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
    name = {"ActivityCategoryData","ActivityCategoryDomain","ActivityCategoryFilterMapper","ActivityCategoryPostMapper","ActivityCategoryUpdateMapper","ActivityCategorySimpleMapper","ActivityCategoryDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","detail",},
    name = {"page","rows","orderBy","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","activitySubCategoryList",},
    type = {Long.class,Integer.class,String.class,Long.class,Long.class,Long.class,Long.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","String, Object",},
    list = {false,false,true,false,false,false,false,true,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivityCategoryDO.class,ActivityCategoryTO.class,}
)
public class ActivityCategory {
  /**
   * 活动大类ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 大类名称 */
  private String name;

  /**
   * 自定义文本 */
  private String diyText;

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
