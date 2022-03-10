package com.exam.demo.controller;

import com.exam.demo.entity.ExamJudge;
import com.exam.demo.params.JudgeParam;
import com.exam.demo.params.submit.JudgeSubmitParam;
import com.exam.demo.results.vo.ExamJudgeVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamJudgeService;
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
@RequestMapping("examJudge")
@Api(value="/examJudge",tags={"判断题目操作接口"})
public class ExamJudgeController {

    @Autowired
    private ExamJudgeService examJudgeService;

    @GetMapping("findAll")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "查询所有判断题目接口")
    public WebResult<List<ExamJudge>> findAll() {
        return WebResult.<List<ExamJudge>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.findAll())
                .build();
    }

    @GetMapping("findPage")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "分页查询所有判断题目接口")
    public WebResult<List<ExamJudge>> findPage(@RequestParam @ApiParam(name="currentPage") Integer currentPage,
                                               @RequestParam @ApiParam(name="pageSize") Integer pageSize) {
        return WebResult.<List<ExamJudge>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.findPage(currentPage, pageSize))
                .build();
    }

    @GetMapping("findById")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "根据题目ID查询判断题目接口")
    public WebResult<ExamJudge> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamJudge>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.findById(id))
                .build();
    }
//=========================================================组合查询=======================================================
    @PostMapping("search")
    @ApiOperation(notes = "LBX",value = "组合查询——分页——根据条件查询判断题目, 注意这个接口查询的不是材料题下的判断题")
    public WebResult<PageVo<ExamJudgeVo>> search(@ApiParam(name = "judgeParam", value = "接受前端请求参数的实体类，前端注意该接口没有把部门作为查询条件")
                                               @RequestBody JudgeParam judgeParam) {
        return WebResult.<PageVo<ExamJudgeVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.search(judgeParam.getCurrentPage(), judgeParam.getPageSize(),
                        judgeParam.getId(), judgeParam.getContext(), judgeParam.getSubject(), 0))
                .build();
    }
//==========================================================新增=========================================================

    @PostMapping("saveExamJudge")
    @Transactional
    @ApiOperation(notes = "LBX", value = "新增填空题")
    public WebResult<Map<String,Object>> saveExamJudge(
            @ApiParam(value = "题目") @RequestParam(required = false) String context,
            @ApiParam(value = "科目Id") @RequestParam(required = false) Integer subjectId,
            @ApiParam(value = "答案 对：0 错：1") @RequestParam(required = false) Integer answer,
            @ApiParam(value = "分数") @RequestParam(required = false) Double score,
            @ApiParam(value = "上传图片") @RequestParam(required = false) MultipartFile image
            ) {
        if (context == null && subjectId == null && answer == null && score == null && image == null) {
            return WebResult.<Map<String,Object>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<Map<String,Object>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.saveExamJudge(context, subjectId, answer, score, image, false))
                .build();
    }

    @PostMapping("saveExamJudgeInMaterial")
    @Transactional
    @ApiOperation(notes = "LBX", value = "材料题——新增填空题")
    public WebResult<Map<String,Object>> saveExamJudgeInMaterial(
            @ApiParam(value = "题目") @RequestParam(required = false) String context,
            @ApiParam(value = "答案 对：0 错：1") @RequestParam(required = false) Integer answer,
            @ApiParam(value = "分数") @RequestParam(required = false) Double score,
            @ApiParam(value = "上传图片") @RequestParam(required = false) MultipartFile image
    ) {
        if (context == null && answer == null && score == null && image == null) {
            return WebResult.<Map<String,Object>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<Map<String,Object>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.saveExamJudge(context, null, answer, score, image, true))
                .build();
    }
//======================================================================================================================
    @PostMapping("update")
    @ApiOperation(notes = "xiong",value = "修改题库的判断题目接口")
    public WebResult<Integer> updateExamJudge(@RequestBody @ApiParam(name="examJudge",required=true) ExamJudge examJudge) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.updateExamJudge(examJudge))
                .build();
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(notes = "xiong",value = "删除题库中的判断题目接口")
    public WebResult<Integer> deleteExamJudge(@PathVariable @ApiParam(name="id",required=true,value = "id传入null") Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.deleteExamJudge(id))
                .build();
    }
}