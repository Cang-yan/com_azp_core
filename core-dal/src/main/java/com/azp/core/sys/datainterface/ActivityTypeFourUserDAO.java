package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourUserDO;
import com.azp.core.sys.dataobject.ActivityTypeFourUserDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeFourUserDAO extends base class
 */
@Repository
public interface ActivityTypeFourUserDAO extends MyBatisBaseDAO<ActivityTypeFourUserDO, String, ActivityTypeFourUserDOExample> {
}