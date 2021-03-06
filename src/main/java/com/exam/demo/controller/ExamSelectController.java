package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.params.SelectParam;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.params.submit.SelectSubmitParam;
import com.exam.demo.results.vo.ExamSelectVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamSelectService;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.util.List;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examSelect")
@Api(value="/examSelect",tags={"选择题目操作接口"})
public class ExamSelectController {

    @Autowired
    private ExamSelectService examSelectService;

    @GetMapping("findAll")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "查询所有选择题目接口")
    public WebResult<List<SelectQuestionVo>> findAll() {
        return WebResult.<List<SelectQuestionVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findAll())
                .build();
    }

    @GetMapping("findSingleSelection")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "查询所有单选题目接口")
    public WebResult<List<SelectQuestionVo>> findSingleSelection() {
        return WebResult.<List<SelectQuestionVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findSingleOrMultipleSelection(1))
                .build();
    }

    @GetMapping("findMultipleSelection")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "查询所有多选题目接口")
    public WebResult<List<SelectQuestionVo>> findMultipleSelection() {
        return WebResult.<List<SelectQuestionVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findSingleOrMultipleSelection(2))
                .build();
    }

    @GetMapping("findSingleSelectionByPage")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "分页查询所有单选题目接口")
    public WebResult<List<SelectQuestionVo>> findSingleSelectionByPage(
            @RequestParam @ApiParam(name="currentPage") Integer currentPage,
            @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<SelectQuestionVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findPage(currentPage, pageSize, 1))
                .build();
    }

    @GetMapping("findMultipleSelectionByPage")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "分页查询所有多选题目接口")
    public WebResult<List<SelectQuestionVo>> findMultipleSelectionByPage(
            @RequestParam @ApiParam(name="currentPage") Integer currentPage,
            @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<SelectQuestionVo>>builder()
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.findPage(currentPage, pageSize, 2))
                .build();
    }


    @PostMapping("searchSingleSelection")
    @ApiOperation(notes = "xiong",value = "组合查询——分页——根据条件查询单选题目接口")
    public WebResult<PageVo<ExamSelectVo>> searchSingleSelection(@RequestBody
                                                                 @ApiParam(name = "selectParam", value = "接受前端的请求体,media-type:application/json," +
                                                                         "注意：所属部门 后台没有作为查询条件")
                                                                 SelectParam selectParam) {
        return WebResult.<PageVo<ExamSelectVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.search(selectParam.getId(), selectParam.getName(), selectParam.getSubject(),
                        selectParam.getCurrentPage(), selectParam.getPageSize(), 1, 0))
                .build();
    }

    @PostMapping("searchMultipleSelection")
    @ApiOperation(notes = "xiong",value = "组合查询——分页——根据条件查询多选题目接口")
    public WebResult<PageVo<ExamSelectVo>> searchMultipleSelection(@RequestBody
                                                                     @ApiParam(name = "selectParam", value = "接受前端的请求体,media-type:application/json" +
                                                                             "，注意：所属部门 后台没有作为查询条件")
                                                                     SelectParam selectParam)  {
        return WebResult.<PageVo<ExamSelectVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.search(selectParam.getId(), selectParam.getName(), selectParam.getSubject(),
                        selectParam.getCurrentPage(), selectParam.getPageSize(), 2, 0))
                .build();
    }
