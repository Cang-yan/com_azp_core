package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivitySubCategoryDO;
import com.azp.core.sys.domain.ActivitySubCategoryTO;
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
    name = {"ActivitySubCategoryData","ActivitySubCategoryDomain","ActivitySubCategoryFilterMapper","ActivitySubCategoryPostMapper","ActivitySubCategoryUpdateMapper","ActivitySubCategorySimpleMapper","ActivitySubCategoryDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","filter","detail","detail","detail","detail",},
    name = {"page","rows","orderBy","activityCategoryIdIn","statusIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","activityTypeOneList","activityTypeTwoList","activityTypeFourList","activityTypeFiveList",},
    type = {Long.class,Integer.class,String.class,String.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class,Map.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","null","String, Object","String, Object","String, Object","String, Object",},
    list = {false,false,true,true,true,false,false,false,false,true,true,true,true,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivitySubCategoryDO.class,ActivitySubCategoryTO.class,}
)
public class ActivitySubCategory {
  /**
   * 活动小类ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 大类ID */
  private String activityCategoryId;

  /**
   * 名称 */
  private String name;

  /**
   * 规则说明 */
  private String rule;

  /**
   * 获得积分数（只有类型3需要） */
  private Integer point;

  /**
   * 是否置顶 */
  private Integer onTop;

  /**
   * 置顶时间 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date onTopDate;

  /**
   * 状态 */
  private Integer status;

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
