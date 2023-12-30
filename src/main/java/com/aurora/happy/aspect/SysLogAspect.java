package com.aurora.happy.aspect;

import com.aurora.happy.annotation.SysLog;
import com.aurora.happy.pojo.Log;
import com.aurora.happy.utils.SpelUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Autowired(required = false)
    private HttpServletRequest request;
 
    @Pointcut("@annotation(com.aurora.happy.annotation.SysLog)")
    public void logPointCut() {}
 
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
 
        //保存日志
        saveSysLog(point, time);
 
        return result;
    }
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
 
        Log sysLog = new Log();
        sysLog.setTime(time);
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            if (StringUtils.hasText(syslog.value())) {
                sysLog.setOperation(syslog.value());
            }
            if (StringUtils.hasText(syslog.spelValue())) {
                String spelValue = SpelUtil.generateKeyBySpEL(syslog.spelValue(), joinPoint);
                sysLog.setOperation(spelValue);
            }
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
 
        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = toJsonStr(args);
            sysLog.setParams(params);
        } catch (Exception ignored) {
        }
 
        //设置IP地址
        sysLog.setIp(getClientIP(request));
        sysLog.setBrowser(getBrowser(request));
 
        //保存系统日志
        log.info("保存系统日志: {}", toJsonStr(sysLog));
    }

    //转json
    private String toJsonStr(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取ip
    private String getClientIP(HttpServletRequest request) {
        if (request == null) return "127.0.0.1";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    //获取浏览器类型
    private String getBrowser(HttpServletRequest request) {
        if (request == null) return "unknow";
        // 获取请求头：user-agent
        String userAgent = request.getHeader("user-agent");
        System.out.println("userAgent：" + userAgent);
        if (userAgent == null) return "unknow";
        // 判断用户使用的浏览器类型
        String browserName;
        if (userAgent.contains("Firefox")){
            browserName = "Firefox"; //火狐
        }else if (userAgent.contains("Chrome")){
            browserName = "Chrome"; //谷歌
        }else if (userAgent.contains("Trident")){
            browserName = "Trident"; //IE
        }else{
            browserName = "unknow"; //未知
        }
        return browserName;
    }
}