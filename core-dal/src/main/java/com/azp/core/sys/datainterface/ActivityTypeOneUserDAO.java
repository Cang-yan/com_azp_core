package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeOneUserDO;
import com.azp.core.sys.dataobject.ActivityTypeOneUserDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeOneUserDAO extends base class
 */
@Repository
public interface ActivityTypeOneUserDAO extends MyBatisBaseDAO<ActivityTypeOneUserDO, String, ActivityTypeOneUserDOExample> {
}