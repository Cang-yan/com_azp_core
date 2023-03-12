package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Group;
import com.azp.core.sys.model.GroupUpdateMapper;
import com.azp.core.sys.service.GroupService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class GroupFlowUpdateService {
  @Autowired
  private GroupService groupService;

  @Flow("FLOW_CODE_GROUP_UPDATE")
  public Group update(Group updateEntity) {
    return groupService.update(updateEntity);
  }

  @Flow("FLOW_CODE_GROUP_UPDATE_LIST")
  public List<Group> updateList(List<Group> updateEntities) {
    return groupService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_GROUP_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<GroupUpdateMapper> updateMappers) {
    return groupService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_GROUP_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(GroupUpdateMapper updateMapper) {
    return groupService.updateMapping(updateMapper);
  }
}
