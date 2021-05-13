package com.aurora.happy.controller;

import com.aurora.happy.annotation.UserLog;
import com.aurora.happy.common.Result;
import com.aurora.happy.contanst.Contants;
import com.aurora.happy.enums.ExceptionCodeEnum;
import com.aurora.happy.enums.ModuleEnum;
import com.aurora.happy.enums.OperationEnum;
import com.aurora.happy.pojo.User;
import com.aurora.happy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by pijiang on 2021/5/11.
 * 用户操作日志保存
 */
@Slf4j
@RequestMapping("/user/")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public Result login(User loginUser, HttpServletRequest request) {
        User user = userService.selectOne(loginUser);
        if (user == null) return Result.error(ExceptionCodeEnum.NEED_LOGIN, "用户名或秘密错误");
        request.getSession().setAttribute(Contants.CURRENT_USER_IN_SESSION, user);
        return Result.success(user);
    }

    @UserLog(module = ModuleEnum.USER, title = "注册用户", type = OperationEnum.ADD)
    @PostMapping("register")
    public Result register(@RequestBody User userInfo) {
        int rows = userService.addUser(userInfo);
        if (rows > 0){
            log.info("新用户[{}]注册成功！", userInfo.getName());
            return Result.success();
        }
        return Result.error(ExceptionCodeEnum.NEED_REGISTER, "用户注册失败");
    }

    @GetMapping("loginOut")
    public Result loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute(Contants.CURRENT_USER_IN_SESSION);
        return Result.success(ExceptionCodeEnum.LOGIN_OUT);
    }


    @UserLog(module = ModuleEnum.USER, title = "新增用户", type = OperationEnum.ADD)
    @PostMapping("addUser")
    public Result addUser(@RequestBody User user) {
        userService.addUser(user);
        return Result.success(user);
    }

    @UserLog(module = ModuleEnum.USER, title = "更新用户", type = OperationEnum.MODIFY)
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success(user);
    }
}