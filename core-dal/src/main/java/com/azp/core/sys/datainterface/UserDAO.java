package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.UserDO;
import com.azp.core.sys.dataobject.UserDOExample;
import org.springframework.stereotype.Repository;

/**
 * UserDAO extends base class
 */
@Repository
public interface UserDAO extends MyBatisBaseDAO<UserDO, String, UserDOExample> {
}