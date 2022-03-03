package com.exam.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.exam.demo.Utils.FileCommit;
import com.exam.demo.entity.Datatype;
import com.exam.demo.entity.ShowStudy;
import com.exam.demo.entity.Study;
import com.exam.demo.service.*;
import com.exam.demo.results.Consts;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: csx,wxn
 * @Date: 2022/1/23
 * 学习控制器
 */
@RestController
@RequestMapping ("study")
@Api(value="/study",tags={"学习操作接口"})
public class StudyController {
    @Autowired
    private StudyService studyService;
    @Autowired
    private DataTypeService dataTypeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectTypeService subjectTypeService;

    @GetMapping("findAll")
    @ApiOperation(notes = "csx",value = "全部学习资料查询接口")

    public WebResult<List<ShowStudy>> findAll(){



        List<Study> study=studyService.findAll();
        ArrayList<ShowStudy> showStudies=new ArrayList<>();
//        while (study.isEmpty()) ;
        Iterator<Study> it = study.iterator();
        while(it.hasNext()){//判断是否有迭代元素
            Study study1= it.next();

            ShowStudy showStudy=new ShowStudy();
            showStudy.setId(study1.getId());
            showStudy.setName(study1.getName());
//            int id=it.next().getSubjectid();
            showStudy.setDatatype_id(study1.getDatatypeid());
            Datatype datatype=dataTypeService.findById(study1.getDatatypeid());
            String name=(datatype.getName());
            showStudy.setDatatype_name(name);
            showStudy.setUrl(study1.getUrl());
            showStudy.setBeizhu(study1.getBeizhu());
            showStudy.setDepartment_id(study1.getDepartmentid());
            showStudy.setDepartment_name(departmentService.findById(study1.getDepartmentid()).getName());
            showStudy.setSubject_id(study1.getSubjectid());
            showStudy.setSubject_name(subjectService.findById(study1.getSubjectid()).getName());
            showStudy.setTypename(subjectTypeService.findById(study1.getTypeid()).getName());
            showStudies.add(showStudy);
        }

        return  WebResult.<List<ShowStudy>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(showStudies)
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
    public WebResult<List<Study>> findBySubject(@RequestParam @ApiParam(name="subject") Integer subject){






        return WebResult.<List<Study>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.findBySubject(subject))
                .build();
    }


