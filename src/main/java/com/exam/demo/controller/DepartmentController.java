package com.exam.demo.controller;

import com.exam.demo.entity.Department;
import com.exam.demo.service.DepartmentService;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: gaoyk
 * @Date: 2022/3/1 12:55
 */
@RestController
@RequestMapping("department")
@Api(value="/department",tags={"部门操作接口"})
public class DepartmentController {
    @Autowired
    DepartmentService  departmentService;

    @GetMapping("findAll")
    @ApiOperation(notes = "gaoyk",value = "返回所有部门")
    public WebResult<List<Department>> findAll(){
        return WebResult.<List<Department>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(departmentService.findAll())
                .build();
    }
}
