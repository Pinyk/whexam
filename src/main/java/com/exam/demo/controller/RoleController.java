package com.exam.demo.controller;

import com.exam.demo.entity.Role;
import com.exam.demo.service.RoleService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: gaoyk
 * @Date: 2022/1/19 13:46
 * 角色控制器
 */

@RestController
@RequestMapping("role")
@Api(value="/role",tags={"角色操作接口"})
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("findAll")
    @ApiOperation(notes = "gaoyk",value = "全部角色查询接口")
    public WebResult<List<Role>> findAll(){
        return WebResult.<List<Role>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(roleService.findAll())
                .build();
    }

    @PostMapping("addRole")
    @ApiOperation(notes = "gaoyk",value = "新增角色接口")
    public WebResult<Integer> addRole(@RequestParam @ApiParam(name="name",required=true)
                                         String name){
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(roleService.addRole(name))
                .build();
    }

    @GetMapping("findById")
    @ApiOperation(notes = "gaoyk",value = "查找角色接口")
    public WebResult<Role> addRole(@RequestParam @ApiParam(name="id",required=true)
                                              Integer id){
        return WebResult.<Role>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(roleService.findById(id))
                .build();
    }



}
