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
public class PointExchangeTO implements Serializable {
  /**
   * 积分兑换ID */
  private String id;

  /**
   * 商品ID */
  private String productId;

  /**
   * 积分数量 */
  private Integer pointNum;

  /**
   * 用户ID */
  private String userId;

  /**
   * 兑换数量 */
  private Integer exchangeNum;

  /**
   * 更新时间 */
  private Date gmtUpdate;

  /**
   * 创建时间 */
  private Date gmtCreate;
}
