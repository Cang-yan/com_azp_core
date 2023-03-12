package com.azp.core.sys.model;

import org.springframework.util.StringUtils;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/18 2:16 下午
 **/
public enum ActivityTypeFiveRelationEnum {

    GOOD_EYE("1", "火眼金睛"),

    RAIN_AID("2", "雨中送援"),

    SKILL_EXCELLENCE("3", "技能突出奖"),

    SNOW_HEAT("4", "雪中送炭"),

    SPECIAL_TIME("5", "特殊时节奖"),

    MONTH("6", "月度优秀奖"),

    QUARTER("7", "季度优秀奖"),

    YEAR("8", "年度优秀奖"),
    ;

    private String relationId;

    private String name;

    ActivityTypeFiveRelationEnum(String relationId, String name) {
        this.relationId = relationId;
        this.name = name;
    }

    public static String getNameByRelationId(String relationId) {
        if (StringUtils.isEmpty(relationId)) return "";
        for (ActivityTypeFiveRelationEnum value : values()) {
            if (value.relationId.equals(relationId)) {
                return value.name;
            }
        }
        return "";
    }

    public String getRelationId() {
        return relationId;
    }

    public String getName() {
        return name;
    }
}