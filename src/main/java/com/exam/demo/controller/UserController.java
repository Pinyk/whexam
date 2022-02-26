package com.exam.demo.controller;

import com.exam.demo.entity.User;
import com.exam.demo.otherEntity.UserPojo;
import com.exam.demo.service.UserService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_ERROR;
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
            value = "传入[openid][wxname][image][gender]参数") User user){
        User msg = userService.loginWx(user);
        return WebResult.<User>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(msg)
                .build();
    }

    @PostMapping("loginWeb")
    @ApiOperation(notes = "gaoyk",value = "管理端登录接口")
    public WebResult<User> loginWeb(@RequestBody @ApiParam(name="用户对象",required=true,
            value = "传入[name][password]参数") User user){
        User msg = userService.loginWeb(user);
        if (msg == null){
            return WebResult.<User>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }else {
            return WebResult.<User>builder()
                    .code(200)
                    .message(REQUEST_STATUS_SUCCESS)
                    .data(msg)
                    .build();
        }
    }

    @PostMapping("updateUser")
    @ApiOperation(notes = "gaoyk",value = "修改用户信息接口")
    public WebResult<Boolean> updateUser(@RequestBody @ApiParam(name="用户对象",required=true,
            value = "传入用户当前所有json数据") User user){
        Boolean msg = userService.updateUser(user);
        return WebResult.<Boolean>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(msg)
                .build();
    }

    @GetMapping("findAll")
    @ApiOperation(notes = "gaoyk",value = "返回所有用户")
    public WebResult<List<User>> findAll(){
        return WebResult.<List<User>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(userService.findAll())
                .build();
    }

    @GetMapping("findPart")
    @ApiOperation(notes = "gaoyk",value = "返回所有用户部分信息")
    public WebResult<List<UserPojo>> findPart(){
        return WebResult.<List<UserPojo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(userService.findPart())
                .build();
    }

    @DeleteMapping("deleteById")
    @ApiOperation(notes = "gaoyk",value = "根据Id删除用户")
    public WebResult<Boolean> deleteById(@RequestParam @ApiParam(name="id",required=true)
                                                     Integer id){
        Integer i = userService.deleteById(id);
        if (i != 1){
            return WebResult.<Boolean>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .data(false)
                    .build();
        }else {
            return WebResult.<Boolean>builder()
                    .code(200)
                    .data(true)
                    .message(REQUEST_STATUS_SUCCESS)
                    .build();
        }
    }

    @PostMapping("grantRole")
    @ApiOperation(notes = "gaoyk",value = "更改权限")
    public WebResult<Boolean> grantRole(@RequestParam @ApiParam(name="user_id",required=true)
                                                Integer user_id){
        Boolean msg = userService.grantRole(user_id);
        return WebResult.<Boolean>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(msg)
                .build();
    }

}
