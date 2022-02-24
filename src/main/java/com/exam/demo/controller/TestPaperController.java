package com.exam.demo.controller;

import com.exam.demo.entity.Testpaper;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("testPaper")
@Api(value="/testPaper",tags={"试卷卷头操作接口"})
public class TestPaperController {

    @Autowired
    private TestPaperService testPaperService;

    @GetMapping("findAllTestPaper")
    @ApiOperation(notes = "xiong",value = "查询所有试卷信息接口")
    public WebResult<List<RtTestpaper>> findAllTestPaper() {
        return WebResult.<List<RtTestpaper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(testPaperService.findAll())
                .build();
    }

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
                                           @RequestParam @ApiParam(name="shuffle",required=true) Integer shuffle) {
        Testpaper testpaper = new Testpaper();
        testpaper.setSubjectId(subjectId);
        testpaper.setName(name);
        testpaper.setTotalscore(totalscore);
        testpaper.setPassscore(passscore);
        testpaper.setTime(time);
        testpaper.setUserId(userId);
        testpaper.setDepartmentId(departmentId);
        testpaper.setShuffle(shuffle);

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