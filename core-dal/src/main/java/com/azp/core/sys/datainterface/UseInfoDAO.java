package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.UseInfoDO;
import com.azp.core.sys.dataobject.UseInfoDOExample;
import org.springframework.stereotype.Repository;

/**
 * UseInfoDAO extends base class
 */
@Repository
public interface UseInfoDAO extends MyBatisBaseDAO<UseInfoDO, String, UseInfoDOExample> {
}