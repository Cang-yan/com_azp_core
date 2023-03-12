package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeFourPeriodsDO;
import com.azp.core.sys.dataobject.ActivityTypeFourPeriodsDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeFourPeriodsDAO extends base class
 */
@Repository
public interface ActivityTypeFourPeriodsDAO extends MyBatisBaseDAO<ActivityTypeFourPeriodsDO, String, ActivityTypeFourPeriodsDOExample> {
}