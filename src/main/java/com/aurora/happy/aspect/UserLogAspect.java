package com.aurora.happy.aspect;

import com.aurora.happy.annotation.UserLog;
import com.aurora.happy.mapper.UserLogMapper;
import com.aurora.happy.pojo.User;
import com.aurora.happy.pojo.UserLogDO;
import com.aurora.happy.utils.ThreadLocalUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 操作日志注解切面实现
 */
@Slf4j
@Component
@Aspect
public class UserLogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserLogMapper userLogMapper;

    @Pointcut("@annotation(com.aurora.happy.annotation.UserLog)")
    public void pointcut() {
    }

    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint point) {
        saveSysUserLog(point);
    }

    private void saveSysUserLog(JoinPoint point) {

        // 目标方法、以及方法上的@UserLog注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        UserLog userLogAnnotation = method.getAnnotation(UserLog.class);
        if (userLogAnnotation == null) return;

        // 获取当前登录用户
        User user = getUserInfo();

        // 收集相关信息并保存
        UserLogDO userLogDO = new UserLogDO();
        userLogDO.setModuleCode(userLogAnnotation.module().getModuleCode());
        userLogDO.setContent(getContentJson(point));
        userLogDO.setTitle(userLogAnnotation.title());
        userLogDO.setOperatorId(Integer.toUnsignedLong(user.getAge()));
        userLogDO.setOperateTime(new Date());
        userLogDO.setType(userLogAnnotation.type().getValue());
        int num = userLogMapper.add(userLogDO);
        log.info("saveSysUserLog: {}", num);
    }

    //获取登陆用户信息
    private User getUserInfo() {
        return (User) ThreadLocalUtil.get("system");
    }

    private String getContentJson(JoinPoint point) {
        String requestType = request.getMethod();
        if ("GET".equals(requestType)) {
            // 如果是GET请求，直接返回QueryString（目前没有针对查询操作进行日志记录，先留着吧）
            return request.getQueryString();
        }

        Object[] args = point.getArgs();
        Object[] arguments = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            // 只打印客户端传递的参数，排除Spring注入的参数，比如HttpServletRequest
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        try {
            return objectMapper.writeValueAsString(arguments);
        } catch (JsonProcessingException e) {
            log.error("UserLogAspect#getContentJson JsonProcessingException", e);
        }
        return "";
    }
}