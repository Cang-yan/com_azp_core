package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.NotificationDO;
import com.azp.core.sys.dataobject.NotificationDOExample;
import org.springframework.stereotype.Repository;

/**
 * NotificationDAO extends base class
 */
@Repository
public interface NotificationDAO extends MyBatisBaseDAO<NotificationDO, String, NotificationDOExample> {
}