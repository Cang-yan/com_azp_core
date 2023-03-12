package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.MonthExcellentDO;
import com.azp.core.sys.dataobject.MonthExcellentDOExample;
import org.springframework.stereotype.Repository;

/**
 * MonthExcellentDAO extends base class
 */
@Repository
public interface MonthExcellentDAO extends MyBatisBaseDAO<MonthExcellentDO, String, MonthExcellentDOExample> {
}