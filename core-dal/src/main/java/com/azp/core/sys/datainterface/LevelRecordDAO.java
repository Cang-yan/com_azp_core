package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.LevelRecordDO;
import com.azp.core.sys.dataobject.LevelRecordDOExample;
import org.springframework.stereotype.Repository;

/**
 * LevelRecordDAO extends base class
 */
@Repository
public interface LevelRecordDAO extends MyBatisBaseDAO<LevelRecordDO, String, LevelRecordDOExample> {
}