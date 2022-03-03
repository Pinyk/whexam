package com.exam.demo.controller;

import com.exam.demo.entity.ExamSubject;
import com.exam.demo.params.SelectParam;
import com.exam.demo.params.SubjectParam;
import com.exam.demo.results.vo.ExamSubjectVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamSubjectService;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examSubject")
@Api(value="/examSubject",tags={"主观题目操作接口"})
public class ExamSubjectController {

    @Autowired
    private ExamSubjectService examSubjectService;

    @GetMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有主观题目接口")
    public WebResult<List<ExamSubject>> findAll() {
        return WebResult.<List<ExamSubject>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.findAll())
                .build();
    }

    @GetMapping("findPage")
    @ApiOperation(notes = "xiong",value = "分页查询所有主观题目接口")
    public WebResult<List<ExamSubject>> findPage(@RequestParam @ApiParam(name="currentPage") Integer currentPage,
                                                 @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<ExamSubject>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.findPage(currentPage, pageSize))
                .build();
    }

    @GetMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询主观题目接口")
    public WebResult<ExamSubject> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamSubject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.findById(id))
                .build();
    }

    @PostMapping("search")
    @ApiOperation(notes = "xiong",value = "根据条件查询主观题目接口")
    public WebResult<PageVo<ExamSubjectVo>> search(@ApiParam(value = "组合查询——分页——根据条件查询主观题目接口")
                                               @RequestBody SubjectParam subjectParam) {
        return WebResult.<PageVo<ExamSubjectVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.search(subjectParam.getCurrentPage(), subjectParam.getPageSize(),
                        subjectParam.getId(), subjectParam.getContext(), 0))
                .build();
    }

//    @PostMapping("save")
//    @ApiOperation(notes = "xiong",value = "向题库添加主观题目接口")
//    public WebResult<Integer> saveExamSubject(@RequestBody @ApiParam(name="examSubject",required=true,value = "id传入null") ExamSubject examSubject) {
//        return WebResult.<Integer>builder()
//                .code(200)
//                .message(REQUEST_STATUS_SUCCESS)
//                .data(examSubjectService.saveExamSubject(examSubject))
//                .build();
//    }
@PostMapping("save")
@ApiOperation(notes = "xiong",value = "向题库添加主观题目接口")
public WebResult<Integer> saveExamSubject(@RequestParam @ApiParam(name="context",required=true) String context,
                                          @RequestParam @ApiParam(name="answer",required=true) String answer,
                                          @RequestParam @ApiParam(name="subjectId",required=true) Integer subjectId,
                                          @RequestParam @ApiParam(name="score",required=true) Double score,
                                          @RequestParam("file") MultipartFile multipartFile) {
    if(multipartFile.isEmpty()) {
        return WebResult.<Integer>builder()
                .code(404)
                .message(REQUEST_STATUS_ERROR)
                .data(-1)
                .build();
    }
    //文件名=当前时间到毫秒+原来的文件名
    String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
    //文件路径
    String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img";
    //如果文件路径不存在，新增该路径
    File file1 = new File(filePath);
    if(!file1.exists()){
        file1.mkdir();
    }
    //实际的文件地址
    File dest = new File(filePath + System.getProperty("file.separator") + fileName);
    //存储到数据库里的相对文件地址
    String storeUrlPath = "/img/" + fileName;
    try {
        multipartFile.transferTo(dest);
        // 添加到数据库
        ExamSubject examSubject = new ExamSubject();
        examSubject.setContext(context);
        examSubject.setAnswer(answer);
        examSubject.setDifficulty(1);
        examSubject.setSubjectId(subjectId);
        examSubject.setScore(score);
        examSubject.setImgUrl(storeUrlPath);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.saveExamSubject(examSubject))//添加到数据库
                .build();
    } catch (Exception e) {
        return WebResult.<Integer>builder()
                .code(404)
                .message(REQUEST_STATUS_ERROR)
                .data(-1)
                .build();
    }
}

    @PostMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的主观题目接口")
    public WebResult<Integer> updateExamSubject(@RequestBody @ApiParam(name="examSubject",required=true,value = "id传入null") ExamSubject examSubject) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.updateExamSubject(examSubject))
                .build();
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(notes = "xiong",value = "删除题库中的主观题目接口")
    public WebResult<Integer> deleteExamSubject(@PathVariable @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.deleteExamSubject(id))
                .build();
    }
}
