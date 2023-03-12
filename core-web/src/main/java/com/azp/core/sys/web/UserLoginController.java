package com.azp.core.sys.web;

import com.azp.core.sys.model.*;
import com.azp.core.sys.service.UseInfoService;
import com.azp.core.sys.service.UserExtendsService;
import com.azp.core.sys.service.UserLoginService;
import com.azp.core.sys.service.UserService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.error.Assert;
import com.horsecoder.common.status.Status;
import com.horsecoder.common.status.StatusException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Api(
        value = "user_login",
        tags = "用户登录管理"
)
@RestController
@RequestMapping("api/sys/user/login")
public class UserLoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UseInfoService useInfoService;

    @Autowired
    private UserExtendsService userExtendsService;

    @Autowired
    private UserLoginService userLoginService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "微信授权登录获取头像",
            notes = "微信授权登录获取头像"
    )
    @RequestMapping(
            value = "wxhead",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> wxOAuth(@RequestParam String code, @RequestParam String userId) {
        return Status.successBuilder()
                .addDataValue(userLoginService.weChatLogin(code, userId))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "登录/注册",
            notes = "登录/注册"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> login(@RequestBody UserPostMapper user, HttpServletResponse
            response) {
        // 管理员默认部门是0
        user.departmentId = "0";
        user.userCode = "0";
        user.name = "admin";
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.account = user.account;
        List<User> users = userService.getListByFilter(userFilterMapper);
        User loginUser = null;
        if (!CollectionUtils.isEmpty(users)) {
            loginUser = users.get(0);
            Assert.isTrue(loginUser.getPassword().equals(user.password), "PASSWORD_ERROR");
        } else {
            loginUser = userService.post(user.buildEntity());
        }
        String loginUserId = loginUser.getId();
        String token = Base64.getUrlEncoder().encodeToString(loginUserId.getBytes(StandardCharsets.UTF_8));
        Cookie cookie = new Cookie("token", token);
        Cookie userId = new Cookie("userId", loginUserId);
        userId.setPath("/");
        userId.setMaxAge(60 * 60 * 24);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        response.setHeader("token", token);
        response.setHeader("userId", loginUserId);
        response.addCookie(cookie);
        response.addCookie(userId);
        return Status.successBuilder()
                .addDataValue(UserReturnMapper.buildMapper(loginUser))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "员工登录",
            notes = "员工登录"
    )
    @RequestMapping(
            value = "user",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> userLogin(@RequestBody UserPostMapper user, HttpServletResponse
            response) {
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.userCodeIn = Collections.singletonList(user.userCode);
        List<User> users = userService.getListByFilter(userFilterMapper);
        User loginUser = null;
        if (!CollectionUtils.isEmpty(users)) {
            loginUser = users.get(0);
            Assert.isTrue(loginUser.getStatus() == 0, "USER_PAUSE");
            Assert.isTrue(loginUser.getName().equals(user.name), "MATCH_ERROR");
            UseInfoFilterMapper useInfoFilterMapper = new UseInfoFilterMapper();
            useInfoFilterMapper.userId = loginUser.getId();
            UseInfo useInfo = useInfoService.getListByFilter(useInfoFilterMapper).stream().findFirst().orElse(null);
            if (useInfo == null) {
                useInfo = new UseInfo();
                useInfo.setPointNumber(0);
                useInfo.setUserId(loginUser.getId());
                UseInfo post = useInfoService.post(useInfo);
                loginUser.setUserInfoId(post.getId());
                loginUser = userService.update(loginUser);
            }
            String loginUserId = loginUser.getId();
            String token = Base64.getUrlEncoder().encodeToString(loginUserId.getBytes(StandardCharsets.UTF_8));
            Cookie cookie = new Cookie("token", token);
            Cookie userId = new Cookie("userId", loginUserId);
            userId.setPath("/");
            userId.setMaxAge(60 * 60 * 24);
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            response.setHeader("token", token);
            response.setHeader("userId", loginUserId);
            response.addCookie(cookie);
            response.addCookie(userId);
            return Status.successBuilder()
                    .addDataValue(userExtendsService.getUserInfoDetail(loginUserId))
                    .map();
        } else {
            throw new StatusException(755, "没有该用户");
        }
    }

}
