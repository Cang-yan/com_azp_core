package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.PointStoreDO;
import com.azp.core.sys.dataobject.PointStoreDOExample;
import org.springframework.stereotype.Repository;

/**
 * PointStoreDAO extends base class
 */
@Repository
public interface PointStoreDAO extends MyBatisBaseDAO<PointStoreDO, String, PointStoreDOExample> {
}