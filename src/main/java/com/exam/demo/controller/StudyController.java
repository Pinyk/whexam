package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.Utils.FileCommit;
import com.exam.demo.Utils.URLtoUTF8;
import com.exam.demo.entity.Datatype;
import com.exam.demo.entity.ShowStudy;
import com.exam.demo.entity.Study;
import com.exam.demo.entity.StudyByType;
import com.exam.demo.service.*;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.DataTypeService;
import com.exam.demo.service.DepartmentService;
import com.exam.demo.service.StudyService;
import com.exam.demo.service.SubjectService;
import com.exam.demo.results.Consts;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.plugin2.message.Message;


import java.lang.annotation.Inherited;
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
    public WebResult<PageVo<Study>> findPage(@RequestParam @ApiParam(name="page") Integer page,
                                @RequestParam @ApiParam(name="pageSize") Integer pageSize){
        return WebResult.<PageVo<Study>>builder()
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
    public WebResult<List<StudyByType>> findByTypeId(@RequestParam @ApiParam(name="typeid") Integer typeid){

        List<StudyByType> list=new LinkedList<>();

        List<Datatype> datatypes=dataTypeService.findAll();
        List<Study> studies=studyService.findBySubjectType(typeid);
        Iterator<Study> studyIterator = studies.iterator();
        Iterator<Datatype> datatypeIterator = datatypes.iterator();
        while (datatypeIterator.hasNext()){
            Datatype datatype=datatypeIterator.next();
            List<Study> studies1=new LinkedList<>();

            StudyByType studyByType=new StudyByType();
            studyByType.setId(datatype.getId());
            studyByType.setData(studies1);
            list.add(studyByType);



        }

        while(studyIterator.hasNext()){//判断是否有迭代元素
            Study study=studyIterator.next();
            Iterator<Datatype> datatypeIterator1 = datatypes.iterator();
            while (datatypeIterator1.hasNext()){
                Datatype datatype=datatypeIterator1.next();

                if(datatype.getId()==study.getDatatypeid()){


                    list.get(datatype.getId()-1).getData().add(study);


                    break;
                }

            }
        }
        return WebResult.<List<StudyByType>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(list)
                .build();
    }

    //根据id进行删除
    @DeleteMapping("/delete")
    @ApiOperation(notes = "csx",value = "删除课程接口")
    public WebResult<Integer> delete(@RequestParam @ApiParam(name="study_id") Integer study_id){


        Study study=studyService.findById(study_id);
        String tempKey=study.getUrl().split("http://xiaoningya-1302847510.cos.ap-chongqing.myqcloud.com/")[1];
        String key= URLtoUTF8.unescape(tempKey.split("\\?sign=")[0]);
        FileCommit fileCommit=new FileCommit();
        fileCommit.delete(key);
            return WebResult.<Integer>builder()
                    .code(200)
                    .message(REQUEST_STATUS_SUCCESS)
                    .data(studyService.delete(study_id))
                    .build();

    }


//
//
//    @DeleteMapping("/deletekey")
//    @ApiOperation(notes = "csx",value = "删除课程接口")
//    public void deletekey(@RequestParam @ApiParam(name="study_id") Integer study_id){
//        Study study=studyService.findById(study_id);
//        String tempKey=study.getUrl().split("http://xiaoningya-1302847510.cos.ap-chongqing.myqcloud.com/")[1];
//        String key= URLtoUTF8.unescape(tempKey.split("\\?sign=")[0]);
//        FileCommit fileCommit=new FileCommit();
//        fileCommit.delete(key);
//    }


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
                          @RequestParam @ApiParam(name="remark") String remark,
                          @RequestParam(value = "file") MultipartFile mpFile,
                          @RequestParam @ApiParam(name="time") String time,
                          @RequestParam @ApiParam(name="typeid") Integer typeid) {
        JSONObject jsonObject = new JSONObject();

        if (mpFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "资料上传失败");
            return jsonObject;
        }
        try {
            FileCommit fileCommit = new FileCommit();
            fileCommit.fileCommit(mpFile);
            String url = fileCommit.downLoad(mpFile);
//            System.out.println("======================"+url);
            Study study = new Study();
                study.setName(name);
                study.setDepartmentid(1);
                study.setUrl(url);
                study.setSubjectid(subject_id);
                study.setDatatypeid(datatype_id);
                study.setTime(time);
                study.setTypeid(typeid);
                study.setBeizhu(remark);
                this.studyService.insert(study);
                return WebResult.<Integer>builder()
                        .code(200)
                        .message(REQUEST_STATUS_SUCCESS)
                        .data(1)
                        .build();
            } catch(Exception e){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "新增失败" + e.getMessage());
            return jsonObject;
        }
    }
}
