package com.aurora.happy.test;

import com.aurora.happy.annotation.Flower;
import com.aurora.happy.controller.AnnotationController;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationTest {

    public static void main(String[] args) throws Exception{
        Class<AnnotationController> clazz = AnnotationController.class;
        Method sayBye = clazz.getDeclaredMethod("SayBye");

        //method
        Flower method1 = sayBye.getDeclaredAnnotation(Flower.class);

        //method by util
        Flower method2 = AnnotationUtils.findAnnotation(sayBye, Flower.class);
        System.out.println(Arrays.toString(method1.subName()) + "\n" + Arrays.toString(method2.subName()));

        //class
        Flower annoClass1 = clazz.getAnnotation(Flower.class);

        //class by util
        Flower annoClass2 = AnnotationUtils.findAnnotation(AnnotationController.class, Flower.class);
        System.out.println(annoClass1.name() + "\n"+ annoClass2.name());
    }
}