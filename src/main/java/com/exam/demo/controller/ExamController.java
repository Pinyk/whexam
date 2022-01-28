package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.Exam;
import com.exam.demo.service.ExamService;
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
}
