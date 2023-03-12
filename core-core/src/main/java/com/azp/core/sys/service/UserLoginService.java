package com.azp.core.sys.service;

import com.alibaba.fastjson.JSONObject;
import com.azp.core.sys.model.AuthUtil;
import com.azp.core.sys.model.UseInfo;
import com.azp.core.sys.model.UseInfoFilterMapper;
import com.horsecoder.common.status.StatusException;
import com.horsecoder.storage.domain.FileUpdateCoreFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/4/6 16:43
 */
@Service
public class UserLoginService {

    @Autowired
    UseInfoService useInfoService;

    @Resource
    private FileUpdateCoreFacade fileUpdateCoreFacade;

    public String weChatLogin(String code, String userId) {
        try {
            //通过第一步获得的code获取微信授权信息
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID + "&secret="
                    + AuthUtil.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
            JSONObject jsonObject = AuthUtil.doGetJson(url);
            String openid = jsonObject.getString("openid");
            String token = jsonObject.getString("access_token");
            String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid
                    + "&lang=zh_CN";
            JSONObject userInfo = AuthUtil.doGetJson(infoUrl);

            //成功获取授权,以下部分为业务逻辑处理了，根据个人业务需求写就可以了
            if (userInfo != null && openid != null) {
                String headimgurl = userInfo.getString("headimgurl");
                headimgurl = headimgurl.replace("\\", "");
                headimgurl = fileUpdateCoreFacade.uploadFileByUrl(headimgurl, "azp", null, null);
                UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
                useInfoFilterMapper.userId = userId;
                UseInfo useInfo = useInfoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
                if (useInfo == null) {
                    useInfo = new UseInfo();
                    useInfo.setUserId(userId);
                    useInfo.setHead(headimgurl);
                    useInfo.setPointNumber(0);
                    useInfo.setLevelId("");
                    useInfoService.post(useInfo);
                } else {
                    useInfo.setHead(headimgurl);
                    useInfoService.update(useInfo);
                }

                return "授权成功";
            } else {
                throw new StatusException("LOGIN_FAILED");
            }
        } catch (Exception e) {
            throw new StatusException("LOGIN_FAILED");
        }
    }
}
