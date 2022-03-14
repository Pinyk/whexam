package com.exam.demo.controller;

import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.params.FillBlankParam;
import com.exam.demo.params.submit.FillBlankSubmitParam;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.ExamFillBlankVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamFillBlankService;
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
@RequestMapping("examFb")
@Api(value="/examFb",tags={"填空题操作接口"})
public class ExamFillBlankController {

    @Autowired
    ExamFillBlankService examFillBlankService;

    @GetMapping("findAll")
    @ApiIgnore
    @ApiOperation(notes = "wxn",value = "查询所有填空题接口")
    public WebResult<List<ExamFillBlank>> findAll(){
        return WebResult.<List<ExamFillBlank>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.findAll())
                .build();
    }

    @GetMapping("findPage")
    @ApiIgnore
    @ApiOperation(notes = "wxn",value = "分页查询所有填空题接口")
    public WebResult<List<ExamFillBlank>> findPage(@RequestParam @ApiParam(name="currentPage") Integer currentPage,
                                                   @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<ExamFillBlank>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.findPage(currentPage, pageSize))
                .build();
    }

    @GetMapping("findById")
    @ApiIgnore
    @ApiOperation(notes = "wxn",value = "根据题目ID查询填空题接口")
    public WebResult<ExamFillBlankVo> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamFillBlankVo>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.findById(id))
                .build();
    }
//====================================================组合查询============================================================
    @PostMapping("search")
    @ApiOperation(notes = "LBX",value = "组合查询",httpMethod = "POST")
    public WebResult<PageVo<ExamFillBlankVo>> search(@ApiParam
                                                 @RequestBody FillBlankParam fillBlankParam) {
        return WebResult.<PageVo<ExamFillBlankVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.search(fillBlankParam.getCurrentPage(), fillBlankParam.getPageSize(),
                        fillBlankParam.getId(), fillBlankParam.getContext(), fillBlankParam.getSubject(), 0))
                .build();
    }
//=========================================================新增============================================================
    @PostMapping("saveExamFillBlank")
    @Transactional
    @ApiOperation(notes= "LBX", value = "新增填空题")
    public WebResult<Map<String,Object>> saveExamFillBlank(
            @ApiParam (value = "填空题题目内容") @RequestParam(required = false) String context,
            @ApiParam (value = "填空题所属学科ID") @RequestParam(required = false) Integer subjectId,
            @ApiParam (value = "填空题答案") @RequestParam(required = false) String answer,
            @ApiParam (value = "填空题分数") @RequestParam(required = false) Double score,
            @ApiParam (value = "上传图片") @RequestParam(required = false) MultipartFile file
            ) {
        if (context == null && subjectId == null && answer == null && score == null && file == null) {
            return WebResult.<Map<String,Object>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<Map<String,Object>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.saveExamFillBlank(context, subjectId, answer, score, file, false))
                .build();
    }

    @PostMapping("saveExamFillBlankInMaterial")
    @Transactional
    @ApiOperation(notes= "LBX", value = "材料题——新增填空题")
    public WebResult<Map<String,Object>> saveExamFillBlankInMaterial(
            @ApiParam (value = "填空题题目内容") @RequestParam(required = false) String context,
            @ApiParam (value = "填空题所属学科ID") @RequestParam(required = false) Integer subjectId,
            @ApiParam (value = "填空题答案") @RequestParam(required = false) String answer,
            @ApiParam (value = "填空题分数") @RequestParam(required = false) Double score,
            @ApiParam (value = "上传图片") @RequestParam(required = false) MultipartFile file
    ) {
        if (context == null && subjectId == null && answer == null && score == null && file == null) {
            return WebResult.<Map<String,Object>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<Map<String,Object>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.saveExamFillBlank(context, subjectId, answer, score, file, true))
                .build();
    }
//======================================================================================================================
    @PostMapping("update")
    @ApiOperation(notes = "wxn",value = "修改题库的填空题接口",httpMethod = "POST")
    public WebResult<Integer> updateExamFillBlank(@RequestBody @ApiParam(name="examFillBlank",required=true,value = "id传入null") ExamFillBlank examFillBlank) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.updateExamFillBlank(examFillBlank))
                .build();
    }

    @DeleteMapping("delete")
    @Transactional
    @ApiOperation(notes = "LBX",value = "删除题库中的填空题接口")
    public WebResult<Integer> deleteExamFillBlank(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.deleteExamFillBlank(id))
                .build();
    }
}
