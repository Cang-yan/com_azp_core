package com.azp.core.sys.model;

import lombok.*;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/10 4:45 下午
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoExtends {

    private Level level;

    private Integer totalPoint = 0;

    private Integer exchangePoint = 0;

    private Integer totalCount = 0;

    private Integer pointRank = 0;

    private Integer countRank = 0;
}
