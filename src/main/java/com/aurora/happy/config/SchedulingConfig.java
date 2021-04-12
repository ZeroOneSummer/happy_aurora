package com.aurora.happy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by pijiang on 2021/4/2.
 * springboot task 线程池配置
 */
@Component
@Slf4j
public class SchedulingConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //task线程
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(3); //可改为配置
        taskScheduler.setThreadNamePrefix("SchedulingConfig-Aurora-Task-");
        /**
         *  CallerRunsPolicy：不使用异步线程，直接主线程执行
         *  AbortPolicy：丢弃当前任务，直接抛异常
         *  DiscardPolicy：丢弃当前任务，无声无息（不抛异常）
         *  DiscardOldestPolicy：丢弃队列里最老的任务，执行当前任务
         *  taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
         */
        taskScheduler.setRejectedExecutionHandler(
                (r, executor) -> log.info("警告邮件: 任务卡爆了, 当前线程名称为:{}, 当前线程池队列长度为:{}", r.toString(), executor.getQueue().size()));
        taskScheduler.initialize();
        //注册
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }
}