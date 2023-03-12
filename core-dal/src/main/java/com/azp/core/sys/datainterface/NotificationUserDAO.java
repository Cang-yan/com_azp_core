package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.NotificationUserDO;
import com.azp.core.sys.dataobject.NotificationUserDOExample;
import org.springframework.stereotype.Repository;

/**
 * NotificationUserDAO extends base class
 */
@Repository
public interface NotificationUserDAO extends MyBatisBaseDAO<NotificationUserDO, String, NotificationUserDOExample> {
}