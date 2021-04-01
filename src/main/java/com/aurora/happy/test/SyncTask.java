package com.aurora.happy.test;

import java.util.concurrent.*;

/**
 * 参考：https://blog.csdn.net/qq_31865983/article/details/106137777
 * Created by pijiang on 2021/3/22.
 * 异步任务
 */
public class SyncTask {

    /**
     * 1.executorService.execute()
     * 执行顺序：mian -> 子thead
     * 打印结果：
     *      main thread
     *      execute task
     */
    static void doTask_1(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("execute task");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    /**
     * 2.executorService.submit(Runnable)，void
     *   executorService.submit(Callable)，返回值
     * 执行顺序(get方法阻塞main)：子thead(异常时，阻塞) -> mian
     * 打印结果：
     *      execute task
     *      true
     *      main thread
     */
    static void doTask_2(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("execute task");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        });

        try {
            Object o = future.get();
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    /**
     * 3.CompletableFuture.runAsync(Runnable)，void异步
     * 执行顺序：子thread(异步执行) -> main
     * 打印结果：
     *      main thread
     */
    static void doTask_3(){
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("execute task");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //completableFuture.get(); //阻塞main
    }

    /**
     * 4.CompletableFuture.supplyAsync(Supplier)，返回值异步
     * 执行顺序：子thread(异步执行) -> main
     * 打印结果：
     *      execute task
     *      true
     *      main thread
     */
    static void doTask_4(){
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("execute task");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        });

        try {
            Object o =  completableFuture.get();
            System.out.println(o);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5.CompletableFuture.supplyAsync(Supplier)，返回值异步
     *   completableFuture.thenApplyAsync(Function)，返回值回调
     * 执行顺序：子thread1 -> 子thread2 -> main
     * 打印结果：
     *      task 1
     *      task 2: task1 result -> 5
     *      task2 is ok
     *      main thread
     */
    static void doTask_5(){
        ForkJoinPool pool = new ForkJoinPool();     //线程池
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("task 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        }, pool);

        //TODO 回调任务中的参数rs为task1的返回值
        CompletableFuture<String> completableFuture2 = completableFuture.thenApplyAsync((rs) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("task 2: task1 result -> " + rs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task2 is ok";
        });

        try {
            //TODO get()阻塞主线程，等子线程执行完再执行main
            Object o =  completableFuture2.get();
            System.out.println(o);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 6.CompletableFuture.supplyAsync(Supplier)，返回值异步
     *   completableFuture.thenApply(Function)，返回值回调，上个任务返回值作入参
     *   completableFuture.thenAccept(Consumer)，void返回值，上个任务返回值作入参
     *   completableFuture.thenRun(Runnable)，无参void，
     * 执行顺序：子thread1 -> 子thread2 -> 子thread3 -> 子thread4 -> main
     * 打印结果：
     *      task 1
     *      task2: 5
     *      task3: task2 is ok
     *      task4 is over
     *      null
     *      main thread
     */
    static void doTask_6(){
        ForkJoinPool pool = new ForkJoinPool();     //线程池
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("task 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        }, pool)
        .thenApply((rs) -> {
            System.out.println("task2: "+rs);
            return "task2 is ok";
        })
        .thenAccept((rs) -> {
            System.out.println("task3: "+rs);
        })
        .thenRun(() -> {
            System.out.println("task4 is over");
        });

        try {
            //TODO get()阻塞主线程，等子线程执行完再执行main
            Object o =  completableFuture.get();
            System.out.println(o);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 7.CompletableFuture.supplyAsync(Supplier)，返回值异步
     * 执行顺序：子thread1 -> 子thread2 -> 子thread3 -> 子thread4 -> main
     * 打印结果：
     *      task 1
     *      task2: 5
     *      task3: task2 is ok
     *      task4 is over
     *      null
     *      main thread
     */
    static void doTask_7(){

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //task1
        Future<String> T1 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Runnable task start...");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Runnable task result");

        //task2
        Future<String> T2 = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Callable task start...");
                TimeUnit.SECONDS.sleep(2);
                return "Callable task result";
            }
        });

        //轮询
        boolean isOverRunnable = false;
        boolean isOverCallable = false;
        do {
            if (T1.isDone()) {
                try {
                    System.out.println("poll Runnable rs: " + T1.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                isOverRunnable = true;
            }
            if (T2.isDone()) {
                try {
                    System.out.println("poll Callable rs: " + T2.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                isOverCallable = true;
            }
        } while (!isOverRunnable || !isOverCallable);
    }

    public static void main(String[] args) {
//        SyncTask.doTask_1();
//        SyncTask.doTask_2();
//        SyncTask.doTask_3();
//        SyncTask.doTask_4();
//        SyncTask.doTask_5();
//        SyncTask.doTask_6();
        SyncTask.doTask_7();
        System.out.println("main: task is over.");
    }
}