package com.exam.demo.controller;

import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.rtEntity.SelectQuestion;
import com.exam.demo.service.ExamSelectService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examSelect")
@Api(value="/examSelect",tags={"选择题目操作接口"})
public class ExamSelectController {

    @Autowired
    private ExamSelectService examSelectService;

    @GetMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有选择题目接口")
    public WebResult<List<SelectQuestion>> findAll() {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findAll())
                .build();
    }

    @GetMapping("findPage")
    @ApiOperation(notes = "xiong",value = "分页查询所有选择题目接口")
    public WebResult<List<SelectQuestion>> findPage(@RequestParam @ApiParam(name="currentPage") Integer currentPage,
                                                    @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findPage(currentPage, pageSize))
                .build();
    }

    @GetMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询选择题目接口")
    public WebResult<SelectQuestion> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<SelectQuestion>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findById(id))
                .build();
    }

    @PostMapping("search")
    @ApiOperation(notes = "xiong",value = "根据条件查询选择题目接口")
    public WebResult<List<SelectQuestion>> search(@RequestParam @ApiParam(name="current") Integer current,
                                              @RequestParam @ApiParam(name="pageSize") Integer pageSize,
                                              @RequestBody @ApiParam(name="queryQuestion") QueryQuestion queryQuestion) {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.search(current, pageSize, queryQuestion))
                .build();
    }

    @PostMapping("save")
    @ApiOperation(notes = "xiong",value = "向题库添加选择题目接口")
    public WebResult<Integer> saveExamSelect(@RequestBody @ApiParam(name="examSelect",required=true) ExamSelect examSelect) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.saveExamSelect(examSelect))
                .build();
    }

    @PostMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的选择题目接口")
    public WebResult<Integer> updateExamSelect(@RequestBody @ApiParam(name="examSelect",required=true) ExamSelect examSelect) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.updateExamSelect(examSelect))
                .build();
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(notes = "xiong",value = "删除题库中的选择题目接口")
    public WebResult<Integer> deleteExamSelect(@PathVariable @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.deleteExamSelect(id))
                .build();
    }
}