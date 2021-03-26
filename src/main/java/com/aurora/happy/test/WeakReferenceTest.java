package com.aurora.happy.test;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * Created by pijiang on 2021/3/26.
 * 弱引用，可被GC回收
 */
public class WeakReferenceTest {

    public static void main(String[] args) throws Exception{
        Car car = new Car("奔驰");
        WeakReference<Car> carWeak = new WeakReference<>(car);

        System.gc();
        System.out.println("GC后，强引用+弱引用："+carWeak.get()); //GC后，强引用：com.aurora.happy.test.WeakReferenceTest$Car@1ddc4ec2

        car = null;
        TimeUnit.SECONDS.sleep(3);
        System.out.println("切断强引用，弱引用："+carWeak.get()); //弱引用：com.aurora.happy.test.WeakReferenceTest$Car@1ddc4ec2

        System.gc();
        System.out.println("GC后，弱引用："+carWeak.get());   //GC后，弱引用：null
    }

    static class Car{
        private String name;

        Car(String name) {
            this.name = name;
        }
    }
}