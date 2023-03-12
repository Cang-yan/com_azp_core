package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeThreeDO;
import com.azp.core.sys.dataobject.ActivityTypeThreeDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeThreeDAO extends base class
 */
@Repository
public interface ActivityTypeThreeDAO extends MyBatisBaseDAO<ActivityTypeThreeDO, String, ActivityTypeThreeDOExample> {
}