package com.aurora.happy.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MvcConfig里注入
 */
@Component
public class BizInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 随便写的逻辑，总之就是拦截
        boolean isAuth = request.getHeader("Authorization") == null;
        System.out.println("权限验证："+isAuth);
        return isAuth;
    }
}