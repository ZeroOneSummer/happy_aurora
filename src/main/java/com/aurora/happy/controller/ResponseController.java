package com.aurora.happy.controller;

import com.aurora.happy.annotation.CosmoController;
import com.aurora.happy.annotation.IgnoreCosmoResult;
import com.aurora.happy.i18n.MessageUtils;
import com.aurora.happy.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pijiang on 2021/5/11.
 * 关于CosmoController的自动封装响应
 */
@CosmoController
@RequestMapping("/resp/")
public class ResponseController {

    @GetMapping("getUser")
    public User getUser(){
        System.out.println("国际化：" + MessageUtils.get("ty.zll"));
        return new User(1001L,"linda", 20);
    }

    @GetMapping("getUser2")
    @IgnoreCosmoResult
    public User getUser2(){
        return new User(1001L,"linda", 20);
    }
}