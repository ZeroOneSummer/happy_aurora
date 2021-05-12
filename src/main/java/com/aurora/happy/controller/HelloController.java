package com.aurora.happy.controller;

import com.aurora.happy.common.Result;
import com.aurora.happy.pojo.UserPojo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pijiang on 2021/3/11.
 */
@Api(tags = "用户接口")
@Slf4j
@RequestMapping("/hello/")
@RestController
public class HelloController {

    @ApiResponses({
            @ApiResponse(code = -1, message = "网络错误"),
            @ApiResponse(code = 0, message = "无操作权限"),
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 911, message = "token无效")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "名字", dataType = "String", paramType = "query")
    })
    @ApiOperation(value = "获取用户信息")
    @GetMapping("getUser")
    public Result getUser(@RequestParam Long id,
                          @RequestParam String name) {

        UserPojo user = new UserPojo().setAge(18).setId(id).setUserType(1).setName(name);
        log.info("user: {}", user);
        return Result.success(user);
    }


    @ApiOperation(value = "添加用户信息", hidden = true)
    @GetMapping("addUser")
    public Result addUser() {

        log.info("用户已添加");
        return Result.success();
    }
}