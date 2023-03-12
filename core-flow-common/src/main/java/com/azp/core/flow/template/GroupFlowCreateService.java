package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Group;
import com.azp.core.sys.model.GroupPostMapper;
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
public class GroupFlowCreateService {
  @Autowired
  private GroupService groupService;

  @Flow("FLOW_CODE_GROUP_CREATE")
  public Group create(Group postEntity) {
    return groupService.post(postEntity);
  }

  @Flow("FLOW_CODE_GROUP_CREATE_LIST")
  public List<Group> createList(List<Group> postEntities) {
    return groupService.postList(postEntities);
  }

  @Flow("FLOW_CODE_GROUP_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<GroupPostMapper> postMappers) {
    return groupService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_GROUP_CREATE_MAPPING")
  public Map<String, Object> createMapping(GroupPostMapper postMapper) {
    return groupService.postMapping(postMapper);
  }
}
