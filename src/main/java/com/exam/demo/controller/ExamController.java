package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.Exam;
import com.exam.demo.service.ExamService;
import com.exam.demo.service.ScoreService;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.utils.Consts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private TestPaperService testPaperService;

    @RequestMapping("findByTestPaperId")
    @ApiOperation(notes = "xiong",value = "根据试卷ID查询试卷的所有试题")
    public Object findByTestPaperId(@PathVariable Integer testPaperId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "试卷试题查询成功");
        jsonObject.put(Consts.DATA, examService.findByTestPaperId(testPaperId));
        return jsonObject;
    }

    @RequestMapping("addProblem")
    @ApiOperation(notes = "xiong",value = "添加试卷试题接口")
    public Object addProblem(@RequestBody Exam exam) {
        examService.addProblem(exam);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "试卷试题添加成功");
        return jsonObject;
    }

    @RequestMapping("deleteProblem")
    @ApiOperation(notes = "xiong",value = "删除试卷试题接口")
    public Object deleteProblem(@PathVariable Integer id) {
        examService.deleteProblem(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "试卷试题删除成功");
        return jsonObject;
    }

    @RequestMapping("findTesting")
    @ApiOperation(notes = "xiong",value = "查询正在考试试卷接口")
    public Object findTesting() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "正在考试试卷查询成功");
        jsonObject.put(Consts.DATA, testPaperService.findTesting());
        return jsonObject;
    }

    @RequestMapping("findTested")
    @ApiOperation(notes = "xiong",value = "查询历史考试试卷接口")
    public Object findTested() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "历史考试试卷查询成功");
        jsonObject.put(Consts.DATA, testPaperService.findTested());
        return jsonObject;
    }

    @RequestMapping("find")
    @ApiOperation(notes = "xiong",value = "查询考试详情接口")
    public Object find详情(@PathVariable Integer testPaperId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "考试详情查询成功");
        jsonObject.put(Consts.DATA, scoreService.findByTestPaperId(testPaperId));
        return jsonObject;
    }

    @RequestMapping("findByUserId")
    @ApiOperation(notes = "xiong",value = "查询用户考试详情接口")
    public Object findByUserId(@PathVariable Integer userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "用户考试详情查询成功");
        jsonObject.put(Consts.DATA, scoreService.findByUserId(userId));
        return jsonObject;
    }
}
