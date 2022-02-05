package com.exam.demo.controller;

import com.exam.demo.entity.TestPaper;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("testPaper")
@Api(value="/testPaper",tags={"试卷卷头操作接口"})
public class TestPaperController {

    @Autowired
    private TestPaperService testPaperService;

    @GetMapping("findAllTestPaper")
    @ApiOperation(notes = "xiong",value = "查询所有试卷信息接口")
    public WebResult<List<TestPaper>> findAllTestPaper() {
        return WebResult.<List<TestPaper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAll())
                .build();
    }

    @PostMapping("addTestPaper")
    @ApiOperation(notes = "xiong",value = "添加试卷头信息接口")
    public WebResult<Integer> addTestPaper(@RequestParam @ApiParam(name="testPaper",required=true) TestPaper testPaper) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.addTestPaper(testPaper))
                .build();
    }
}