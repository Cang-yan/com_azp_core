package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.ActivityDepartmentSelectDO;
import com.azp.core.sys.dataobject.ActivityDepartmentSelectDOExample;
import org.springframework.stereotype.Repository;

/**
 * ActivityDepartmentSelectDAO extends base class
 */
@Repository
public interface ActivityDepartmentSelectDAO extends MyBatisBaseDAO<ActivityDepartmentSelectDO, String, ActivityDepartmentSelectDOExample> {
}