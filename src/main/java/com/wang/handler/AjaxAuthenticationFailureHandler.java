package com.wang.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.wang.bean.AjaxResponseBody;

@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("400");
        responseBody.setMsg("Login Failure!");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
