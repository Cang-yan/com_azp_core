package com.azp.core.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import cc.eamon.open.mapping.mapper.MapperExtra;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import com.azp.core.sys.dataobject.ActivityTypeThreeDO;
import com.azp.core.sys.domain.ActivityTypeThreeTO;
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
    name = {"ActivityTypeThreeData","ActivityTypeThreeDomain","ActivityTypeThreeFilterMapper","ActivityTypeThreePostMapper","ActivityTypeThreeUpdateMapper","ActivityTypeThreeSimpleMapper","ActivityTypeThreeDetailMapper",}
)
@MapperExtra(
    value = {"filter","filter","filter","filter","filter","filter","filter","filter","detail","detail","detail",},
    name = {"page","rows","orderBy","statusIn","gmtUpdateFrom","gmtUpdateTo","gmtCreateFrom","gmtCreateTo","user","activityTypeThreeImageList","activitySubCategory",},
    type = {Long.class,Integer.class,String.class,Integer.class,Long.class,Long.class,Long.class,Long.class,Map.class,Map.class,Map.class,},
    typeArgs = {"null","null","null","null","null","null","null","null","String, Object","String, Object","String, Object",},
    list = {false,false,true,true,false,false,false,false,false,true,false,}
)
@MapperConvert(
    value = {"data","domain",},
    type = {ActivityTypeThreeDO.class,ActivityTypeThreeTO.class,}
)
public class ActivityTypeThree {
  /**
   * 类型3活动ID */
  @MapperIgnore({"post",})
  private String id;

  /**
   * 名称 */
  private String name;

  /**
   * 开始时间 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date beginDate;

  /**
   * 结束日期 */
  @MapperIgnore({"filter", })
  @MapperModify(
      value = {"post", "update", "simple", "detail", "filter",},
      modify = {"modifyTime", "modifyTime", "modifyTime", "modifyTime", "modifyTime",},
      recover = {"recoverTime", "recoverTime", "recoverTime", "recoverTime", "recoverTime",}
  )
  private Date endDate;

  /**
   * 编号 */
  private String serial;

  /**
   * 品牌 */
  private String brand;

  /**
   * 详情描述 */
  private String description;

  /**
   * 积分 */
  private Integer point;

  /**
   * 活动小类ID */
  private String activitySubCategoryId;

  /**
   * 状态 */
  private Integer status;

  /**
   * 是否优秀 */
  private Integer isOutstanding;

  /**
   * 活动创建用户 */
  private String createUserId;

  /**
   * 审核意见 */
  private String reviewIdea;

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
