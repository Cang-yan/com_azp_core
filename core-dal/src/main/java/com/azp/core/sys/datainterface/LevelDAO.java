package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.LevelDO;
import com.azp.core.sys.dataobject.LevelDOExample;
import org.springframework.stereotype.Repository;

/**
 * LevelDAO extends base class
 */
@Repository
public interface LevelDAO extends MyBatisBaseDAO<LevelDO, String, LevelDOExample> {
}