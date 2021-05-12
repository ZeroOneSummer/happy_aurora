package com.aurora.happy.controller;

import com.aurora.happy.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by pijiang on 2021/5/12.
 */
@Slf4j
@Controller
public class HomeController {

    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("user", new User(1001L, "一条鱼", 0));
        return "index";
    }

    @GetMapping("demo")
    public String demo() {
        return "demo";
    }
}