/**
 * Created by 小机灵鬼儿
 * 2022/2/28 7:30
 */

package com.exam.demo.controller;
import com.exam.demo.entity.Onteach;
import com.exam.demo.service.OnteachService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: wxn
 * @Date: 2022/2/28
 */
@RestController
@RequestMapping("onteach")
@Api(value="/onteach",tags={"线上授课"})
public class OnteachController {
    @Autowired
    OnteachService onteachService;

    @GetMapping("/select")
    @ApiOperation(notes = "wxn",value = "信息查询接口")
    public WebResult<List<Onteach>> select(){
        return  WebResult.<List<Onteach>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(onteachService.selectAll())
                .build();

    }

    @PostMapping("/insert")
    @ApiOperation(notes = "wxn",value = "插入课程接口")
    public WebResult<Integer> insert(@RequestParam @ApiParam(name = "crouse_name") String crouseName,
                                     @RequestParam @ApiParam(name = "assessment") double assessment,
                                     @RequestParam @ApiParam(name = "get") double get,
                                     @RequestParam @ApiParam(name = "crouse_time") double crouseTime,
                                     @RequestParam @ApiParam(name = "study_time") double studyTime,
                                     @RequestParam @ApiParam(name = "begin_time") @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
                                     @RequestParam @ApiParam(name = "end_time") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                     @RequestParam @ApiParam(name = "subject_id") Integer subject_id,
                                     @RequestParam @ApiParam(name = "department_id") Integer department_id){
        Onteach onteach = new Onteach();
        onteach.setSubject_id(subject_id);
        onteach.setDepartment_id(department_id);
        onteach.setCrouse_name(crouseName);
        onteach.setAssessment(assessment);
        onteach.setGet(get);
        onteach.setCrouse_time(crouseTime);
        onteach.setStudy_time(studyTime);
        onteach.setBegin_time(beginTime);
        onteach.setEnd_time(endTime);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(onteachService.insert(onteach))
                .build();
    }

    @PostMapping("/update")
    @ApiOperation(notes = "wxn",value = "修改信息接口")
    public WebResult<Integer> update(@RequestParam @ApiParam(name = "crouse_name") String crouseName,
                                     @RequestParam @ApiParam(name = "assessment") double assessment,
                                     @RequestParam @ApiParam(name = "get") double get,
                                     @RequestParam @ApiParam(name = "crouse_time") double crouseTime,
                                     @RequestParam @ApiParam(name = "study_time") double studyTime,
                                     @RequestParam @ApiParam(name = "begin_time") @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
                                     @RequestParam @ApiParam(name = "end_time") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                     @RequestParam @ApiParam(name = "subject_id") Integer subject_id,
                                     @RequestParam @ApiParam(name = "department_id") Integer department_id){
        Onteach onteach = new Onteach();
        onteach.setSubject_id(subject_id);
        onteach.setDepartment_id(department_id);
        onteach.setCrouse_name(crouseName);
        onteach.setAssessment(assessment);
        onteach.setGet(get);
        onteach.setCrouse_time(crouseTime);
        onteach.setStudy_time(studyTime);
        onteach.setBegin_time(beginTime);
        onteach.setEnd_time(endTime);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(onteachService.update(onteach))
                .build();
    }

    @DeleteMapping("/delete")
    @ApiOperation(notes = "wxn",value = "删除课程接口")
    public WebResult<Integer> delete(@RequestParam @ApiParam(name="subject_id") Integer subject_id){
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(onteachService.delete(subject_id))
                .build();

    }
}
