package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ActivityTypeTwoUserExtendsService {
    @Autowired
    private ActivityTypeTwoUserService activityTypeTwoUserService;
    @Autowired
    private ActivityTypeTwoService activityTypeTwoService;
    //用户报名
    public List<Map<String,Object>> getEnrollInfo(ActivityTypeTwoUserFilterMapper activityTypeTwoUserFilterMapper, String subCategoryId)
    {
        List<Map<String,Object>> activityTypeTwoUserEnrollMappers = new ArrayList<>();
        List<ActivityTypeTwoUser> activityTypeTwoUsers = activityTypeTwoUserService.getListByFilter(activityTypeTwoUserFilterMapper);
        if(subCategoryId == null)
        {
            for(ActivityTypeTwoUser activityTypeTwoUser : activityTypeTwoUsers)
            {
                if(activityTypeTwoService.getByPK(activityTypeTwoUser.getActivityTypeTwoId()) != null)
                {
                    activityTypeTwoUserEnrollMappers.add(activityTypeTwoUserService.getDetailMapByPK(activityTypeTwoUser.getId()));
                }
            }
            return activityTypeTwoUserEnrollMappers;
        }
        else
        {
            for(ActivityTypeTwoUser activityTypeTwoUser : activityTypeTwoUsers)
            {
                if(activityTypeTwoService.getByPK(activityTypeTwoUser.getActivityTypeTwoId()) != null)
                {
                    if(Objects.equals(activityTypeTwoService.getByPK(activityTypeTwoUser.getActivityTypeTwoId()).getActivitySubCategoryId(), subCategoryId))
                    {
                        activityTypeTwoUserEnrollMappers.add(activityTypeTwoUserService.getDetailMapByPK(activityTypeTwoUser.getId()));
                    }
                }
            }
            return activityTypeTwoUserEnrollMappers;
        }
    }

    public Map<String, Object> enroll(ActivityTypeTwoUserPostMapper postMapper) {
        //根据UserId查报名记录
        ActivityTypeTwo activityTypeTwo = activityTypeTwoService.getByPK(postMapper.activityTypeTwoId);
        if (activityTypeTwo == null || activityTypeTwo.getStatus() != 1) throw new StatusException("ACTIVITY_NOT_EXIST");
        ActivityTypeTwoUserFilterMapper activityTypeTwoUserFilterMapper = new ActivityTypeTwoUserFilterMapper();
        activityTypeTwoUserFilterMapper.userId = postMapper.userId;
        activityTypeTwoUserFilterMapper.activityTypeTwoId = postMapper.activityTypeTwoId;
        ActivityTypeTwo activityTypeTwoEntity;
        ActivityTypeTwoUser activityTypeTwoUserEntity;
        List<ActivityTypeTwoUser> getEnrollInfo = activityTypeTwoUserService.getListByFilter(activityTypeTwoUserFilterMapper);

        if(getEnrollInfo.size() != 0)//如果有报名记录
        {
            activityTypeTwoUserEntity = getEnrollInfo.get(0);
            Assert.isTrue(activityTypeTwoUserEntity.getStatus() != 0, "RE_ENROLL");
            activityTypeTwoUserEntity.setStatus(0);//修改状态
            activityTypeTwoUserEntity.setSignDate(postMapper.buildEntity().getSignDate());//时间
            activityTypeTwoUserService.update(getEnrollInfo.get(0));
            activityTypeTwoEntity = activityTypeTwoService.getByPK(getEnrollInfo.get(0).getActivityTypeTwoId());
        }
        else//如果没有
        {
            activityTypeTwoUserEntity = activityTypeTwoUserService.post(postMapper.buildEntity());
            activityTypeTwoEntity = activityTypeTwoService.getByPK(activityTypeTwoUserEntity.getActivityTypeTwoId());
        }
        //修改报名人数
        activityTypeTwoEntity.setParticipantsNumber(activityTypeTwoEntity.getParticipantsNumber() + 1);
        if(activityTypeTwoEntity.getLimitNumber() <= activityTypeTwoEntity.getParticipantsNumber())
        {
            activityTypeTwoEntity.setStatus(3);
        }
        activityTypeTwoService.update(activityTypeTwoEntity);
        return ActivityTypeTwoUserDetailMapper.buildMap(activityTypeTwoUserEntity);
    }

    public Map<String, Object> unenroll(ActivityTypeTwoUserUpdateMapper updateMapper) {
        //修改报名状态
        updateMapper.status = 4;
        ActivityTypeTwoUser activityTypeTwoUserEntity = activityTypeTwoUserService.update(updateMapper.buildEntity());
        //修改报名人数
        ActivityTypeTwo activityTypeTwo = activityTypeTwoService.getByPK(activityTypeTwoUserEntity.getActivityTypeTwoId());
        activityTypeTwo.setParticipantsNumber(activityTypeTwo.getParticipantsNumber() - 1);
        activityTypeTwo.setStatus(1);
        activityTypeTwoService.update(activityTypeTwo);
        return ActivityTypeTwoUserDetailMapper.buildMap(activityTypeTwoUserEntity);
    }
}
