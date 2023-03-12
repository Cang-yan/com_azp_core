package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourDO;
import com.azp.core.sys.dataobject.ActivityTypeFourDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeFourDAO extends base class
 */
@Repository
public interface ActivityTypeFourDAO extends MyBatisBaseDAO<ActivityTypeFourDO, String, ActivityTypeFourDOExample> {
}