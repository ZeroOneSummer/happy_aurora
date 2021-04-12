package com.aurora.happy;

import com.aurora.happy.base.WebAppTest;
import com.aurora.happy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by pijiang on 2021/4/12.
 */
@Slf4j
public class OrderTest extends WebAppTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testOrder() {
        orderService.addOrder("李宁");
    }
}