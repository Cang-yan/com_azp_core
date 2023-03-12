package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeTwoUserDO;
import com.azp.core.sys.dataobject.ActivityTypeTwoUserDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeTwoUserDAO extends base class
 */
@Repository
public interface ActivityTypeTwoUserDAO extends MyBatisBaseDAO<ActivityTypeTwoUserDO, String, ActivityTypeTwoUserDOExample> {
}