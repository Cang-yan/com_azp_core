package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.TipsDO;
import com.azp.core.sys.dataobject.TipsDOExample;
import org.springframework.stereotype.Repository;

/**
 * TipsDAO extends base class
 */
@Repository
public interface TipsDAO extends MyBatisBaseDAO<TipsDO, String, TipsDOExample> {
}