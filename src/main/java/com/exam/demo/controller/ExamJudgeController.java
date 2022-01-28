package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.ExamJudge;
import com.exam.demo.service.ExamJudgeService;
import com.exam.demo.utils.Consts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("examJudge")
public class ExamJudgeController {

    @Autowired
    private ExamJudgeService examJudgeService;

    @RequestMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有判断题目")
    public Object findAll() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "判断题目查询成功");
        jsonObject.put(Consts.DATA, examJudgeService.findAll());
        return jsonObject;
    }

    @RequestMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询判断题目")
    public Object findById(@PathVariable Integer id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "判断题目查询成功");
        jsonObject.put(Consts.DATA, examJudgeService.findById(id));
        return jsonObject;
    }

    @RequestMapping("search")
    @ApiOperation(notes = "xiong",value = "根据条件查询判断题目")
    public Object search(@RequestBody ExamJudge judgeSearch) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "判断题目查询成功");
        jsonObject.put(Consts.DATA, examJudgeService.search(judgeSearch));
        return jsonObject;
    }

    @RequestMapping("save")
    @ApiOperation(notes = "xiong",value = "向题库添加判断题目")
    public Object saveExamJudge(@RequestBody ExamJudge examJudge) {
        examJudgeService.saveExamJudge(examJudge);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "判断题目添加成功");
        return jsonObject;
    }

    @RequestMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的判断题目")
    public Object updateExamJudge(@RequestBody ExamJudge examJudge) {
        examJudgeService.updateExamJudge(examJudge);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "判断题目修改成功");
        return jsonObject;
    }

    @RequestMapping("delete")
    @ApiOperation(notes = "xiong",value = "删除题库中的判断题目")
    public Object deleteExamJudge(@PathVariable Integer id) {
        examJudgeService.deleteExamJudge(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "判断题目删除成功");
        return jsonObject;
    }
}
