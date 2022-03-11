package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.*;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.otherEntity.UserAnswer;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.results.vo.TestpaperVo;
import com.exam.demo.service.ExamService;
import com.exam.demo.service.ScoreService;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("exam")
@Api(value="/exam",tags={"考试操作接口"})
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private TestPaperService testPaperService;

    @GetMapping("findByTestPaperId")
    @ApiOperation(notes = "xiong",value = "根据试卷ID查询试卷的所有试题接口")
    public WebResult<Map<String, List<Object>>> findByTestPaperId(@RequestParam @ApiParam(name="testPaperId",required=true) Integer testPaperId) {
        return WebResult.<Map<String, List<Object>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examService.findByTestPaperId(testPaperId))
                .build();
    }

    @PostMapping("submitTest")
    @ApiOperation(notes = "xiong",value = "试卷提交接口")
    public WebResult<Integer> submitTest(@RequestBody @ApiParam(name="userAnswer",required=true) UserAnswer userAnswer) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(1)
                .data(examService.submitTest(userAnswer.getTestPaperId(), userAnswer.getUserId(), userAnswer))
                .build();
    }

    @PostMapping("addProblem")
    @ApiOperation(notes = "xiong",value = "添加试卷试题接口")//一道一道题目的添加
    public WebResult<Integer> addProblem(@RequestBody @ApiParam(name="exam",required=true,value = "id传入null") Exam exam) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examService.addProblem(exam))
                .build();
    }

    @PostMapping("componentTestPaper")
    @ApiOperation(notes = "LBX",value = "组建试卷试题接口")
    public WebResult<Integer> componentTestPaper(@RequestParam @ApiParam(name="testPaperId",required=true) Integer testPaperId,
                                        @RequestParam @ApiParam(name="subjectId",required=true) Integer subjectId,
                                        @RequestParam @ApiParam(name="judgeCount",required=true) Integer judgeCount,
                                        @RequestParam @ApiParam(name="singleCount",required=true) Integer singleCount,
                                        @RequestParam @ApiParam(name="multipleCount",required=true) Integer multipleCount,
                                        @RequestParam @ApiParam(name="subjectCount",required=true) Integer subjectCount) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examService.randomComponentPaper(testPaperId, subjectId, judgeCount, singleCount, multipleCount, subjectCount))
                .build();
    }

    @DeleteMapping("deleteProblem/{id}")
    @ApiOperation(notes = "xiong",value = "删除试卷试题接口")
    public WebResult<Integer> deleteProblem(@PathVariable @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examService.deleteProblem(id))
                .build();
    }

    @GetMapping("findTesting")
    @ApiOperation(notes = "xiong",value = "查询正在考试试卷接口")
    public WebResult<List<RtTestpaper>> findTesting() {
        return WebResult.<List<RtTestpaper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findTesting())
                .build();
    }

    @GetMapping("findTested")
    @ApiOperation(notes = "xiong",value = "查询历史考试试卷接口")
    public WebResult<List<RtTestpaper>> findTested() {
        return WebResult.<List<RtTestpaper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findTested())
                .build();
    }

    @GetMapping("findNotStartTest")
    @ApiOperation(notes = "xiong",value = "查询尚未开始的考试试卷接口")
    public WebResult<List<RtTestpaper>> findNotStartTest() {
        return WebResult.<List<RtTestpaper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findNotStartTest())
                .build();
    }

    @GetMapping("findDetail")
    @ApiOperation(notes = "LBX",value = "查询考试成绩详情接口")
    public WebResult<PageVo<JSONObject>> findDetail(
            @RequestParam @ApiParam(name = "currentPage") Integer currentPage,
            @RequestParam @ApiParam(name = "sizePage") Integer sizePage,
            @RequestParam @ApiParam(name="testPaperId") Integer testPaperId) {
        if (currentPage == null && sizePage == null && testPaperId == null) {
            return WebResult.<PageVo<JSONObject>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<PageVo<JSONObject>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(scoreService.findByTestPaperId(testPaperId, currentPage, sizePage))
                .build();
    }

    @GetMapping("findByUserId")
    @ApiOperation(notes = "xiong",value = "查询用户考试成绩详情接口")
    public WebResult<List<UserTestPaperScore>> findByUserId(@RequestParam @ApiParam(name="userId",required=true) Integer userId) {
        return WebResult.<List<UserTestPaperScore>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(scoreService.findByUserId(userId))
                .build();
    }


    @GetMapping("findTestPaperDetail")
    @ApiOperation(notes = "LBX", value = "组合查询试卷（试卷ID，试卷名字，试卷所属部门，科目）")
    public WebResult<List<TestpaperVo>> combinedQueryTestPaper(@RequestParam(required = false) @ApiParam(name = "试卷id", required = true) Integer testPaperId,
                                                               @RequestParam(required = false) @ApiParam(name = "试卷名称", required = true) String testPaperName,
                                                               @RequestParam(required = false) @ApiParam(name = "试卷所属部门", required = true) String departmentName,
                                                               @RequestParam(required = false) @ApiParam(name = "科目", required = true) String subject) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examService.combinedQueryTestPaper(testPaperId, testPaperName, departmentName, subject))
                .build();
    }

    @PostMapping("updateScoreByUserId")
    @ApiOperation(notes = "xiong",value = "根据用户ID和试卷ID修改试卷总分接口")
    public WebResult<Integer> updateScoreByUserId(@RequestParam @ApiParam(name="testPaperId",required=true) Integer testPaperId,
                                                  @RequestParam @ApiParam(name="userId",required=true) Integer userId,
                                                  @RequestParam @ApiParam(name="scoreNum",required=true) Double scoreNum) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examService.updateScoreByUserId(scoreNum, testPaperId, userId))
                .build();
    }

    @GetMapping("findScoreDetailByUIdAndTPId")
    @ApiOperation(notes = "xiong",value = "根据用户ID和试卷ID查询考试明细接口")
    public WebResult<List<Map<String, Object>>> findScoreDetailByUIdAndTPId(@RequestParam @ApiParam(name="testPaperId",required=true) Integer testPaperId,
                                                                            @RequestParam @ApiParam(name="userId",required=true) Integer userId) {
        return WebResult.<List<Map<String, Object>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examService.findScoreDetailByUIdAndTPId(testPaperId, userId))
                .build();
    }
}