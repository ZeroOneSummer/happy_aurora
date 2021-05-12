package com.aurora.happy.interceptor;

import com.alibaba.fastjson.JSON;
import com.aurora.happy.pojo.User;
import com.aurora.happy.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("-> UserInterceptor-UserInterceptor");
        //全局ThreadLocal存储信息
        System.out.println("ThreadLocal存储用户信息");
        ThreadLocalUtil.put("system", new User(1001L, "管理员", 30));
        ThreadLocalUtil.put("another", "another");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("UserInterceptor-afterCompletion");
        User user = (User) ThreadLocalUtil.get("system");
        System.out.println("-> ThreadLocal获取用户信息：" + JSON.toJSONString(user));

        ThreadLocalUtil.remove((String) ThreadLocalUtil.get("another"));
        ThreadLocalUtil.clear();
        super.afterCompletion(request, response, handler, ex);
    }

}