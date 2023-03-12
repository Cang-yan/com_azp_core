package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Tips;
import com.azp.core.sys.model.TipsFilterMapper;
import com.azp.core.sys.service.TipsService;
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
public class TipsFlowRetrieveService {
  @Autowired
  private TipsService tipsService;

  @Flow("FLOW_CODE_TIPS_RETRIEVE_BY_PK")
  public Tips retrieveByPK(String key) {
    return tipsService.getByPK(key);
  }

  @Flow("FLOW_CODE_TIPS_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return tipsService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_TIPS_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return tipsService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_TIPS_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(TipsFilterMapper filterMapper) {
    return tipsService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_TIPS_RETRIEVE_LIST_BY_FILTER")
  public List<Tips> retrieveListByFilter(TipsFilterMapper filterMapper) {
    return tipsService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_TIPS_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(TipsFilterMapper filterMapper) {
    return tipsService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_TIPS_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(TipsFilterMapper filterMapper) {
    return tipsService.getFilterDetailMapList(filterMapper);
  }
}
