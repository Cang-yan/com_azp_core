package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.AwardGoodEye;
import com.azp.core.sys.model.AwardGoodEyeFilterMapper;
import com.azp.core.sys.service.AwardGoodEyeService;
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
public class AwardGoodEyeFlowRetrieveService {
  @Autowired
  private AwardGoodEyeService awardGoodEyeService;

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_RETRIEVE_BY_PK")
  public AwardGoodEye retrieveByPK(String key) {
    return awardGoodEyeService.getByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return awardGoodEyeService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return awardGoodEyeService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(AwardGoodEyeFilterMapper filterMapper) {
    return awardGoodEyeService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_RETRIEVE_LIST_BY_FILTER")
  public List<AwardGoodEye> retrieveListByFilter(AwardGoodEyeFilterMapper filterMapper) {
    return awardGoodEyeService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(AwardGoodEyeFilterMapper filterMapper) {
    return awardGoodEyeService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_AWARD_GOOD_EYE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(AwardGoodEyeFilterMapper filterMapper) {
    return awardGoodEyeService.getFilterDetailMapList(filterMapper);
  }
}
