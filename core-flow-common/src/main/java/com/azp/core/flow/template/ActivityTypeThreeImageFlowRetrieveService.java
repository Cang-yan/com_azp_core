package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeThreeImage;
import com.azp.core.sys.model.ActivityTypeThreeImageFilterMapper;
import com.azp.core.sys.service.ActivityTypeThreeImageService;
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
public class ActivityTypeThreeImageFlowRetrieveService {
  @Autowired
  private ActivityTypeThreeImageService activityTypeThreeImageService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_RETRIEVE_BY_PK")
  public ActivityTypeThreeImage retrieveByPK(String key) {
    return activityTypeThreeImageService.getByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return activityTypeThreeImageService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return activityTypeThreeImageService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(ActivityTypeThreeImageFilterMapper filterMapper) {
    return activityTypeThreeImageService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_RETRIEVE_LIST_BY_FILTER")
  public List<ActivityTypeThreeImage> retrieveListByFilter(ActivityTypeThreeImageFilterMapper filterMapper) {
    return activityTypeThreeImageService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(ActivityTypeThreeImageFilterMapper filterMapper) {
    return activityTypeThreeImageService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(ActivityTypeThreeImageFilterMapper filterMapper) {
    return activityTypeThreeImageService.getFilterDetailMapList(filterMapper);
  }
}
