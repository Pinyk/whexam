package com.exam.demo.controller;

import com.exam.demo.entity.Testpaper;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.results.vo.TestpaperVo2;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("testPaper")
@Api(value="/testPaper",tags={"试卷卷头操作接口"})
public class TestPaperController {

    @Autowired
    private TestPaperService testPaperService;

    @GetMapping("findAllTestPaper")
    @ApiIgnore
    @ApiOperation(notes = "xiong",value = "查询所有试卷信息接口")
    public WebResult<List<RtTestpaper>> findAllTestPaper() {
        return WebResult.<List<RtTestpaper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAll())
                .build();
    }
//=========================================根据试卷id查询试卷详情===========================================================
    @GetMapping("findTestPaperById")
    @ApiOperation(notes = "", value = "根据试卷id查询试卷详情")
    public WebResult<List<Map<String, Object>>> findTestPaperById(@RequestParam int testPaperId) {
        return WebResult.<List<Map<String, Object>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findTestPaperById(testPaperId))
                .build();
    }
//=============================================查询卷头==================================================================
    @PostMapping("findCurrentTestPaperHead")
    @ApiOperation(notes = "LBX", value = "查询进行中考试试卷的卷头")
    public WebResult<List<Map<String, Object>>> findCurrentTestPaperHead(Integer userId) {
        return WebResult.<List<Map<String, Object>>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findCurrentTestPaperHead(userId))
                .build();
    }

    @PostMapping("findHistorialTestPaperHead")
    @ApiOperation(notes = "LBX", value = "查询历史考试试卷的卷头")
    public WebResult<List<Map<String, Object>>> findHistorialTestPaperHead(Integer userId) {
        return WebResult.<List<Map<String, Object>>>builder()
                .code(200)

                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findHistorialTestPaperHead(userId))
                .build();
    }

//======================================================================================================================
    @PostMapping("addTestPaper")
    @ApiOperation(notes = "xiong",value = "添加试卷头信息接口")
    public WebResult<Integer> addTestPaper(@RequestParam @ApiParam(name="subjectId",required=true) Integer subjectId,
                                           @RequestParam @ApiParam(name="name",required=true) String name,
                                           @RequestParam @ApiParam(name="totalscore",required=true) Double totalscore,
                                           @RequestParam @ApiParam(name="passscore",required=true) Double passscore,
                                           @RequestParam @ApiParam(name="startTime",required=true) String startTime,
                                           @RequestParam @ApiParam(name="deadTime",required=true) String deadTime,
                                           @RequestParam @ApiParam(name="time",required=true) Integer time,
                                           @RequestParam @ApiParam(name="userId",required=true) Integer userId,
                                           @RequestParam @ApiParam(name="departmentId",required=true) Integer departmentId,
                                           @RequestParam @ApiParam(name="repeat",required=true) String repeat) {
        Testpaper testpaper = new Testpaper();
        testpaper.setSubjectId(subjectId);
        testpaper.setName(name);
        testpaper.setTotalscore(totalscore);
        testpaper.setPassscore(passscore);
        testpaper.setTime(time);
        testpaper.setUserId(userId);
        testpaper.setDepartmentId(departmentId);
        testpaper.setRepeat(repeat);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            testpaper.setStartTime(dateFormat.parse(startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            testpaper.setDeadTime(dateFormat.parse(deadTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.addTestPaper(testpaper))
                .build();
    }
}