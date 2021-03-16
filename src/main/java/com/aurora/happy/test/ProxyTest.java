package com.aurora.happy.test;

import java.lang.reflect.Proxy;

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

    public static void main(String[] args) throws Exception{
        Class clazz = ProxyTest.Dog.class;
        Animal o = (Animal)Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (p, m, a) -> {
            System.out.println("代理前");
            m.invoke(clazz.newInstance(), a);
            System.out.println("代理后");
            return null;
        });
        o.show();
    }
}