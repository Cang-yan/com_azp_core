package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ThreeMonthExcellentDO;
import com.azp.core.sys.dataobject.ThreeMonthExcellentDOExample;
import org.springframework.stereotype.Repository;

/**
 * ThreeMonthExcellentDAO extends base class
 */
@Repository
public interface ThreeMonthExcellentDAO extends MyBatisBaseDAO<ThreeMonthExcellentDO, String, ThreeMonthExcellentDOExample> {
}