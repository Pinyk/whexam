package com.exam.demo.controller;

import com.exam.demo.params.InformationParam;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.*;
import com.exam.demo.service.InformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: wxn
 * @Date: 2022/3/13
 */
@RestController
@RequestMapping("information")
@Api(value="/information",tags={"资料查询操作接口"})
public class InformationController {

    @Autowired
    private InformationService informationService;

    @PostMapping("search")
    @ApiOperation(notes = "wxn",value = "组合查询")
    public WebResult<PageVo<InformationVo>> search(@ApiParam(name="前端查询条件 InformationParam 查询条件实体类")
                                                   @RequestBody InformationParam informationParam){

        return WebResult.<PageVo<InformationVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(informationService.search(informationParam.getNums(),informationParam.getUsername(),informationParam.getDepartmentId(),
                        informationParam.getCurrentPage(),informationParam.getPageSize()))
                .build();
    }

    @GetMapping("searchIn")
    @ApiOperation(notes = "wxn",value = "组合查询")
    public WebResult<InformationInVo> searchIn(@ApiParam(name="前端查询条件 InformationInParam 查询条件实体类")
                                               @RequestBody Integer userId){
        return WebResult.<InformationInVo>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(informationService.searchIn(userId))
                .build();
    }

    @GetMapping("getStudyDurationByUserId")
    @ApiOperation(notes = "wxn",value = "根据用户Id导出学习时长")
    public WebResult<InformationAllVo> getStudyDurationByUserId(@ApiParam(name="前端查询条件 InformationInParam 查询条件实体类")
                                                         @RequestParam Integer userId){
        return WebResult.<InformationAllVo>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(informationService.getStudyDurationByUserId(userId))
                .build();
    }

    @PostMapping("add")
    @Transactional
    @ApiOperation(notes = "wxn",value = "更新课程信息接口")
    public WebResult<Integer> add(@RequestParam @ApiParam(name = "userId") Integer userId,
                                  @RequestParam @ApiParam(name = "dataId") Integer dataId,
                                  @RequestParam @ApiParam(name = "studyTime") double studyTime) {
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(informationService.addNewStudyRecord(userId,dataId,studyTime))
                .build();
    }

}
