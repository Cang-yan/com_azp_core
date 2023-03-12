package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSnowHeat;
import com.azp.core.sys.model.AwardSnowHeatFilterMapper;
import com.azp.core.sys.service.AwardSnowHeatService;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class AwardSnowHeatFlowRetrieveService {
  @Autowired
  private AwardSnowHeatService awardSnowHeatService;

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_RETRIEVE_BY_PK")
  public AwardSnowHeat retrieveByPK(String key) {
    return awardSnowHeatService.getByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return awardSnowHeatService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return awardSnowHeatService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(AwardSnowHeatFilterMapper filterMapper) {
    return awardSnowHeatService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_RETRIEVE_LIST_BY_FILTER")
  public List<AwardSnowHeat> retrieveListByFilter(AwardSnowHeatFilterMapper filterMapper) {
    return awardSnowHeatService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(AwardSnowHeatFilterMapper filterMapper) {
    return awardSnowHeatService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SNOW_HEAT_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(AwardSnowHeatFilterMapper filterMapper) {
    return awardSnowHeatService.getFilterDetailMapList(filterMapper);
  }
}
