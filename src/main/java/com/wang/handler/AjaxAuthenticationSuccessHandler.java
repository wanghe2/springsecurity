package com.wang.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.wang.bean.AjaxResponseBody;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("200");
        responseBody.setMsg("Login Success!");
        System.err.println("*********登录成功**************");
        httpServletResponse.sendRedirect("/static/freeone");
//       httpServletRequest.getRequestDispatcher("/static/freeone").forward(httpServletRequest, httpServletResponse);
    }
}

