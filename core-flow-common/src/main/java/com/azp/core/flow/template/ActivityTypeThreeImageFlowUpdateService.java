package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.ActivityTypeThreeImage;
import com.azp.core.sys.model.ActivityTypeThreeImageUpdateMapper;
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
public class ActivityTypeThreeImageFlowUpdateService {
  @Autowired
  private ActivityTypeThreeImageService activityTypeThreeImageService;

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_UPDATE")
  public ActivityTypeThreeImage update(ActivityTypeThreeImage updateEntity) {
    return activityTypeThreeImageService.update(updateEntity);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_UPDATE_LIST")
  public List<ActivityTypeThreeImage> updateList(List<ActivityTypeThreeImage> updateEntities) {
    return activityTypeThreeImageService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<ActivityTypeThreeImageUpdateMapper> updateMappers) {
    return activityTypeThreeImageService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_ACTIVITY_TYPE_THREE_IMAGE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(ActivityTypeThreeImageUpdateMapper updateMapper) {
    return activityTypeThreeImageService.updateMapping(updateMapper);
  }
}
