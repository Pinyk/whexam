package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.params.SubjectParam;
import com.exam.demo.params.submit.SubjectSubmitParam;
import com.exam.demo.results.vo.ExamSubjectVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamSubjectService;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examSubject")
@Api(value="/examSubject",tags={"主观题目操作接口"})
public class ExamSubjectController {

    @Autowired
    private ExamSubjectService examSubjectService;

    @GetMapping("findAll")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "查询所有主观题目接口")
    public WebResult<List<ExamSubject>> findAll() {
        return WebResult.<List<ExamSubject>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.findAll())
                .build();
    }

    @GetMapping("findPage")
    @ApiIgnore
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
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "根据题目ID查询主观题目接口")
    public WebResult<ExamSubject> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamSubject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.findById(id))
                .build();
    }
//=================================================组合查询===============================================================
    @PostMapping("search")
    @ApiOperation(notes = "LBX",value = "组合查询——分页——根据条件查询主观题目接口")
    public WebResult<PageVo<ExamSubjectVo>> search(@ApiParam(value = "组合查询——分页——根据条件查询主观题目接口")
                                               @RequestBody SubjectParam subjectParam) {
        return WebResult.<PageVo<ExamSubjectVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.search(subjectParam.getCurrentPage(), subjectParam.getPageSize(),
                        subjectParam.getId(), subjectParam.getContext(), subjectParam.getSubject(), 0))
                .build();
    }
//==================================================新增=================================================================
    @PostMapping("saveSubject")
    @Transactional
    @ApiOperation(notes = "LBX", value = "新增问答题")
    public WebResult<JSONObject> saveSubject(
            @ApiParam("题目") @RequestParam(required = false) String context,
            @ApiParam("科目Id") @RequestParam(required = false) Integer subjectId,
            @ApiParam("答案") @RequestParam(required = false) String answer,
            @ApiParam("分数") @RequestParam(required = false) Double score,
            @ApiParam("上传图片") @RequestParam(required = false) MultipartFile image
            ) {
        if (StringUtils.isBlank(context) && subjectId == null && StringUtils.isBlank(answer) && score == null
                && image == null) {
            return WebResult.<JSONObject>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<JSONObject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.saveSubject(context, subjectId, answer, score, image, false))
                .build();
    }

    @PostMapping("saveSubjectInMaterial")
    @Transactional
    @ApiOperation(notes = "LBX", value = "材料题——新增问答题")
    public WebResult<JSONObject> saveSubjectInMaterial(
            @ApiParam("题目") @RequestParam(required = false) String context,
            @ApiParam("科目Id") @RequestParam(required = false) Integer subjectId,
            @ApiParam("答案") @RequestParam(required = false) String answer,
            @ApiParam("分数") @RequestParam(required = false) Double score,
            @ApiParam("上传图片") @RequestParam(required = false) MultipartFile image
    ) {
        if (StringUtils.isBlank(context) && subjectId == null && StringUtils.isBlank(answer) && score == null
                && image == null) {
            return WebResult.<JSONObject>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<JSONObject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.saveSubject(context, subjectId, answer, score, image, true))
                .build();
    }

    @PostMapping("update")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "修改题库的主观题目接口")
    public WebResult<Integer> updateExamSubject(@RequestBody @ApiParam(name="examSubject",required=true,value = "id传入null") ExamSubject examSubject) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.updateExamSubject(examSubject))
                .build();
    }

    @DeleteMapping("delete")
    @Transactional
    @ApiOperation(notes = "LBX",value = "删除题库中的主观题目接口")
    public WebResult<Integer> deleteExamSubject(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSubjectService.deleteExamSubject(id))
                .build();
    }
}
