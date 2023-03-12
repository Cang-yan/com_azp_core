package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeOneDO;
import com.azp.core.sys.dataobject.ActivityTypeOneDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeOneDAO extends base class
 */
@Repository
public interface ActivityTypeOneDAO extends MyBatisBaseDAO<ActivityTypeOneDO, String, ActivityTypeOneDOExample> {
}