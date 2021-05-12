package com.aurora.happy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by pijiang on 2021/5/12.
 */
@Slf4j
@Controller
public class HomeController {

    @GetMapping("index")
    public String index() {
        return "html/index";
    }
}