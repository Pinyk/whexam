package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.service.ExamSubjectService;
import com.exam.demo.utils.Consts;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examSubject")
@Api(value="/examSubject",tags={"主观题目操作接口"})
public class ExamSubjectController {

    @Autowired
    private ExamSubjectService examSubjectService;

    @RequestMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有主观题目接口")
    public WebResult<List<ExamSubject>> findAll() {
        return WebResult.<List<ExamSubject>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.findAll())
                .build();
    }

    @RequestMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询主观题目接口")
    public WebResult<ExamSubject> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamSubject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.findById(id))
                .build();
    }

    @RequestMapping("search")
    @ApiOperation(notes = "xiong",value = "根据条件查询主观题目接口")
    public WebResult<List<ExamSubject>> search(@RequestBody @ApiParam(name="subjectSearch",required=true) ExamSubject subjectSearch) {
        return WebResult.<List<ExamSubject>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.search(subjectSearch))
                .build();
    }

    @RequestMapping("save")
    @ApiOperation(notes = "xiong",value = "向题库添加主观题目接口")
    public WebResult<Integer> saveExamSubject(@RequestBody @ApiParam(name="examSubject",required=true) ExamSubject examSubject) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.saveExamSubject(examSubject))
                .build();
    }

    @RequestMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的主观题目接口")
    public WebResult<Integer> updateExamSubject(@RequestBody @ApiParam(name="examSubject",required=true) ExamSubject examSubject) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.updateExamSubject(examSubject))
                .build();
    }

    @RequestMapping("delete")
    @ApiOperation(notes = "xiong",value = "删除题库中的主观题目接口")
    public WebResult<Integer> deleteExamSubject(@PathVariable @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.deleteExamSubject(id))
                .build();
    }
}
