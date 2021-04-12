package com.aurora.happy.service;

import com.aurora.happy.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pijiang on 2021/4/12.
 * 订单-发布事件
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private ApplicationContext applicationContext;

    public int addOrder(String name){
        log.info("【发布事件】新申请单: {}", name);
        applicationContext.publishEvent(new OrderEvent(this));
        return 1;
    }

    public List getOrders(){
        return null;
    }
}