//========================================新增===========================================================================
    @PostMapping("saveSingleSelection")
    @Transactional
    @ApiOperation(value = "单选题新增保存接口", notes = "LBX")
    public WebResult<JSONObject> saveSingleSelection(
            @ApiParam("题目") @RequestParam(required = false) String context,
            @ApiParam("科目Id") @RequestParam(required = false) Integer subjectId,
            @ApiParam("A选项") @RequestParam(required = false) String selectionA,
            @ApiParam("B选项") @RequestParam(required = false) String selectionB,
            @ApiParam("C选项") @RequestParam(required = false) String selectionC,
            @ApiParam("D选项") @RequestParam(required = false) String selectionD,
            @ApiParam("答案") @RequestParam(required = false) String answer,
            @ApiParam("分数") @RequestParam(required = false) Double score,
            @ApiParam("上传图片") @RequestParam(required = false) MultipartFile file) {
        if (StringUtils.isBlank(context) && subjectId == null &&StringUtils.isBlank(selectionA) && StringUtils.isBlank(selectionB) && StringUtils.isBlank(selectionC)
                && StringUtils.isBlank(selectionD) && StringUtils.isBlank(answer) && score == null) {
            return WebResult.<JSONObject>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<JSONObject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.saveSingleSelection(context, subjectId, selectionA, selectionB, selectionC,
                        selectionD,answer, score, file, false))
                .build();
    }

    @PostMapping("saveSingleSelectionInMaterial")
    @Transactional
    @ApiOperation(value = "材料题——单选题新增保存接口", notes = "LBX")
    public WebResult<JSONObject> saveSingleSelectionInMaterial(
            @ApiParam("题目") @RequestParam(required = false) String context,
            @ApiParam("科目Id") @RequestParam(required = false) Integer subjectId,
            @ApiParam("A选项") @RequestParam(required = false) String selectionA,
            @ApiParam("B选项") @RequestParam(required = false) String selectionB,
            @ApiParam("C选项") @RequestParam(required = false) String selectionC,
            @ApiParam("D选项") @RequestParam(required = false) String selectionD,
            @ApiParam("答案") @RequestParam(required = false) String answer,
            @ApiParam("分数") @RequestParam(required = false) Double score,
            @ApiParam("上传图片") @RequestParam(required = false) MultipartFile file) {
        if (StringUtils.isBlank(context) && subjectId == null &&StringUtils.isBlank(selectionA) && StringUtils.isBlank(selectionB) && StringUtils.isBlank(selectionC)
                && StringUtils.isBlank(selectionD) && StringUtils.isBlank(answer) && score == null) {
            return WebResult.<JSONObject>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<JSONObject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.saveSingleSelection(context, subjectId, selectionA, selectionB, selectionC,
                        selectionD,answer, score, file, true))
                .build();
    }

    @PostMapping("saveMultipleSelection")
    @Transactional
    @ApiOperation(value = "多选题新增保存接口", notes = "LBX")
    public WebResult<JSONObject> saveMultipleSelection(
            @ApiParam("题目") @RequestParam(required = false) String context,
            @ApiParam("科目Id") @RequestParam(required = false) Integer subjectId,
            @ApiParam("A选项") @RequestParam(required = false) String selectionA,
            @ApiParam("B选项") @RequestParam(required = false) String selectionB,
            @ApiParam("C选项") @RequestParam(required = false) String selectionC,
            @ApiParam("D选项") @RequestParam(required = false) String selectionD,
            @ApiParam("答案") @RequestParam(required = false) String answer,
            @ApiParam("分数") @RequestParam(required = false) Double score,
            @ApiParam("上传图片") @RequestParam(required = false) MultipartFile file) {
        if (StringUtils.isBlank(context) && subjectId == null &&StringUtils.isBlank(selectionA) && StringUtils.isBlank(selectionB) && StringUtils.isBlank(selectionC)
                && StringUtils.isBlank(selectionD) && StringUtils.isBlank(answer) && score == null) {
            return WebResult.<JSONObject>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<JSONObject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.saveMultipleSelection(context, subjectId, selectionA, selectionB, selectionC,
                        selectionD,answer, score, file, false))
                .build();
    }

    @PostMapping("saveMultipleSelectionInMaterial")
    @Transactional
    @ApiOperation(value = "材料题——多选题新增保存接口", notes = "LBX")
    public WebResult<JSONObject> saveMultipleSelectionInMaterial(
            @ApiParam("题目") @RequestParam(required = false) String context,
            @ApiParam("科目Id") @RequestParam(required = false) Integer subjectId,
            @ApiParam("A选项") @RequestParam(required = false) String selectionA,
            @ApiParam("B选项") @RequestParam(required = false) String selectionB,
            @ApiParam("C选项") @RequestParam(required = false) String selectionC,
            @ApiParam("D选项") @RequestParam(required = false) String selectionD,
            @ApiParam("答案") @RequestParam(required = false) String answer,
            @ApiParam("分数") @RequestParam(required = false) Double score,
            @ApiParam("上传图片") @RequestParam(required = false) MultipartFile file) {
        if (StringUtils.isBlank(context) && subjectId == null &&StringUtils.isBlank(selectionA) && StringUtils.isBlank(selectionB) && StringUtils.isBlank(selectionC)
                && StringUtils.isBlank(selectionD) && StringUtils.isBlank(answer) && score == null) {
            return WebResult.<JSONObject>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<JSONObject>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.saveMultipleSelection(context, subjectId, selectionA, selectionB, selectionC,
                        selectionD,answer, score, file, true))
                .build();
    }
//======================================================================================================================
    @PostMapping("update")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "修改题库的选择题目接口")
    public WebResult<Integer> updateExamSelect(@RequestBody @ApiParam(name="examSelect",required=true,value = "id传入null") ExamSelect examSelect) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.updateExamSelect(examSelect))
                .build();
    }

    @DeleteMapping("delete")
    @Transactional
    @ApiOperation(notes = "LBX",value = "删除题库中的选择题目接口")
    public WebResult<Integer> deleteExamSelect(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examSelectService.deleteExamSelect(id))
                .build();
    }
}