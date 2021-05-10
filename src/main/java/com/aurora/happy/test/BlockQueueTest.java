package com.aurora.happy.test;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pijiang on 2021/5/10.
 * 阻塞队列
 */
public class BlockQueueTest {

    public static void main(String[] args) {
//        WQ_Test();  //轮询-阻塞队列
//        WN_Test();  //Synchronized-阻塞队列
        LK_Test();  //ReentrantLock-阻塞队列
    }

    /**
     * 轮询 - 实现
     */
    static class WhileQueue<T> {
        private final LinkedList<T> queue = new LinkedList<>();

        private void put(T t) throws InterruptedException {
            while (queue.size() >= 1) {
                System.out.println("生产者：队列已满");
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            queue.addFirst(t);
            System.out.println("生产者：插入" + t);
        }

        public void get() throws InterruptedException {
            while (queue.size() <= 0) {
                System.out.println("消费者：队列为空");
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            T t = queue.removeLast();
            System.out.println("消费者：消费" + t);
        }
    }

    /**
     * Synchronized - 实现
     */
    static class WaitNotifyQueue<T> {
        private final LinkedList<T> queue = new LinkedList<>();

        private synchronized void put(T t) throws InterruptedException {
            while (queue.size() >= 1) {
                System.out.println("生产者：队列已满");
                this.wait();
            }
            queue.addFirst(t);
            System.out.println("生产者：插入" + t);
            //this.notify(); //随机唤醒，容易出现生产者相互wait
            this.notifyAll();
        }

        public synchronized void get() throws InterruptedException {
            while (queue.size() <= 0) {
                System.out.println("消费者：队列为空");
                this.wait();
            }
            T t = queue.removeLast();
            System.out.println("消费者：消费" + t);
            this.notifyAll();
        }
    }

    /**
     * ReentrantLock - 实现
     */
    static class LockQueue<T> {
        private final LinkedList<T> queue = new LinkedList<>();
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition producerCondition = lock.newCondition();
        private final Condition consumerCondition = lock.newCondition();

        private void put(T t) throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() >= 1) {
                    System.out.println("生产者：队列已满");
                    producerCondition.await();
                }
                queue.addFirst(t);
                System.out.println("生产者：插入" + t);
                consumerCondition.signal();
            } finally {
                lock.unlock();
            }
        }

        public void get() throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() <= 0) {
                    System.out.println("消费者：队列为空");
                    consumerCondition.await();
                }
                T t = queue.removeLast();
                System.out.println("消费者：消费" + t);
                producerCondition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void WQ_Test(){
        WhileQueue<String> wq = new WhileQueue<>();

        //生产者
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    wq.put("messege_" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //消费者
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    wq.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void WN_Test(){
        WaitNotifyQueue<String> wn = new WaitNotifyQueue<>();

        //生产者
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    wn.put("messege_" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //生产者
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    wn.put("messege_" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //消费者
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    wn.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void LK_Test(){
        LockQueue<String> lk = new LockQueue<>();

        //生产者
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    lk.put("messege_" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //消费者
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    lk.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}