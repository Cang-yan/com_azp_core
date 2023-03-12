package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardRainAid;
import com.azp.core.sys.model.AwardRainAidFilterMapper;
import com.azp.core.sys.service.AwardRainAidService;
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
public class AwardRainAidFlowRetrieveService {
  @Autowired
  private AwardRainAidService awardRainAidService;

  @Flow("FLOW_CODE_AWARD_RAIN_AID_RETRIEVE_BY_PK")
  public AwardRainAid retrieveByPK(String key) {
    return awardRainAidService.getByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return awardRainAidService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return awardRainAidService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(AwardRainAidFilterMapper filterMapper) {
    return awardRainAidService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_RETRIEVE_LIST_BY_FILTER")
  public List<AwardRainAid> retrieveListByFilter(AwardRainAidFilterMapper filterMapper) {
    return awardRainAidService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(AwardRainAidFilterMapper filterMapper) {
    return awardRainAidService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_RAIN_AID_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(AwardRainAidFilterMapper filterMapper) {
    return awardRainAidService.getFilterDetailMapList(filterMapper);
  }
}
