package com.exam.demo.controller;

import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.otherEntity.SelectQuestion;
import com.exam.demo.service.ExamSelectService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examSelect")
@Api(value="/examSelect",tags={"选择题目操作接口"})
public class ExamSelectController {

    @Autowired
    private ExamSelectService examSelectService;

    @GetMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有选择题目接口")
    public WebResult<List<SelectQuestion>> findAll() {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findAll())
                .build();
    }

    @GetMapping("findSingleSelection")
    @ApiOperation(notes = "xiong",value = "查询所有单选题目接口")
    public WebResult<List<SelectQuestion>> findSingleSelection() {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findSingleOrMultipleSelection(1))
                .build();
    }

    @GetMapping("findMultipleSelection")
    @ApiOperation(notes = "xiong",value = "查询所有多选题目接口")
    public WebResult<List<SelectQuestion>> findMultipleSelection() {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findSingleOrMultipleSelection(2))
                .build();
    }

    @GetMapping("findSingleSelectionByPage")
    @ApiOperation(notes = "xiong",value = "分页查询所有单选题目接口")
    public WebResult<List<SelectQuestion>> findSingleSelectionByPage(
            @RequestParam @ApiParam(name="currentPage") Integer currentPage,
            @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findPage(currentPage, pageSize, 1))
                .build();
    }

    @GetMapping("findMultipleSelectionByPage")
    @ApiOperation(notes = "xiong",value = "分页查询所有多选题目接口")
    public WebResult<List<SelectQuestion>> findMultipleSelectionByPage(
            @RequestParam @ApiParam(name="currentPage") Integer currentPage,
            @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<SelectQuestion>>builder()
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findPage(currentPage, pageSize, 2))
                .build();
    }

    @GetMapping("findById")
    @ApiOperation(notes = "xiong",value = "根据题目ID查询选择题目接口")
    public WebResult<SelectQuestion> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<SelectQuestion>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findById(id))
                .build();
    }

    @PostMapping("searchSingleSelection")
    @ApiOperation(notes = "xiong",value = "根据条件查询单选题目接口")
    public WebResult<List<SelectQuestion>> searchSingleSelection(
            @RequestParam @ApiParam(name="current") Integer current,
            @RequestParam @ApiParam(name="pageSize") Integer pageSize,
            @RequestBody @ApiParam(name="queryQuestion") QueryQuestion queryQuestion) {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.search(current, pageSize, queryQuestion, 1))
                .build();
    }

    @PostMapping("searchMultipleSelection")
    @ApiOperation(notes = "xiong",value = "根据条件查询多选题目接口")
    public WebResult<List<SelectQuestion>> searchMultipleSelection(
            @RequestParam @ApiParam(name="current") Integer current,
            @RequestParam @ApiParam(name="pageSize") Integer pageSize,
            @RequestBody @ApiParam(name="queryQuestion") QueryQuestion queryQuestion) {
        return WebResult.<List<SelectQuestion>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.search(current, pageSize, queryQuestion, 2))
                .build();
    }

//    @PostMapping("save")
//    @ApiOperation(notes = "xiong",value = "向题库添加选择题目接口")
//    public WebResult<Integer> saveExamSelect(@RequestBody @ApiParam(name="examSelect",required=true,value = "id传入null") ExamSelect examSelect) {
//        return WebResult.<Integer>builder()
//                .code(200)
//                .message(REQUEST_STATUS_SUCCESS)
//                .data(examSelectService.saveExamSelect(examSelect))
//                .build();
//    }
@PostMapping("save")
@ApiOperation(notes = "xiong",value = "向题库添加选择题目接口")
public WebResult<Integer> saveExamSelect(@RequestParam @ApiParam(name="context",required=true) String context,
                                         @RequestParam @ApiParam(name="selectionA",required=true) String selectionA,
                                         @RequestParam @ApiParam(name="selectionB",required=true) String selectionB,
                                         @RequestParam @ApiParam(name="selectionC",required=true) String selectionC,
                                         @RequestParam @ApiParam(name="selectionD",required=true) String selectionD,
                                         @RequestParam @ApiParam(name="answer",required=true) String answer,
                                         @RequestParam @ApiParam(name="subjectId",required=true) Integer subjectId,
                                         @RequestParam @ApiParam(name="score",required=true) Double score,
                                         @RequestParam @ApiParam(name="type",required=true) Integer type,
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
    //    难度固定
    Integer difficulty = 1;
    //匹配答案
    String selection = selectionA + "；" +selectionB + "；" +selectionC + "；" +selectionD;
    String dealAnswer = "";
    char[] chars = answer.toCharArray();
    for (int i = 0; i < chars.length - 1; i++) {
        switch (chars[i]){
            case 'A': dealAnswer += selectionA + "；";break;
            case 'B': dealAnswer = selectionB + "；";break;
            case 'C': dealAnswer = selectionC + "；";break;
            case 'D': dealAnswer = selectionD + "；";break;
        }
    }
    switch (chars[chars.length - 1]){
        case 'A': dealAnswer += selectionA;break;
        case 'B': dealAnswer = selectionB;break;
        case 'C': dealAnswer = selectionC;break;
        case 'D': dealAnswer = selectionD;break;
    }
    answer = dealAnswer;
    try {
        multipartFile.transferTo(dest);
        // 添加到数据库
        ExamSelect examSelect = new ExamSelect();
        examSelect.setContext(context);
        examSelect.setSelection(selection);
        examSelect.setAnswer(answer);
        examSelect.setDifficulty(difficulty);
        examSelect.setSubjectId(subjectId);
        examSelect.setScore(score);
        examSelect.setType(type);
        examSelect.setImgUrl(storeUrlPath);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.saveExamSelect(examSelect))//添加到数据库
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
    @ApiOperation(notes = "xiong",value = "修改题库的选择题目接口")
    public WebResult<Integer> updateExamSelect(@RequestBody @ApiParam(name="examSelect",required=true,value = "id传入null") ExamSelect examSelect) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.updateExamSelect(examSelect))
                .build();
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(notes = "xiong",value = "删除题库中的选择题目接口")
    public WebResult<Integer> deleteExamSelect(@PathVariable @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.deleteExamSelect(id))
                .build();
    }
}