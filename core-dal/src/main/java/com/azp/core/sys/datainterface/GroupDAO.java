package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.GroupDO;
import com.azp.core.sys.dataobject.GroupDOExample;
import org.springframework.stereotype.Repository;

/**
 * GroupDAO extends base class
 */
@Repository
public interface GroupDAO extends MyBatisBaseDAO<GroupDO, String, GroupDOExample> {
}