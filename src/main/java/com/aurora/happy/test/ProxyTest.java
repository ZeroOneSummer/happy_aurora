package com.aurora.happy.test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pijiang on 2021/3/17.
 * 动态代理
 */
public class ProxyTest {

    interface Animal {
        void eat(String act);
        void show();
    }

    static class Dog implements Animal {
        @Override
        public void eat(String act) {
            System.out.println("eat food");
        }
        @Override
        public void show() {
            System.out.println("dog play.");
        }
    }

    public static void main(String[] args) throws Exception {
        Class clazz = ProxyTest.Dog.class;
        Animal o = (Animal)Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, arg) -> {
            System.out.println("代理前");
            method.invoke(clazz.newInstance(), arg);
            System.out.println("代理后");
            return null;
        });
        o.show();
    }


    //反射绕过泛型限制
//    public static void main(String[] args) throws Exception{
//        List<String> list = new ArrayList<>();
//        list.add("aaa");
//        list.add("bbb");
//        // 编译器会阻止
//        //list.add(333);
//
//        // 泛型约束只存在于编译期，底层仍是Object，运行期可以往List存入任何类型的元素
//        Method addMethod = list.getClass().getDeclaredMethod("add", Object.class);
//        addMethod.invoke(list, 333);
//
//        for (Object obj : list) {
//            System.out.println(obj);
//            //aaa bbb 333
//        }
//    }
}