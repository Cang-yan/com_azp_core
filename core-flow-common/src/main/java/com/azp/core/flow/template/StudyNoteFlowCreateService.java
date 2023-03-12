package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.StudyNote;
import com.azp.core.sys.model.StudyNotePostMapper;
import com.azp.core.sys.service.StudyNoteService;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class StudyNoteFlowCreateService {
  @Autowired
  private StudyNoteService studyNoteService;

  @Flow("FLOW_CODE_STUDY_NOTE_CREATE")
  public StudyNote create(StudyNote postEntity) {
    return studyNoteService.post(postEntity);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_CREATE_LIST")
  public List<StudyNote> createList(List<StudyNote> postEntities) {
    return studyNoteService.postList(postEntities);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_CREATE_MAPPING_LIST")
  public List<Map<String, Object>> createMappingList(List<StudyNotePostMapper> postMappers) {
    return studyNoteService.postMappingList(postMappers);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_CREATE_MAPPING")
  public Map<String, Object> createMapping(StudyNotePostMapper postMapper) {
    return studyNoteService.postMapping(postMapper);
  }
}
