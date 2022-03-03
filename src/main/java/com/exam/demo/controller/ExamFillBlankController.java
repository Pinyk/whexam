package com.exam.demo.controller;

import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.ExamFillBlankVo;
import com.exam.demo.service.ExamFillBlankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.util.List;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;


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
    @ApiIgnore
    @ApiOperation(notes = "wxn",value = "根据题目ID查询填空题接口")
    public WebResult<ExamFillBlankVo> findById(@RequestParam @ApiParam(name="id",required=true) Integer id) {
        return WebResult.<ExamFillBlankVo>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.findById(id))
                .build();
    }

    @GetMapping("search")
    @ApiOperation(notes = "wxn",value = "根据条件查询填空题接口",httpMethod = "GET")
    public WebResult<List<ExamFillBlank>> search(@RequestParam @ApiParam(name="current") Integer current,
                                                 @RequestParam @ApiParam(name="pageSize") Integer pageSize,
                                                 @RequestBody @ApiParam(name="queryQuestion") SelectQuestionVo queryQuestion) {
        return WebResult.<List<ExamFillBlank>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examFillBlankService.search(current, pageSize, queryQuestion, 0))
                .build();
    }

    @PostMapping("save")
    @ApiOperation(notes = "wxn",value = "向题库添加填空题接口",httpMethod = "POST")
    public WebResult<Integer> saveExamFillBlank(@RequestParam @ApiParam(name="context",required=true) String context,
                                                @RequestParam @ApiParam(name="answer",required=true) String answer,
                                                @RequestParam @ApiParam(name="subjectId",required=true) Integer subjectId,
                                                @RequestParam @ApiParam(name="score",required=true) Double score,
                                                @RequestParam("file") MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            return WebResult.<Integer>builder().code(201).message("图片为空，请重新上传").data(-1).build();
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
            ExamFillBlank examFillBlank = new ExamFillBlank();
            examFillBlank.setContext(context);
            examFillBlank.setAnswer(answer);
            examFillBlank.setDifficulty(1);
            examFillBlank.setSubjectId(subjectId);
            examFillBlank.setScore(score);
            examFillBlank.setImgUrl(storeUrlPath);
            return WebResult.<Integer>builder()
                    .code(200)
                    .message(REQUEST_STATUS_SUCCESS)
                    .data(examFillBlankService.saveExamFillBlank(examFillBlank))//添加到数据库
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
