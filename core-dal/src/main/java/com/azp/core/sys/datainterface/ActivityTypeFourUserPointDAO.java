package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourUserPointDO;
import com.azp.core.sys.dataobject.ActivityTypeFourUserPointDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeFourUserPointDAO extends base class
 */
@Repository
public interface ActivityTypeFourUserPointDAO extends MyBatisBaseDAO<ActivityTypeFourUserPointDO, String, ActivityTypeFourUserPointDOExample> {
}