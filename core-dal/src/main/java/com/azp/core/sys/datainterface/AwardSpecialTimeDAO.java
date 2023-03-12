package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.AwardSpecialTimeDO;
import com.azp.core.sys.dataobject.AwardSpecialTimeDOExample;
import org.springframework.stereotype.Repository;

/**
 * AwardSpecialTimeDAO extends base class
 */
@Repository
public interface AwardSpecialTimeDAO extends MyBatisBaseDAO<AwardSpecialTimeDO, String, AwardSpecialTimeDOExample> {
}