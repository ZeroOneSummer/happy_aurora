package com.aurora.happy.annotation;

import com.aurora.happy.aspect.ApiLogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启参数日志打印
 */
@Import(ApiLogAspect.class)   //核心，省略ApiLogAspect添加@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableApiLog {
}