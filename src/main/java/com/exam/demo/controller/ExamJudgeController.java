package com.exam.demo.controller;

import com.exam.demo.entity.ExamJudge;
import com.exam.demo.service.ExamJudgeService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examJudge")
@Api(value="/examJudge",tags={"判断题目操作接口"})
public class ExamJudgeController {

    @Autowired
    private ExamJudgeService examJudgeService;

    @RequestMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有判断题目接口")
    public WebResult<List<ExamJudge>> findAll() {
        return WebResult.<List<ExamJudge>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.findAll())
                .build();
    }

    @RequestMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询判断接口")
    public WebResult<ExamJudge> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamJudge>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.findById(id))
                .build();
    }

    @RequestMapping("search")
    @ApiOperation(notes = "xiong",value = "根据条件查询判断题目接口")
    public WebResult<List<ExamJudge>> search(@RequestBody @ApiParam(name="judgeSearch",required=true) ExamJudge judgeSearch) {
        return WebResult.<List<ExamJudge>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.search(judgeSearch))
                .build();
    }

    @RequestMapping("save")
    @ApiOperation(notes = "xiong",value = "向题库添加判断题目接口")
    public WebResult<Integer> saveExamJudge(@RequestBody @ApiParam(name="examJudge",required=true) ExamJudge examJudge) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.saveExamJudge(examJudge))
                .build();
    }

    @RequestMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的判断题目接口")
    public WebResult<Integer> updateExamJudge(@RequestBody @ApiParam(name="examJudge",required=true) ExamJudge examJudge) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.updateExamJudge(examJudge))
                .build();
    }

    @RequestMapping("delete")
    @ApiOperation(notes = "xiong",value = "删除题库中的判断题目接口")
    public WebResult<Integer> deleteExamJudge(@PathVariable @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.deleteExamJudge(id))
                .build();
    }
}