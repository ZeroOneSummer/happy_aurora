package com.aurora.happy.controller;

import com.aurora.happy.annotation.LoginRequired;
import com.aurora.happy.contanst.Contants;
import com.aurora.happy.i18n.I18nMessageUtils;
import com.aurora.happy.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by pijiang on 2021/5/12.
 */
@Slf4j
@Controller
public class HomeController {

    /**
     *需要登陆才能访问
     */
    @LoginRequired
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("user", ThreadLocalUtil.get(Contants.USER_INFO));
//        model.addAttribute("user", new User(3L, "Aurora", 20));
        return "index";
    }

    @GetMapping("demo")
    public String demo() {
        return "demo";
    }

    /**
     * jsp
     */
    @GetMapping("home.do")
    public String toHome(Model model) {
        log.info("国际化 - zero：" + I18nMessageUtils.get("ty.zll"));
        log.info("国际化 - aurora：" + I18nMessageUtils.get("aurora.title"));
        log.info("当前语言：" + LocaleContextHolder.getLocale());
        model.addAttribute("userNamer", "秋白");
        return "home";
    }
}