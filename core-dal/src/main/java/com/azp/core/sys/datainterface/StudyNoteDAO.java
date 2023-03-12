package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.StudyNoteDO;
import com.azp.core.sys.dataobject.StudyNoteDOExample;
import org.springframework.stereotype.Repository;

/**
 * StudyNoteDAO extends base class
 */
@Repository
public interface StudyNoteDAO extends MyBatisBaseDAO<StudyNoteDO, String, StudyNoteDOExample> {
}