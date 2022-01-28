package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.service.ExamSelectService;
import com.exam.demo.utils.Consts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ExamSelect")
public class ExamSelectController {

    @Autowired
    private ExamSelectService examSelectService;

    @RequestMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有选择题目")
    public Object findAll() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "选择题目查询成功");
        jsonObject.put(Consts.DATA, examSelectService.findAll());
        return jsonObject;
    }

    @RequestMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询选择题目")
    public Object findById(@PathVariable Integer id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "选择题目查询成功");
        jsonObject.put(Consts.DATA, examSelectService.findById(id));
        return jsonObject;
    }

    @RequestMapping("search")
    @ApiOperation(notes = "xiong",value = "根据条件查询选择题目")
    public Object search(@RequestBody ExamSelect examSelect) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "选择题目查询成功");
        jsonObject.put(Consts.DATA, examSelectService.search(examSelect));
        return jsonObject;
    }

    @RequestMapping("save")
    @ApiOperation(notes = "xiong",value = "向题库添加选择题目")
    public Object saveExamJudge(@RequestBody ExamSelect examSelect) {
        examSelectService.saveExamSelect(examSelect);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "选择题目添加成功");
        return jsonObject;
    }

    @RequestMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的选择题目")
    public Object updateExamJudge(@RequestBody ExamSelect examSelect) {
        examSelectService.updateExamSelect(examSelect);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "选择题目修改成功");
        return jsonObject;
    }

    @RequestMapping("delete")
    @ApiOperation(notes = "xiong",value = "删除题库中的选择题目")
    public Object deleteExamJudge(@PathVariable Integer id) {
        examSelectService.deleteExamSelect(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "选择题目删除成功");
        return jsonObject;
    }
}