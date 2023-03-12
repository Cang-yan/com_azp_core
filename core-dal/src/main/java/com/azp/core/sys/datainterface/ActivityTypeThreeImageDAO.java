package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeThreeImageDO;
import com.azp.core.sys.dataobject.ActivityTypeThreeImageDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeThreeImageDAO extends base class
 */
@Repository
public interface ActivityTypeThreeImageDAO extends MyBatisBaseDAO<ActivityTypeThreeImageDO, String, ActivityTypeThreeImageDOExample> {
}