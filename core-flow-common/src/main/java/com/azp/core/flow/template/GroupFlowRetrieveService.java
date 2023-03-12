package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.Group;
import com.azp.core.sys.model.GroupFilterMapper;
import com.azp.core.sys.service.GroupService;
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
public class GroupFlowRetrieveService {
  @Autowired
  private GroupService groupService;

  @Flow("FLOW_CODE_GROUP_RETRIEVE_BY_PK")
  public Group retrieveByPK(String key) {
    return groupService.getByPK(key);
  }

  @Flow("FLOW_CODE_GROUP_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return groupService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_GROUP_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return groupService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_GROUP_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(GroupFilterMapper filterMapper) {
    return groupService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_GROUP_RETRIEVE_LIST_BY_FILTER")
  public List<Group> retrieveListByFilter(GroupFilterMapper filterMapper) {
    return groupService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_GROUP_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(GroupFilterMapper filterMapper) {
    return groupService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_GROUP_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(GroupFilterMapper filterMapper) {
    return groupService.getFilterDetailMapList(filterMapper);
  }
}
