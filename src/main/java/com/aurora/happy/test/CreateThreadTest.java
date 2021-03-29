package com.aurora.happy.test;

import java.util.concurrent.*;

/**
 * Created by pijiang on 2021/3/29.
 * 多线程创建
 */
public class CreateThreadTest {

    public static void main(String[] args) throws Exception{
//        createMethod_1();
//        createMethod_2();
//        createMethod_3();
//        createMethod_4();
        createMethod_5();
    }

    /**
     * 1.重写Thread的run方法
     */
    static void createMethod_1(){
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"，已执行。。。");
            }
        }.start();
    }

    /**
     * 2.lambda表达式
     */
    static void createMethod_2(){
        new Thread(() -> System.out.println(Thread.currentThread().getName()+"，已执行。。。")).start();
    }

    /**
     * 3.线程池 + Future
     */
    static void createMethod_3() throws Exception{
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> submit = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "，已执行。。。");
            return "ok";
        });
        String rs = submit.get();  //获取回调结果，并阻塞main线程
        System.out.println("callable: " + rs);
        executorService.shutdown();
        System.out.println(Thread.currentThread().getName() + "，已执行。。。");
    }

    /**
     * 4.Runnable接口 + 内部写法
     */
    static void createMethod_4() throws Exception{
        new MyThread().begin();     //常规写法：new Thread(new MyThread()).start();
    }

    static class MyThread implements Runnable {
        MyThread() {}

        MyThread(MyThread myThread) {}

        void begin(){
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "，已执行。。。");
        }
    }

    /**
     * 5.Runnable接口 + 内部写法
     */
    static void createMethod_5() throws Exception{
        Callable<String> callable = () -> {
            Thread.sleep(2000);  //模拟异步任务耗时
            System.out.println(Thread.currentThread().getName() + "，已执行。。。");
            return "ok";
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();

        //主线程继续干活
        System.out.println("main work 0.5s");
        Thread.sleep(500);
        System.out.println("main work 1.0s");
        Thread.sleep(500);

        //实时获取任务执行结果
        for (;;) {
            if (futureTask.isDone()) {
                System.out.println("async task result: " + futureTask.get());
                break;
            }else
                System.out.println("async task is executing, please wait.");
        }
        System.out.println("task end.");
    }
}