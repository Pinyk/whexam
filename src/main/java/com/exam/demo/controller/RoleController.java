package com.exam.demo.controller;
import com.exam.demo.service.RoleService;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.utils.Consts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gaoyk
 * @Date: 2022/1/19 13:46
 * 角色控制器
 */

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("findAll")
    @ApiOperation(notes = "gaoyk",value = "全部角色查询接口")
    public Object findAll(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "角色查询成功");
        jsonObject.put(Consts.DATA,roleService.findAll());
        return jsonObject;
    }
}
