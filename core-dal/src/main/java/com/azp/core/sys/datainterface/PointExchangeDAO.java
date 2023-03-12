package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.PointExchangeDO;
import com.azp.core.sys.dataobject.PointExchangeDOExample;
import org.springframework.stereotype.Repository;

/**
 * PointExchangeDAO extends base class
 */
@Repository
public interface PointExchangeDAO extends MyBatisBaseDAO<PointExchangeDO, String, PointExchangeDOExample> {
}