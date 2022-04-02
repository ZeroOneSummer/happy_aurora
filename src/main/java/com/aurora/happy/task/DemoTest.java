package com.aurora.happy.task;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Created by pijiang on 2022/4/2.
 */
public class DemoTest {
    private static final long start = 0, end = 10_0000_0000L;

    public static void main(String[] args) throws Exception{
        System.out.println(String.format("从%d加到%d耗时：", start, end));
        doTask_1();
        doTask_2();
    }

    static void doTask_1() throws Exception{
        long t0 = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(DemoTest.start, DemoTest.end)
                .parallel()
                .reduce(0, Long::sum);
        System.out.println(sum);
        System.out.println("stream: " + (System.currentTimeMillis() - t0) + " ms");
    }

    static void doTask_2() throws Exception{
        long t0 = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> submit = forkJoinPool.submit(new CalcTask(DemoTest.start, DemoTest.end));
        System.out.println(submit.get());
        System.out.println("forkjoin: " + (System.currentTimeMillis() - t0) + " ms");
    }

    public static class CalcTask extends RecursiveTask<Long> {
        private long start;
        private long end;
        private static final long temp = (DemoTest.end-DemoTest.start)/2; //中间值

        private CalcTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if ((end-start) < temp) {
                long sum = 0L;
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            }else {
                long midde = (end-start)/2;
                ForkJoinTask<Long> fork1 = new CalcTask(start, midde).fork();
                ForkJoinTask<Long> fork2 = new CalcTask(midde+1, end).fork();
                return fork1.join() + fork2.join();
            }

        }
    }
}