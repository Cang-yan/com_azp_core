package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.DepartmentDO;
import com.azp.core.sys.dataobject.DepartmentDOExample;
import org.springframework.stereotype.Repository;

/**
 * DepartmentDAO extends base class
 */
@Repository
public interface DepartmentDAO extends MyBatisBaseDAO<DepartmentDO, String, DepartmentDOExample> {
}