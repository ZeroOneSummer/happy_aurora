package com.aurora.happy.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
 
    //普通的操作说明
    String value() default "";
    
    //spel表达式的操作说明
    String spelValue() default "";
}