package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.YearExcellentDO;
import com.azp.core.sys.dataobject.YearExcellentDOExample;
import org.springframework.stereotype.Repository;

/**
 * YearExcellentDAO extends base class
 */
@Repository
public interface YearExcellentDAO extends MyBatisBaseDAO<YearExcellentDO, String, YearExcellentDOExample> {
}