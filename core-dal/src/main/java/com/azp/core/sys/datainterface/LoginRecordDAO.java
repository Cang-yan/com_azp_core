package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.LoginRecordDO;
import com.azp.core.sys.dataobject.LoginRecordDOExample;
import org.springframework.stereotype.Repository;

/**
 * LoginRecordDAO extends base class
 */
@Repository
public interface LoginRecordDAO extends MyBatisBaseDAO<LoginRecordDO, String, LoginRecordDOExample> {
}