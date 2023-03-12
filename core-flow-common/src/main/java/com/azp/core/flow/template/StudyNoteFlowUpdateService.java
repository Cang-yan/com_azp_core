package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.model.StudyNote;
import com.azp.core.sys.model.StudyNoteUpdateMapper;
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
public class StudyNoteFlowUpdateService {
  @Autowired
  private StudyNoteService studyNoteService;

  @Flow("FLOW_CODE_STUDY_NOTE_UPDATE")
  public StudyNote update(StudyNote updateEntity) {
    return studyNoteService.update(updateEntity);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_UPDATE_LIST")
  public List<StudyNote> updateList(List<StudyNote> updateEntities) {
    return studyNoteService.updateList(updateEntities);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_UPDATE_MAPPING_LIST")
  public List<Map<String, Object>> updateMappingList(List<StudyNoteUpdateMapper> updateMappers) {
    return studyNoteService.updateMappingList(updateMappers);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_UPDATE_MAPPING")
  public Map<String, Object> updateMapping(StudyNoteUpdateMapper updateMapper) {
    return studyNoteService.updateMapping(updateMapper);
  }
}
