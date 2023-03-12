package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.DepartmentPointDO;
import com.azp.core.sys.dataobject.DepartmentPointDOExample;
import org.springframework.stereotype.Repository;

/**
 * DepartmentPointDAO extends base class
 */
@Repository
public interface DepartmentPointDAO extends MyBatisBaseDAO<DepartmentPointDO, String, DepartmentPointDOExample> {
}