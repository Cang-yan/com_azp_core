package com.azp.core.flow.template;

import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.flow.spring.FlowComponent;
import com.azp.core.sys.service.StudyNoteService;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@FlowComponent
public class StudyNoteFlowDeleteService {
  @Autowired
  private StudyNoteService studyNoteService;

  @Flow("FLOW_CODE_STUDY_NOTE_DELETE")
  public Integer delete(String key) {
    return studyNoteService.delete(key);
  }

  @Flow("FLOW_CODE_STUDY_NOTE_DELETE_BATCH")
  public Integer deleteBatch(ArrayList<String> keys) {
    return studyNoteService.deleteList(keys);
  }
}
