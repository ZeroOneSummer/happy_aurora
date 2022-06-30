package com.aurora.happy.controller;

import com.aurora.happy.annotation.CosmoController;
import com.aurora.happy.annotation.IgnoreCosmoResult;
import com.aurora.happy.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pijiang on 2021/5/11.
 * 关于CosmoController的自动封装响应
 */
@CosmoController
@RequestMapping("/resp/")
@Slf4j
public class ResponseController {

    @GetMapping("getUser")
    public User getUser(){
        return new User(1001L,"linda", 20);
    }

    @GetMapping("getUser2")
    @IgnoreCosmoResult
    public User getUser2(){
        return new User(1001L,"linda", 20);
    }
}