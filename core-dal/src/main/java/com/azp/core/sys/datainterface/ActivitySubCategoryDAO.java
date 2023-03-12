package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivitySubCategoryDO;
import com.azp.core.sys.dataobject.ActivitySubCategoryDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivitySubCategoryDAO extends base class
 */
@Repository
public interface ActivitySubCategoryDAO extends MyBatisBaseDAO<ActivitySubCategoryDO, String, ActivitySubCategoryDOExample> {
}