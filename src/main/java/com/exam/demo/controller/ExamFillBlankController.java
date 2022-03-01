package com.exam.demo.controller;

import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.service.ExamFillBlankService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examFb")
@Api(value="/examFb",tags={"填空题操作接口"})
public class ExamFillBlankController {

    @Autowired
    ExamFillBlankService examFillBlankService;

    @GetMapping("findAll")
    @ApiOperation(notes = "wxn",value = "查询所有填空题接口")
    public WebResult<List<ExamFillBlank>> findAll(){
        return WebResult.<List<ExamFillBlank>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.findAll())
                .build();
    }

    @GetMapping("findPage")
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
    @ApiOperation(notes = "wxn",value = "根据题目ID查询填空题接口")
    public WebResult<ExamFillBlank> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamFillBlank>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.findById(id))
                .build();
    }

    @GetMapping("search")
    @ApiOperation(notes = "wxn",value = "根据条件查询填空题接口",httpMethod = "GET")
    public WebResult<List<ExamFillBlank>> search(@RequestParam @ApiParam(name="current") Integer current,
                                                 @RequestParam @ApiParam(name="pageSize") Integer pageSize,
                                                 @RequestBody @ApiParam(name="queryQuestion") QueryQuestion queryQuestion) {
        return WebResult.<List<ExamFillBlank>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.search(current, pageSize, queryQuestion))
                .build();
    }

    @PostMapping("save")
    @ApiOperation(notes = "wxn",value = "向题库添加填空题接口",httpMethod = "POST")
    public WebResult<Integer> saveExamFillBlank(@RequestBody @ApiParam(name="examFillBlank",required=true,value = "id传入null") ExamFillBlank examFillBlank) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.saveExamFillBlank(examFillBlank))
                .build();
    }

    @PostMapping("update")
    @ApiOperation(notes = "wxn",value = "修改题库的填空题接口",httpMethod = "POST")
    public WebResult<Integer> updateExamFillBlank(@RequestBody @ApiParam(name="examFillBlank",required=true,value = "id传入null") ExamFillBlank examFillBlank) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.updateExamFillBlank(examFillBlank))
                .build();
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(notes = "wxn",value = "删除题库中的填空题接口")
    public WebResult<Integer> deleteExamFillBlank(@PathVariable @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.deleteExamFillBlank(id))
                .build();
    }
}
