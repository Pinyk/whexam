package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.Testpaper;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.results.vo.TestpaperVo2;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("/testPaper")
@Api(value="/testPaper",tags={"试卷及卷头操作接口"})
public class TestPaperController {

    @Autowired
    private TestPaperService testPaperService;

    @GetMapping("findAllTestPaper")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "查询所有试卷信息接口")
    public WebResult<List<RtTestpaper>> findAllTestPaper() {
        return WebResult.<List<RtTestpaper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAll())
                .build();
    }
//=========================================根据试卷id查询试卷详情============================================================
    @GetMapping("findTestPaperById")
    @ApiOperation(notes = "YXY", value = "根据试卷id查询试卷详情")
    public WebResult<List<Map<String, Object>>> findTestPaperById(@RequestParam int testPaperId) {
        return WebResult.<List<Map<String, Object>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findTestPaperById(testPaperId))
                .build();
    }
//=============================================查询卷头==============================================================
    @PostMapping("findCurrentTestPaperHead")
    @ApiOperation(notes = "YXY", value = "查询进行中考试试卷的卷头")
    public WebResult<List<Map<String, Object>>> findCurrentTestPaperHead(Integer userId) {
        return WebResult.<List<Map<String, Object>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findCurrentTestPaperHead(userId))
                .build();
    }

    @PostMapping("findHistorialTestPaperHead")
    @ApiOperation(notes = "YXY", value = "查询历史考试试卷的卷头")
    public WebResult<List<Map<String, Object>>> findHistorialTestPaperHead(Integer userId) {
        return WebResult.<List<Map<String, Object>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findHistorialTestPaperHead(userId))
                .build();
    }

//=========================================组建试卷试题接口=================================================================
    @PostMapping("componentTestPaper")
    @Transactional
    @ApiOperation(notes = "LBX",value = "组建试卷试题接口")
    public WebResult<Map<String, Object>> componentTestPaper(@RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return WebResult.<Map<String, Object>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<Map<String, Object>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.componentTestPaper(jsonObject))
                .build();
    }
}