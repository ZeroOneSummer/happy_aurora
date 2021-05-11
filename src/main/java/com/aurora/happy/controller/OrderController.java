package com.aurora.happy.controller;

import com.aurora.happy.annotation.ApiLog;
import com.aurora.happy.annotation.IgnoreApiLog;
import com.aurora.happy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by pijiang on 2021/4/12.
 */
@ApiLog
@Slf4j
@RestController
@RequestMapping("/user/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("getOrders")
    public List getOrders(String name){
        log.info("get requset params: {}", name);
        return orderService.getOrders();
    }

    @IgnoreApiLog
    @PostMapping("getOrders2")
    public List getOrders2(@RequestParam Map<String, Object> params){
        log.info("post requset params: {}", params);
        return orderService.getOrders();
    }
}