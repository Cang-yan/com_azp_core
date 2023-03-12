package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.AwardSnowHeatDO;
import com.azp.core.sys.dataobject.AwardSnowHeatDOExample;
import org.springframework.stereotype.Repository;

/**
 * AwardSnowHeatDAO extends base class
 */
@Repository
public interface AwardSnowHeatDAO extends MyBatisBaseDAO<AwardSnowHeatDO, String, AwardSnowHeatDOExample> {
}