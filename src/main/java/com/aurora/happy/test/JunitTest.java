package com.aurora.happy.test;

import com.alibaba.fastjson.JSON;
import com.aurora.happy.annotation.VAfter;
import com.aurora.happy.annotation.VBefore;
import com.aurora.happy.annotation.VTest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by pijiang on 2021/3/17.
 * 通过注解实现方法执行顺序，before、test、after
 */
public class JunitTest {

    @VBefore
    void doBefore() {
        System.out.println("@VBefore");
    }

    @VTest
    void doTest2() {
        System.out.println("@VTest 2");
    }

    @VAfter
    void doAfter() {
        System.out.println("@VAfter");
    }

    @VTest
    void doTest() {
        System.out.println("@VTest 1");
    }

    public static void main(String[] args) {
        Map<String, Method> methodMap = new HashMap<>();

        //把注解分类的方法分组
        Class clazz = JunitTest.class;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(VBefore.class)) {
                methodMap.put("1_".concat(method.getName()), method);
            }else if (method.isAnnotationPresent(VAfter.class)){
                methodMap.put("3_".concat(method.getName()), method);
            }else {
                methodMap.put("2_".concat(method.getName()), method);
            }
            //排除main方法
            methodMap.remove("2_main");
        }

        //根据key排序执行
        try {
            List<Method> collect = methodMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            for (Method method : collect) {
                method.invoke(clazz.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}