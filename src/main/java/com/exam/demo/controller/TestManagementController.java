package com.exam.demo.controller;

import com.exam.demo.service.TestPaperService;
import com.exam.demo.params.postparams.TestManageParam;
import com.exam.demo.utils.TestpaperVo;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("testmangement")
@Api(value = "/testmangement", tags = {"考试管理模块接口"})
public class TestManagementController {

    @Autowired
    private TestPaperService testPaperService;
//==============================================正在考试=================================================================

    @GetMapping("findAllCurrentExam")
    @ApiOperation(notes = "LBX", value = "不支持分页——查询所有正在进行的考试")
    public WebResult<List<TestpaperVo>> findAllCurrentExam() {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAllCurrentExam())
                .build();
    }

    @PostMapping("findCurrentExam")
    @ApiOperation(notes = "LBX", value = "正在考试——组合查询", httpMethod = "Post")
    public WebResult<List<TestpaperVo>> findCurrentExam(@RequestBody
                                                        @ApiParam(name = "params", value = "前端注意：\n(1)前端需要发送的media-type为application/json" +
                                                                "\n(2)对应的部门传对应的id,后台预留的是根据id查询，也可以联系后台改成按照名称查询\n" +
                                                                "(3)这个接口查询需求还需确定是否要模糊查询\n")
                                                                    TestManageParam params) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findCurrentExam(params.getTestPaperId(), params.getTestPaperName(),
                        params.getDepartmentId(), params.getSubject(), params.getCurrentPage(), params.getPageSize()))
                .build();
    }


    @GetMapping("findCurrentExamByPage")
    @ApiOperation(notes = "LBX", value = "支持分页——查询所有正在进行的考试")
    public WebResult<List<TestpaperVo>> findCurrentExamByPage(@RequestParam @ApiParam(name="currentPage", value = "当前页码", required = true) Integer currentPage,
                                                              @RequestParam @ApiParam(name="pageSize", value = "每页容量", required = true) Integer pageSize) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findCurrentExamByPage(currentPage, pageSize))
                .build();
    }
//=====================================================历史考试==========================================================

    @GetMapping("findAllHistoricalExam")
    @ApiOperation(notes = "LBX", value = "不支持分页——查询所有历史考试")
    public WebResult<List<TestpaperVo>> findAllHistoricalExam() {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAllHistoricalExam())
                .build();
    }

    @PostMapping("findHistoricalExam")
    @ApiOperation(notes = "LBX", value = "历史考试——组合查询")
    public WebResult<List<TestpaperVo>> findHistoricalExam(@RequestBody
                                                           @ApiParam(name = "params", value = "前端注意：\n(1)前端需要发送的media-type为application/json" +
                                                                     "\n(2)对应的部门传对应的id,后台预留的是根据id查询，也可以联系后台改成按照名称查询\n" +
                                                                     "(3)这个接口查询需求还需确定是否要模糊查询\n")
                                                                     TestManageParam params) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findHistoricalExam(params.getTestPaperId(), params.getTestPaperName(),
                        params.getDepartmentId(), params.getSubject(), params.getCurrentPage(), params.getPageSize()))
                .build();
    }

    @GetMapping("findHistoricalExamByPage")
    @ApiOperation(notes = "LBX", value = "支持分页——查询所有历史考试")
    public WebResult<List<TestpaperVo>> findHistoricalExamByPage(@RequestParam @ApiParam(name="currentPage", value = "当前页码", required = true) Integer currentPage,
                                                              @RequestParam @ApiParam(name="pageSize", value = "每页容量", required = true) Integer pageSize) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findHistoricalExamByPage(currentPage, pageSize))
                .build();
    }
//===================================================未来考试============================================================

    @GetMapping("findAllFutureExam")
    @ApiOperation(notes = "LBX", value = "不支持分页——查询所有未来的考试")
    public WebResult<List<TestpaperVo>> findAllFutureExam() {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAllFutureExam())
                .build();
    }

    @PostMapping("findFutureExam")
    @ApiOperation(notes = "LBX", value = "未来考试——组合查询")
    public WebResult<List<TestpaperVo>> findFutureExam(@RequestBody
                                                       @ApiParam(name = "params", value = "前端注意：\n(1)前端需要发送的media-type为application/json" +
                                                                 "\n(2)对应的部门传对应的id,后台预留的是根据id查询，也可以联系后台改成按照名称查询\n" +
                                                                 "(3)这个接口查询需求还需确定是否要模糊查询\n")
                                                                 TestManageParam params)  {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findFutureExam(params.getTestPaperId(), params.getTestPaperName(),
                        params.getDepartmentId(), params.getSubject(), params.getCurrentPage(), params.getPageSize()))
                .build();
    }

    @GetMapping("findFutureExamByPage")
    @ApiOperation(notes = "LBX", value = "支持分页——查询所有未来的考试")
    public WebResult<List<TestpaperVo>> findFutureExamByPage(@RequestParam @ApiParam(name="currentPage", value = "当前页码", required = true) Integer currentPage,
                                                                 @RequestParam @ApiParam(name="pageSize", value = "每页容量", required = true) Integer pageSize) {
        return WebResult.<List<TestpaperVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findFutureExamByPage(currentPage, pageSize))
                .build();
    }
}
