package com.exam.demo.controller;
import com.exam.demo.entity.Subject;
import com.exam.demo.service.SubjectService;
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
 * @Date: 2022/3/1 13:00
 */
@RestController
@RequestMapping("subject")
@Api(value="/subject",tags={"科目操作接口"})
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("findAll")
    @ApiOperation(notes = "gaoyk",value = "返回所有部门")
    public WebResult<List<Subject>> findAll(){
        return WebResult.<List<Subject>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(subjectService.findAll())
                .build();
    }
}
