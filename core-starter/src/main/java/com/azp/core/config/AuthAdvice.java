package com.azp.core.config;

import com.horsecoder.auth.aop.deserializer.model.Body;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 22:38:32
 */
@Aspect
@Component
public class AuthAdvice extends com.horsecoder.auth.advice.AuthAdvice {

    @Override
    public boolean open(HttpServletRequest request, HttpServletResponse response) {
        // 研发环境默认关闭认证系统
        return !"dev".equals(getSpringContextActiveProfile());
    }

    @Override
    public boolean checkGroup(HttpServletRequest request, HttpServletResponse response, String uri, String group, Body body) {
        return true;
    }

    @Override
    public boolean checkExpression(HttpServletRequest request, HttpServletResponse response, String uri, String expression, boolean expressionResult, Body body) {
        return expressionResult;
    }

}
