package com.exam.demo.controller;

import com.exam.demo.entity.Power;
import com.exam.demo.entity.Role;
import com.exam.demo.service.RoleService;
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

    @DeleteMapping("deleteById")
    @ApiOperation(notes = "gaoyk",value = "根据Id删除角色")
    public WebResult<Boolean> deleteById(@RequestParam @ApiParam(name="id",required=true)
                                                 Integer id){
        Integer i = roleService.deleteById(id);
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
    @GetMapping("findRole")
    @ApiOperation(notes="liu",value="根据条件查找角色")
    public  WebResult<List<Power>> findRole(@ApiParam("String nums")@RequestParam  String nums, @ApiParam(" String name")@RequestParam String name,@ApiParam("String department") @RequestParam String department,@ApiParam("String rolename") @RequestParam String rolename){
        return WebResult.<List<Power>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(roleService.findRole(nums, name, department, rolename))
                .build();
    }


}
