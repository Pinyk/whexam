package com.exam.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.course.Study;
import com.exam.demo.service.course.StudyService;
import com.exam.demo.utils.Consts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @Author: csx
 * @Date: 2022/1/23
 * 学习控制器
 */
@RestController
@RequestMapping ("/study")
public class StudyController {
    @Autowired
    private StudyService studyService;

    //查询全部

    @GetMapping("/findAll")
    public List<Study> findAll(){

        return studyService.findAll();
    }

    //分页查询

    @GetMapping("findPage")
    public List<Study> findPage(@RequestParam("page") Integer page,
                                @RequestParam("pageSize") Integer pageSize){
        return studyService.findPage(page,pageSize);
    }

    //按学习类型查询
    @GetMapping("/findByType")
    public List<Study> findByType(@RequestParam("datatype") Integer datatype){


        return studyService.findByType(datatype);
    }

    //根据id进行删除
    @DeleteMapping("/delete")
    public int delete(@RequestParam("study_id") Integer study_id){
        return studyService.delete(study_id);
    }

    //增加新的学习任务
    @PostMapping("/insert")
    public int insert(@RequestParam("id") Integer id,
                      @RequestParam("name") String name,
                      @RequestParam("datatype_id") Integer datatype_id,
                      @RequestParam("url") String url,
                      @RequestParam(value = "subject_id",defaultValue = "0") Integer subject_id,
                      @RequestParam("department_id") Integer departement_id){
        Study study=new Study();
        study.setId(id);
        study.setName(name);
        study.setDepartment_id(datatype_id);
        study.setUrl(url);
        study.setSubject_id(subject_id);
        study.setDatatype_id(datatype_id);
        return studyService.insert(study);
    }

    //修改学习任务
    @PostMapping("/updata")
    public int update2(@RequestParam("id") Integer id,
                       @RequestParam("name") String name,
                       @RequestParam("datatype_id") Integer datatype_id,
                       @RequestParam("url") String url,
                       @RequestParam(value = "subject_id",defaultValue = "0") Integer subject_id,
                       @RequestParam("department_id") Integer departement_id){

        Study study=new Study();
        study.setId(id);
        study.setName(name);
        study.setDepartment_id(datatype_id);
        study.setUrl(url);
        study.setSubject_id(subject_id);
        study.setDatatype_id(datatype_id);
        return studyService.update(study);

    }
}
