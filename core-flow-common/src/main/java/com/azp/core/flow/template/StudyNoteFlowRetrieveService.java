package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.StudyNote;
import com.azp.core.sys.model.StudyNoteFilterMapper;
import com.azp.core.sys.service.StudyNoteService;
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
public class StudyNoteFlowRetrieveService {
  @Autowired
  private StudyNoteService studyNoteService;

  @Flow("FLOW_CODE_STUDY_NOTE_RETRIEVE_BY_PK")
  public StudyNote retrieveByPK(String key) {
    return studyNoteService.getByPK(key);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_RETRIEVE_SIMPLE_MAP_BY_PK")
  public Map<String, Object> retrieveSimpleMapByPK(String key) {
    return studyNoteService.getSimpleMapByPK(key);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_RETRIEVE_DETAIL_MAP_BY_PK")
  public Map<String, Object> retrieveDetailMapByPK(String key) {
    return studyNoteService.getDetailMapByPK(key);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_RETRIEVE_COUNT_BY_FILTER")
  public Long retrieveCountByFilter(StudyNoteFilterMapper filterMapper) {
    return studyNoteService.getCountByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_RETRIEVE_LIST_BY_FILTER")
  public List<StudyNote> retrieveListByFilter(StudyNoteFilterMapper filterMapper) {
    return studyNoteService.getListByFilter(filterMapper);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_RETRIEVE_FILTER_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterMapList(StudyNoteFilterMapper filterMapper) {
    return studyNoteService.getFilterMapList(filterMapper);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_RETRIEVE_FILTER_DETAIL_MAP_LIST")
  public List<Map<String, Object>> retrieveFilterDetailMapList(StudyNoteFilterMapper filterMapper) {
    return studyNoteService.getFilterDetailMapList(filterMapper);
  }
}
