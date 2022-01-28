package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.TestPaper;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.utils.Consts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testPaper")
public class TestPaperController {

    @Autowired
    private TestPaperService testPaperService;

    @RequestMapping("findAllTestPaper")
    @ApiOperation(notes = "xiong",value = "查询所有试卷信息")
    public Object findAllTestPaper() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "试卷信息查询成功");
        jsonObject.put(Consts.DATA, testPaperService.findAll());
        return jsonObject;
    }

    @RequestMapping("addTestPaper")
    @ApiOperation(notes = "xiong",value = "添加试卷头信息")
    public Object addTestPaper(TestPaper testPaper) {
        testPaperService.addTestPaper(testPaper);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "试卷信息添加成功");
        return jsonObject;
    }
}
