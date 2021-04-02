package com.aurora.happy.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by pijiang on 2021/4/2.
 * springboot task
 */
@Component
@EnableScheduling
@Slf4j
public class BootTaskTest {

    @Scheduled(cron = "*/5 * * * * ?")    //每5秒执行一次
//    @Scheduled(fixedDelay = 5*1000L)        //上一次结束5秒后
//    @Scheduled(fixedRate = 5*1000L)         //上一次开始5秒后，默认单线程，会阻塞
//    @Scheduled(initialDelay = 10*1000L, fixedDelay = 5*1000L)       //服务启动10秒后
    public void task1(){
        log.info("task1 start...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("task2 end.");
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void task2(){
        log.info("task2 start...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("task2 end.");
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void task3(){
        log.info("task3 start...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("task3 end.");
    }

    //JDK任务
    public static void main(String[] args) {
        //2秒后开始，每5秒一次
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("jdk task start...");
            }
        }, 2*1000L, 5*1000L);
    }
}