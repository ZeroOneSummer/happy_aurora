package com.aurora.happy;

import com.aurora.happy.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class WebAppTest {

    @Autowired
    private OrderService orderService;

    @Test
    void testOrder() {
        orderService.order();
    }

}
