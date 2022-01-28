package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.service.ExamSubjectService;
import com.exam.demo.utils.Consts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("examSubject")
public class ExamSubjectController {

    @Autowired
    private ExamSubjectService examSubjectService;

    @RequestMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有主观题目")
    public Object findAll() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "主观题目查询成功");
        jsonObject.put(Consts.DATA, examSubjectService.findAll());
        return jsonObject;
    }

    @RequestMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询主观题目")
    public Object findById(@PathVariable Integer id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "主观题目查询成功");
        jsonObject.put(Consts.DATA, examSubjectService.findById(id));
        return jsonObject;
    }

    @RequestMapping("search")
    @ApiOperation(notes = "xiong",value = "根据条件查询主观题目")
    public Object search(@RequestBody ExamSubject subjectSearch) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "主观题目查询成功");
        jsonObject.put(Consts.DATA, examSubjectService.search(subjectSearch));
        return jsonObject;
    }

    @RequestMapping("save")
    @ApiOperation(notes = "xiong",value = "向题库添加主观题目")
    public Object saveExamJudge(@RequestBody ExamSubject examSubject) {
        examSubjectService.saveExamSubject(examSubject);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "主观题目添加成功");
        return jsonObject;
    }

    @RequestMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的主观题目")
    public Object updateExamJudge(@RequestBody ExamSubject examSubject) {
        examSubjectService.updateExamSubject(examSubject);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "主观题目修改成功");
        return jsonObject;
    }

    @RequestMapping("delete")
    @ApiOperation(notes = "xiong",value = "删除题库中的主观题目")
    public Object deleteExamJudge(@PathVariable Integer id) {
        examSubjectService.deleteExamSubject(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "主观题目删除成功");
        return jsonObject;
    }
}
