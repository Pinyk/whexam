package com.exam.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.Study;
import com.exam.demo.service.StudyService;
import com.exam.demo.utils.Consts;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    @GetMapping("/findBySubject")
    @ApiOperation(notes = "csx",value = "安学习类型查询接口")
    public WebResult<List<Study>> findBySubject(@RequestParam @ApiParam(name="datatype") Integer datatype){


        return WebResult.<List<Study>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.findBySubject(datatype))
                .build();
    }
    //按科目类型查询
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

//    //增加新的学习任务
//    @PostMapping("/insert")
//    @ApiOperation(notes = "csx",value = "插入课程接口")
//    public WebResult<Integer> insert(@RequestParam @ApiParam(name="id") Integer id,
//                      @RequestParam @ApiParam(name="name") String name,
//                      @RequestParam @ApiParam(name="datatype_id") Integer datatype_id,
//                      @RequestParam @ApiParam(name="url") String url,
//                      @RequestParam @ApiParam(name= "subject_id",defaultValue = "0") Integer subject_id,
//                      @RequestParam @ApiParam(name="department_id") Integer departement_id){
//        Study study=new Study();
//        study.setId(id);
//        study.setName(name);
//        study.setDepartment_id(datatype_id);
//        study.setUrl(url);
//        study.setSubject_id(subject_id);
//        study.setDatatype_id(datatype_id);
//        return WebResult.<Integer>builder()
//                .code(200)
//                .message(REQUEST_STATUS_SUCCESS)
//                .data(studyService.insert(study))
//                .build();
//    }

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
//    添加学习资料


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(notes = "csx",value = "插入学习资料接口")
    public Object addSong(@RequestParam @ApiParam(name="id") Integer id,
                          @RequestParam @ApiParam(name="name") String name,
                          @RequestParam @ApiParam(name="datatype_id") Integer datatype_id,
                          @RequestParam @ApiParam(name="subject_id",defaultValue = "0") Integer subject_id,
                          @RequestParam @ApiParam(name="department_id") Integer department_id,
                          @RequestParam("file") MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
//        int id = Integer.parseInt(request.getParameter("StudyId").trim());  //学习资料ID
//        String name = request.getParameter("name").trim();    //学习资料名称
//        int datatype_id = Integer.parseInt(request.getParameter("datatype_id").trim());          //所属学习类型
//        String url = "/img/songPic/tubiao.jpg";                     //url
//        int subject_id = Integer.parseInt(request.getParameter("subject_id").trim());     //所属学科id
//        int department_id = Integer.parseInt(request.getParameter("department_id").trim());     //所属资料部门id
        //上传歌曲文件

        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"资料上传失败");
            return jsonObject;
        }

        try {
            //文件名=当前时间到毫秒+原来的文件名
            String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
            //文件路径
            String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"study";
            //如果文件路径不存在，新增该路径
            File file1 = new File(filePath);
            if(!file1.exists()){
                file1.mkdir();
            }
            //实际的文件地址
            File dest = new File(filePath+System.getProperty("file.separator")+fileName);
            //存储到数据库里的相对文件地址
            String url = "/study/"+fileName;
//
//            if (!dest.getParentFile().exists()) {
//                dest.getParentFile().mkdirs();
//            }

            Study study=new Study();
            study.setId(id);
            study.setName(name);
            study.setDepartment_id(department_id);
            study.setUrl(url);
            study.setSubject_id(subject_id);
            study.setDatatype_id(datatype_id);
//            int flag = studyService.insert(study);
//            mpFile.transferTo(dest);

//                jsonObject.put(Consts.CODE,1);
//                jsonObject.put(Consts.MSG,"保存成功");
//                jsonObject.put("avator",storeUrlPath);
//                return jsonObject;
//            System.out.println("1");
            studyService.insert(study);
            mpFile.transferTo(dest);
            return WebResult.<Integer>builder()
                    .code(200)
                    .message(REQUEST_STATUS_SUCCESS)
                    .data(1)
                    .build();
                   } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
            return jsonObject;
        }catch (Exception e){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"新增失败"+e.getMessage());
            return jsonObject;
        }

    }



}
