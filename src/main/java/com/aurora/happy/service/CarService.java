package com.aurora.happy.service;

import com.aurora.happy.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by pijiang on 2021/4/12.
 * 物流-监听事件
 */
@Slf4j
@Service
public class CarService {

    @EventListener(OrderEvent.class)
    public void goCars() {
        log.info("【异步监听】物流系统，安排配送...");
    }
}