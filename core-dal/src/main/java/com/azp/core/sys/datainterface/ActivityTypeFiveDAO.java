package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityTypeFiveDO;
import com.azp.core.sys.dataobject.ActivityTypeFiveDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityTypeFiveDAO extends base class
 */
@Repository
public interface ActivityTypeFiveDAO extends MyBatisBaseDAO<ActivityTypeFiveDO, String, ActivityTypeFiveDOExample> {
}