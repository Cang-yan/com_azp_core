package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.UserPointStatisticsDO;
import com.azp.core.sys.dataobject.UserPointStatisticsDOExample;
import org.springframework.stereotype.Repository;

/**
 * UserPointStatisticsDAO extends base class
 */
@Repository
public interface UserPointStatisticsDAO extends MyBatisBaseDAO<UserPointStatisticsDO, String, UserPointStatisticsDOExample> {
}