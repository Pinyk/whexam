package com.exam.demo.controller;

import com.exam.demo.entity.ExamJudge;
import com.exam.demo.params.JudgeParam;
import com.exam.demo.results.vo.ExamJudgeVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamJudgeService;
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
@RequestMapping("examJudge")
@Api(value="/examJudge",tags={"判断题目操作接口"})
public class ExamJudgeController {

    @Autowired
    private ExamJudgeService examJudgeService;

    @GetMapping("findAll")
    @ApiOperation(notes = "xiong",value = "查询所有判断题目接口")
    public WebResult<List<ExamJudge>> findAll() {
        return WebResult.<List<ExamJudge>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.findAll())
                .build();
    }

    @GetMapping("findPage")
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
    @ApiOperation(notes = "xiong",value = "根据题目ID查询判断题目接口")
    public WebResult<ExamJudge> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamJudge>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.findById(id))
                .build();
    }

    @PostMapping("search")
    @ApiOperation(notes = "xiong",value = "组合查询——分页——根据条件查询判断题目, 注意这个接口查询的不是材料题下的判断题")
    public WebResult<PageVo<ExamJudgeVo>> search(@ApiParam(name = "judgeParam", value = "接受前端请求参数的实体类，前端注意该接口没有把部门作为查询条件")
                                               @RequestBody JudgeParam judgeParam) {
        return WebResult.<PageVo<ExamJudgeVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examJudgeService.search(judgeParam.getCurrentPage(), judgeParam.getPageSize(),
                        judgeParam.getId(), judgeParam.getContext(), 0))
                .build();
    }

//    @PostMapping("save")
//    @ApiOperation(notes = "xiong",value = "向题库添加判断题目接口")
//    public WebResult<Integer> saveExamJudge(@RequestBody @ApiParam(name="examJudge",required=true,value = "id传入null") ExamJudge examJudge) {
//        return WebResult.<Integer>builder()
//                .code(200)
//                .message(REQUEST_STATUS_SUCCESS)
//                .data(examJudgeService.saveExamJudge(examJudge))
//                .build();
//    }

    @PostMapping("save")
    @ApiOperation(notes = "xiong",value = "向题库添加判断题目接口")
    public WebResult<Integer> saveExamJudge(@RequestParam @ApiParam(name="context",required=true) String context,
                                            @RequestParam @ApiParam(name="answer",required=true) Integer answer,
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
            ExamJudge examJudge = new ExamJudge();
            examJudge.setContext(context);
            examJudge.setAnswer(answer);
            examJudge.setDifficulty(1);
            examJudge.setSubjectId(subjectId);
            examJudge.setScore(score);
            examJudge.setImgUrl(storeUrlPath);
            return WebResult.<Integer>builder()
                    .code(200)
                    .message(REQUEST_STATUS_SUCCESS)
                    .data(examJudgeService.saveExamJudge(examJudge))//添加到数据库
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