package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.GroupPointStatisticsDO;
import com.azp.core.sys.dataobject.GroupPointStatisticsDOExample;
import org.springframework.stereotype.Repository;

/**
 * GroupPointStatisticsDAO extends base class
 */
@Repository
public interface GroupPointStatisticsDAO extends MyBatisBaseDAO<GroupPointStatisticsDO, String, GroupPointStatisticsDOExample> {
}