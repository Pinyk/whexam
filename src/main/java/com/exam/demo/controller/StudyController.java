package com.exam.demo.controller;


import com.exam.demo.entity.Study;
import com.exam.demo.service.StudyService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: csx
 * @Date: 2022/1/23
 * 学习控制器
 */
@RestController
@RequestMapping ("study")
@Api(value="/study",tags={"学习操作接口"})
public class StudyController {
    @Autowired
    private StudyService studyService;


    @GetMapping("findAll")
    @ApiOperation(notes = "csx",value = "全部课程查询接口")

    public WebResult<List<Study>> findAll(){
        return  WebResult.<List<Study>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.findAll())
                .build();

    }

    //分页查询

    @GetMapping("findPage")
    @ApiOperation(notes = "csx",value = "按页数查询接口")
    public WebResult<List<Study>> findPage(@RequestParam @ApiParam(name="page") Integer page,
                                @RequestParam @ApiParam(name="pageSize") Integer pageSize){
        return WebResult.<List<Study>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.findPage(page,pageSize))
                .build();
    }

    //按学习类型查询
    @GetMapping("/findByType")
    @ApiOperation(notes = "csx",value = "课程类型查询接口")
    public WebResult<List<Study>> findByType(@RequestParam @ApiParam(name="datatype") Integer datatype){


        return WebResult.<List<Study>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.findByType(datatype))
                .build();
    }

    //根据id进行删除
    @DeleteMapping("/delete")
    @ApiOperation(notes = "csx",value = "删除课程接口")
    public WebResult<Integer> delete(@RequestParam @ApiParam(name="study_id") Integer study_id){
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.delete(study_id))
                .build();

    }

    //增加新的学习任务
    @PostMapping("/insert")
    @ApiOperation(notes = "csx",value = "插入课程接口")
    public WebResult<Integer> insert(@RequestParam @ApiParam(name="id") Integer id,
                      @RequestParam @ApiParam(name="name") String name,
                      @RequestParam @ApiParam(name="datatype_id") Integer datatype_id,
                      @RequestParam @ApiParam(name="url") String url,
                      @RequestParam @ApiParam(name= "subject_id",defaultValue = "0") Integer subject_id,
                      @RequestParam @ApiParam(name="department_id") Integer departement_id){
        Study study=new Study();
        study.setId(id);
        study.setName(name);
        study.setDepartment_id(datatype_id);
        study.setUrl(url);
        study.setSubject_id(subject_id);
        study.setDatatype_id(datatype_id);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.insert(study))
                .build();
    }

    //修改学习任务
    @PostMapping("/updata")
    @ApiOperation(notes = "csx",value = "更新课程信息接口")
    public WebResult<Integer> update2(@RequestParam @ApiParam(name="id") Integer id,
                       @RequestParam @ApiParam(name="name") String name,
                       @RequestParam @ApiParam(name="datatype_id") Integer datatype_id,
                       @RequestParam @ApiParam(name="url") String url,
                       @RequestParam @ApiParam(name="subject_id",defaultValue = "0") Integer subject_id,
                       @RequestParam @ApiParam(name="department_id") Integer departement_id){

        Study study=new Study();
        study.setId(id);
        study.setName(name);
        study.setDepartment_id(datatype_id);
        study.setUrl(url);
        study.setSubject_id(subject_id);
        study.setDatatype_id(datatype_id);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.update(study))
                .build();

    }
}
