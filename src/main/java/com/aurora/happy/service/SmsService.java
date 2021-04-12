package com.aurora.happy.service;

import com.aurora.happy.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Created by pijiang on 2021/4/12.
 * 消息-监听事件
 */
@Slf4j
@Service
public class SmsService implements ApplicationListener<OrderEvent> {

    @Override
    public void onApplicationEvent(OrderEvent orderEvent) {
        log.info("生成订单，发送成功消息...");
    }
}