    //按科目查询
    @GetMapping("/findByType")
    @ApiOperation(notes = "csx",value = "课程类型查询接口")
    public WebResult<List<Study>> findByType(@RequestParam @ApiParam(name="datatype") Integer datatype){






        return WebResult.<List<Study>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.findByType(datatype))
                .build();
    }
    //按科目类型查询
    @GetMapping("/findByTypeid")
    @ApiOperation(notes = "csx",value = "学科小类查询接口")
    public WebResult<List<List<Study>>> findByTypeId(@RequestParam @ApiParam(name="typeid") Integer typeid){
//
//
//        ArrayList<ArrayList<Study>> arrayLists=new ArrayList<>();
        List<List<Study>> arrayLists=new ArrayList<>();
        List<Datatype> datatypes=dataTypeService.findAll();
        List<Study> studies=studyService.findBySubjectType(typeid);
        Iterator<Study> studyIterator = studies.iterator();
        Iterator<Datatype> datatypeIterator = datatypes.iterator();
        while (datatypeIterator.hasNext()){
            Datatype datatype=datatypeIterator.next();
            List<Study> studies1=new LinkedList<>();
            arrayLists.add(studies1);
        }

        while(studyIterator.hasNext()){//判断是否有迭代元素
            Study study=studyIterator.next();
            Iterator<Datatype> datatypeIterator1 = datatypes.iterator();
            while (datatypeIterator1.hasNext()){
                Datatype datatype=datatypeIterator1.next();

                if(datatype.getId()==study.getDatatypeid()){
                    arrayLists.get(datatype.getId()-1).add(study);
                    break;
                }

            }
        }
        return WebResult.<List<List<Study>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(arrayLists)
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
    @PostMapping("/update")
    @ApiOperation(notes = "csx",value = "更新课程信息接口")
    public WebResult<Integer> update2(@RequestParam @ApiParam(name="id") Integer id,
                       @RequestParam @ApiParam(name="name") String name,
                       @RequestParam @ApiParam(name="datatype_id") Integer datatype_id,
                       @RequestParam @ApiParam(name="url") String url,
                       @RequestParam @ApiParam(name="subject_id",defaultValue = "0") Integer subject_id,
                       @RequestParam @ApiParam(name="department_id") Integer departement_id,
                       @RequestParam @ApiParam(name="time") String time){

        Study study=new Study();
        study.setId(id);
        study.setName(name);
        study.setDepartmentid(datatype_id);
        study.setUrl(url);
        study.setSubjectid(subject_id);
        study.setDatatypeid(datatype_id);
        study.setTime(time);

        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(studyService.update(study))
                .build();

    }
//    添加学习资料


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(notes = "csx",value = "插入学习资料接口")
    public Object add(@RequestParam @ApiParam(name="name") String name,
                          @RequestParam @ApiParam(name="datatype_id") Integer datatype_id,
                          @RequestParam @ApiParam(name="subject_id",defaultValue = "0") Integer subject_id,
                          @RequestParam @ApiParam(name="department_id") Integer department_id,
                          @RequestParam("file") MultipartFile mpFile,
                          @RequestParam @ApiParam(name="time") String time,
                      @RequestParam @ApiParam(name="typeid") Integer typeid){
        JSONObject jsonObject = new JSONObject();

        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"资料上传失败");
            return jsonObject;
        }
//        System.out.println("+++++++++++++++++++++++++++");
        try {
            //文件名=当前时间到毫秒+原来的文件名
//            String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
            //文件路径
//            String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"study";
            FileCommit fileCommit = new FileCommit();
            String url = fileCommit.uploadFileAvatar(mpFile);
//            System.out.println("===================="+url);

//            OSSClient ossClient = new OSSClient("https://oss-cn-beijing.aliyuncs.com", "LTAI5tGtGgwJkpyb9UDrAPj7", "gTTvD1103beS004i2Cv9fCumY0JftH");
//            // 关闭client
//            ossClient.shutdown();
//            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
//            String url1 = ossClient.generatePresignedUrl("xiaoningya", mpFile.getOriginalFilename(), expiration).toString();
//            System.out.println("===================="+url1);
            //实际的文件地址
//            File dest = new File(filePath+System.getProperty("file.separator")+fileName);
//            File dest = new File(filePath);
//            System.out.println("==================="+filePath);
            //存储到数据库里的相对文件地址
//            String url = "/study/"+fileName;

            Study study=new Study();
            study.setName(name);
            study.setDepartmentid(department_id);
            study.setUrl(url);
            study.setSubjectid(subject_id);
            study.setDatatypeid(datatype_id);
            study.setTime(time);
            study.setTypeid(typeid);
            this.studyService.insert(study);
//            mpFile.transferTo(dest);
            return WebResult.<Integer>builder()
                    .code(200)
                    .message(REQUEST_STATUS_SUCCESS)
                    .data(1)
                    .build();
                   } catch (Exception e){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"新增失败"+e.getMessage());
            return jsonObject;
        }
    }

    @RequestMapping(value = "/findFile",method = RequestMethod.GET)
    @ApiOperation(notes = "wxn",value = "查询文件接口")
    public String findFile(@RequestParam("file") MultipartFile mpFile){
        OSSClient ossClient = new OSSClient("https://oss-cn-beijing.aliyuncs.com",
                "LTAI5tGtGgwJkpyb9UDrAPj7", "gTTvD1103beS004i2Cv9fCumY0JftH");
        // 关闭client
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl("xiaoningya", mpFile.getOriginalFilename(), expiration).toString();
//        System.out.println("===================="+url);
        return url;
    }

}
