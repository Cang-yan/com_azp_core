package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeThreeImage;
import com.azp.core.sys.model.ActivityTypeThreeImagePostMapper;
import com.azp.core.sys.service.ActivityTypeThreeImageService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class ActivityTypeThreeImageFlowCreateService {
  @Autowired
  private ActivityTypeThreeImageService activityTypeThreeImageService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_CREATE")
  public ActivityTypeThreeImage create(ActivityTypeThreeImage postEntity) {
    return activityTypeThreeImageService.post(postEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_CREATE_LIST")
  public List<ActivityTypeThreeImage> createList(List<ActivityTypeThreeImage> postEntities) {
    return activityTypeThreeImageService.postList(postEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<ActivityTypeThreeImagePostMapper> postMappers) {
    return activityTypeThreeImageService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_CREATE_MAPPING")
  public Map<String, Object> createMapping(ActivityTypeThreeImagePostMapper postMapper) {
    return activityTypeThreeImageService.postMapping(postMapper);
  }
}
