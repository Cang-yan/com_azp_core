package com.azp.core.sys.web.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ActivityTypeThreeExtends {
    private ArrayList<String> departments;

    private String activitySubCategoryId;

    //案例分享：type6 术业专攻：type7
    private Integer type;
}
