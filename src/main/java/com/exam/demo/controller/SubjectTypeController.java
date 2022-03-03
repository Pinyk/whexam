package com.exam.demo.controller;

import com.exam.demo.entity.Study;
import com.exam.demo.entity.SubjectType;
import com.exam.demo.results.WebResult;
import com.exam.demo.service.SubjectTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: csx
 * @Date: 2022/3/3
 */
@RestController
@RequestMapping("subjecttype")
@Api(value="/subject",tags={"科目类型操作接口"})
public class SubjectTypeController {

    @Autowired
    private SubjectTypeService subjectTypeService;

    @GetMapping("findAll")
    @ApiOperation(notes = "gaoyk",value = "返回所有部门")
    public WebResult<List<SubjectType>> findAll(){
        return WebResult.<List<SubjectType>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(subjectTypeService.findAll())
                .build();
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(notes = "csx",value = "插入科目学习类型接口")
    public WebResult<Integer> add(@RequestParam @ApiParam(name="name") String name){

        SubjectType subjectType=new SubjectType();
        subjectType.setName(name);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(subjectTypeService.add(subjectType))
                .build();
    }
    @GetMapping("/findById")
    @ApiOperation(notes = "csx",value = "按id查询接口")
    public WebResult<SubjectType> findById(@RequestParam @ApiParam(name="id") Integer id){


        return WebResult.<SubjectType>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(subjectTypeService.findById(id))
                .build();
    }


}
