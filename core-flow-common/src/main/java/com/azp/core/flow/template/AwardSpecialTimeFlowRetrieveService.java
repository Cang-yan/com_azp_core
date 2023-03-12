package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardSpecialTime;
import com.azp.core.sys.model.AwardSpecialTimeFilterMapper;
import com.azp.core.sys.service.AwardSpecialTimeService;
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
public class AwardSpecialTimeFlowRetrieveService {
  @Autowired
  private AwardSpecialTimeService awardSpecialTimeService;

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_RETRIEVE_BY_PK")
  public AwardSpecialTime retrieveByPK(String key) {
    return awardSpecialTimeService.getByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return awardSpecialTimeService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return awardSpecialTimeService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(AwardSpecialTimeFilterMapper filterMapper) {
    return awardSpecialTimeService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_RETRIEVE_LIST_BY_FILTER")
  public List<AwardSpecialTime> retrieveListByFilter(AwardSpecialTimeFilterMapper filterMapper) {
    return awardSpecialTimeService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(AwardSpecialTimeFilterMapper filterMapper) {
    return awardSpecialTimeService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_SPECIAL_TIME_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(AwardSpecialTimeFilterMapper filterMapper) {
    return awardSpecialTimeService.getFilterDetailMapList(filterMapper);
  }
}
