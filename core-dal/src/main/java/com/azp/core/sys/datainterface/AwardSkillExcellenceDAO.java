package com.azp.core.sys.datainterface;

import com.azp.core.base.MyBatisBaseDAO;
import com.azp.core.sys.dataobject.AwardSkillExcellenceDO;
import com.azp.core.sys.dataobject.AwardSkillExcellenceDOExample;
import org.springframework.stereotype.Repository;

/**
 * AwardSkillExcellenceDAO extends base class
 */
@Repository
public interface AwardSkillExcellenceDAO extends MyBatisBaseDAO<AwardSkillExcellenceDO, String, AwardSkillExcellenceDOExample> {
}