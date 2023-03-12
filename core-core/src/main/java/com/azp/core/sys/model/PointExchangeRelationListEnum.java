package com.azp.core.sys.model;


import org.springframework.util.StringUtils;
/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/26 22:09
 */
public enum PointExchangeRelationListEnum {

    User_List("1",true),
    Department_List("2",true),
    Group_List("3",true)
    ;


    private String relationId;

    private boolean notNUll;


    PointExchangeRelationListEnum(String relationId, boolean notNUll) {
        this.relationId = relationId;
        this.notNUll = notNUll;
    }

    public static boolean getNameByRelationId(String relationId) {
        if (StringUtils.isEmpty(relationId)) return false;
        for (PointExchangeRelationListEnum value : values()) {
            if (value.relationId.equals(relationId)) {
                return value.notNUll;
            }
        }
        return false;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public boolean isNotNUll() {
        return notNUll;
    }

    public void setNotNUll(boolean notNUll) {
        this.notNUll = notNUll;
    }
}
