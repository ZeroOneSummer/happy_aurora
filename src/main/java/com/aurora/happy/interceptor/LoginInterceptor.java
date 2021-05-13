package com.aurora.happy.interceptor;

import com.aurora.happy.annotation.LoginRequired;
import com.aurora.happy.contanst.Contants;
import com.aurora.happy.pojo.User;
import com.aurora.happy.utils.ThreadLocalUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * Created by pijiang on 2021/5/13.
 * 登录拦截器
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不拦截跨域请求相关
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;
        if (isLoginFree(handler)) return true;
        User user = isLoginSucess(request);
        // 登录成功，把用户信息存入ThreadLocal
        ThreadLocalUtil.put(Contants.USER_INFO, user);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove(Contants.USER_INFO);
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 是否免登录
     */
    private boolean isLoginFree(Object handler){
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = AnnotationUtils.getAnnotation(method, LoginRequired.class);
            return loginRequired == null;
        }
        return true;
    }

    /**
     * 登录校验
     */
    private User isLoginSucess(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Contants.CURRENT_USER_IN_SESSION);
        if (user == null) throw new RuntimeException("User need login");
        return user;
    }
}