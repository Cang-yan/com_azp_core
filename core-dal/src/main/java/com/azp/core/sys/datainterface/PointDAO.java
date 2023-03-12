package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.PointDO;
import com.azp.core.sys.dataobject.PointDOExample;
import org.springframework.stereotype.Repository;

/**
 * PointDAO extends base class
 */
@Repository
public interface PointDAO extends MyBatisBaseDAO<PointDO, String, PointDOExample> {
}