package com.aurora.happy.controller;

import com.aurora.happy.annotation.UserLog;
import com.aurora.happy.common.Result;
import com.aurora.happy.enums.ModuleEnum;
import com.aurora.happy.enums.OperationEnum;
import com.aurora.happy.pojo.User;
import com.aurora.happy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pijiang on 2021/5/11.
 * 用户操作日志保存
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

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