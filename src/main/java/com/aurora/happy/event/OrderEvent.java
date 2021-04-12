package com.aurora.happy.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by pijiang on 2021/4/12.
 * 事件
 */
public class OrderEvent extends ApplicationEvent {

    public OrderEvent(Object source) {
        super(source);
    }
}