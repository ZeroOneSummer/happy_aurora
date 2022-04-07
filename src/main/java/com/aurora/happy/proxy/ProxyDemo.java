package com.aurora.happy.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*接口*/
interface Animal{
    void run();
}

/*实现类*/
class Cat implements Animal{
    @Override
    public void run(){
        System.out.println("喵星人在奔跑。。。");
    }
}

/*非实现类*/
class Dog {
    void run(){
        System.out.println("汪星人在奔跑。。。");
    }
}

/**
 * JDK动态代理
 * 原理：通过改造目标类Class字节码文件，生成代理子类，并重写其方法
 * 适用范围：有接口实现
 */
class JdkProxyTest{

    public static void main(String[] args) {
        Animal animal = (Animal)Proxy.newProxyInstance(JdkProxyTest.class.getClassLoader(), Cat.class.getInterfaces(),
                (proxy, method, arg) -> {
                    System.out.println("---before---");
                    Object obj = method.invoke(new Cat(), arg);
                    System.out.println("---after---");
                    return obj;
                }
        );
        animal.run();
    }
}

/**
 * CGlib动态代理
 * 原理：通过改造目标类Class字节码文件，生成代理子类，并重写其方法
 * 适用范围：无接口实现、非final修饰方法
 */
class CglibProxyTest{

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dog.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("---before---");
                Object obj = methodProxy.invokeSuper(o, objects);
                System.out.println("---after---");
                return obj;
            }
        });
        Dog dog = (Dog)enhancer.create();
        dog.run();
    }
}
