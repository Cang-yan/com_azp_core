package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeSixDO;
import com.azp.core.sys.dataobject.ActivityTypeSixDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeSixDAO extends base class
 */
@Repository
public interface ActivityTypeSixDAO extends MyBatisBaseDAO<ActivityTypeSixDO, String, ActivityTypeSixDOExample> {
}