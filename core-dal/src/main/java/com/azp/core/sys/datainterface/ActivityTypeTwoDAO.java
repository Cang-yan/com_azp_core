package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeTwoDO;
import com.azp.core.sys.dataobject.ActivityTypeTwoDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeTwoDAO extends base class
 */
@Repository
public interface ActivityTypeTwoDAO extends MyBatisBaseDAO<ActivityTypeTwoDO, String, ActivityTypeTwoDOExample> {
}