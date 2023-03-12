package com.azp.core.sys.domain;

import java.io.Serializable;
import java.lang.Integer;
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
public class PointStoreTO implements Serializable {
  /**
   * 积分商城ID */
  private String id;

  /**
   * 商品类别 */
  private Integer productType;

  /**
   * 商品名称 */
  private String productName;

  /**
   * 商品编号 */
  private String productSerial;

  /**
   * 所需积分数 */
  private Integer pointNumber;

  /**
   * 图片 */
  private String image;

  /**
   * 兑换说明 */
  private String description;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
