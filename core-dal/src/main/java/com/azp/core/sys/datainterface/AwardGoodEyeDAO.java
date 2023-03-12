package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.AwardGoodEyeDO;
import com.azp.core.sys.dataobject.AwardGoodEyeDOExample;
import org.springframework.stereotype.Repository;

/**
 * AwardGoodEyeDAO extends base class
 */
@Repository
public interface AwardGoodEyeDAO extends MyBatisBaseDAO<AwardGoodEyeDO, String, AwardGoodEyeDOExample> {
}