package com.azp.core.sys.service;

import com.azp.core.sys.model.Tips;
import com.azp.core.sys.model.TipsFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class TipsExtendsService {
    @Autowired
    private TipsService tipsService;

    public Tips getRandom() {
        TipsFilterMapper tipsFilterMapper = new TipsFilterMapper();
        List<Tips> tipsServiceListByFilter = tipsService.getListByFilter(tipsFilterMapper);
        return tipsServiceListByFilter.get((int) (new Date().getTime() % tipsServiceListByFilter.size()));
    }

}
