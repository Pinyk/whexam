package com.exam.demo.controller;

import com.exam.demo.service.TestPaperService;
import com.exam.demo.utils.TestManageParam;
import com.exam.demo.utils.TestpaperVo;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("testmangement")
@Api(value = "/testmangement", tags = {"考试管理模块接口"})
public class TestManagementController {

    @Autowired
    private TestPaperService testPaperService;

    @GetMapping("findAllCurrentExam")
    @ApiOperation(notes = "LBX", value = "查询所有正在进行的考试")
    public WebResult<List<TestpaperVo>> findAllCurrentExam() {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAllCurrentExam())
                .build();
    }

    @PostMapping("findCurrentExam")
    @ApiOperation(notes = "LBX", value = "正在考试——组合查询")
    public WebResult<List<TestpaperVo>> findCurrentExam(@RequestBody TestManageParam param) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findCurrentExam(param.getTestPaperId(), param.getTestPaperName(),
                        param.getDepartmentName(), param.getSubject(), param.getCurrentPage(), param.getPageSize()))
                .build();
    }

    @PostMapping("findCurrentExamByPage")
    @ApiOperation(notes = "LBX", value = "分页查询所有正在进行的考试")
    public WebResult<List<TestpaperVo>> findCurrentExamByPage(@RequestParam @ApiParam(name="currentPage", value = "当前页码", required = true) Integer currentPage,
                                                              @RequestParam @ApiParam(name="pageSize", value = "每页容量", required = true) Integer pageSize) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findCurrentExamByPage(currentPage, pageSize))
                .build();
    }

    @GetMapping("findAllHistoricalExam")
    @ApiOperation(notes = "LBX", value = "查询所有历史考试")
    public WebResult<List<TestpaperVo>> findAllHistoricalExam() {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAllHistoricalExam())
                .build();
    }

    @GetMapping("findHistoricalExam")
    @ApiOperation(notes = "LBX", value = "历史考试——组合查询")
    public WebResult<List<TestpaperVo>> findHistoricalExam(@RequestParam(required = false) @ApiParam(name = "testPaperId", value = "试卷id", required = true) Integer testPaperId,
                                                           @RequestParam(required = false) @ApiParam(name = "testPaperName", value = "试卷名称", required = true) String testPaperName,
                                                           @RequestParam(required = false) @ApiParam(name = "departmentName", value = "试卷所属部门", required = true) String departmentName,
                                                           @RequestParam(required = false) @ApiParam(name = "subject", value = "科目", required = true) String subject) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findHistoricalExam(testPaperId, testPaperName, departmentName, subject))
                .build();
    }

    @GetMapping("findAllFutureExam")
    @ApiOperation(notes = "LBX", value = "查询所有未来进行的考试")
    public WebResult<List<TestpaperVo>> findAllFutureExam() {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAllFutureExam())
                .build();
    }

    @GetMapping("findFutureExam")
    @ApiOperation(notes = "LBX", value = "未来考试——组合查询")
    public WebResult<List<TestpaperVo>> findFutureExam(@RequestParam(required = false) @ApiParam(name = "testPaperId", value = "试卷id", required = true) Integer testPaperId,
                                                       @RequestParam(required = false) @ApiParam(name = "testPaperName", value = "试卷名称", required = true) String testPaperName,
                                                       @RequestParam(required = false) @ApiParam(name = "departmentName", value = "试卷所属部门", required = true) String departmentName,
                                                       @RequestParam(required = false) @ApiParam(name = "subject", value = "科目", required = true) String subject) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findFutureExam(testPaperId, testPaperName, departmentName, subject))
                .build();
    }
}
