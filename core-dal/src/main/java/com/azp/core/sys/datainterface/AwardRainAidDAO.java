package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.AwardRainAidDO;
import com.azp.core.sys.dataobject.AwardRainAidDOExample;
import org.springframework.stereotype.Repository;

/**
 * AwardRainAidDAO extends base class
 */
@Repository
public interface AwardRainAidDAO extends MyBatisBaseDAO<AwardRainAidDO, String, AwardRainAidDOExample> {
}