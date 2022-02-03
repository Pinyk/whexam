package com.exam.demo.controller;

import com.exam.demo.entity.User;
import com.exam.demo.service.UserService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: gaoyk
 * @Date: 2022/1/20 22:56
 * 用户控制器
 */
@RestController
@RequestMapping("user")
@Api(value="/user",tags={"用户操作接口"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("loginWx")
    @ApiOperation(notes = "gaoyk",value = "微信小程序登录接口")
    public WebResult<User> loginWx(@RequestBody @ApiParam(name="用户对象",required=true,
            value = "传入json格式") User user){
        User msg = userService.loginWx(user);
        return WebResult.<User>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(msg)
                .build();
    }
}
