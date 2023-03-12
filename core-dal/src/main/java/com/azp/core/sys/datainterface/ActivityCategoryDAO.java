package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityCategoryDO;
import com.azp.core.sys.dataobject.ActivityCategoryDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityCategoryDAO extends base class
 */
@Repository
public interface ActivityCategoryDAO extends MyBatisBaseDAO<ActivityCategoryDO, String, ActivityCategoryDOExample> {
}