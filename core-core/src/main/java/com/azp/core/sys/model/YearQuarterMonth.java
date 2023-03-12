package com.azp.core.sys.model;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/1/29 5:49 下午
 **/
@Data
public class YearQuarterMonth {

    private Map<String, Set<String>> time = new HashMap<>();

    private Set<String> years = new HashSet<>();

    private Set<String> quarters = new HashSet<>();

    private Set<String> months = new HashSet<>();
}
