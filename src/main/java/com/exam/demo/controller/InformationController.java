package com.exam.demo.controller;

import com.exam.demo.entity.Information;
import com.exam.demo.params.InformationInParam;
import com.exam.demo.params.InformationParam;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.*;
import com.exam.demo.service.InformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
                .data(informationService.search(informationParam.getNums(),informationParam.getUsername(),informationParam.
                        getDepartment(),informationParam.getCurrentPage(),informationParam.getPageSize()))
                .build();
    }

    @PostMapping("searchIn")
    @ApiOperation(notes = "wxn",value = "组合查询")
    public WebResult<PageVo<InformationInVo>> searchIn(@ApiParam(name="前端查询条件 InformationInParam 查询条件实体类")
                                                       @RequestBody InformationInParam informationInParam){
        return WebResult.<PageVo<InformationInVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(informationService.searchIn(informationInParam.getUserId(),
                        informationInParam.getCurrentPage(),informationInParam.getPageSize()))
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
    @ApiOperation(notes = "wxn",value = "更新课程信息接口")
    public WebResult<Integer> insert(@RequestParam @ApiParam(name = "userId") Integer userId,
                                     @RequestParam @ApiParam(name = "dataId") Integer dataId,
                                     @RequestParam @ApiParam(name = "studyTime") String studyTime){
        Information information = new Information();
        InformationAllVo info = informationService.findTime(userId, dataId);
        InfoAddVo infoAddVo = informationService.find(dataId);
        if (info != null){
            String totalTime = info.getTotalTime();
            information.setTotalTime(totalTime);
            information.setStudyTime(studyTime);
        }else{
            information.setTotalTime(studyTime);
            information.setStudyTime(studyTime);
            information.setUserId(userId);
            information.setDepartmentId(infoAddVo.getDepartmentId());
            information.setSubjectId(infoAddVo.getSubjectId());
            information.setTypeId(infoAddVo.getTypeId());
            information.setDataId(dataId);
        }
        String time = infoAddVo.getTime();
        int time1 = Integer.parseInt(time);
        int iST = Integer.parseInt(studyTime);
        int process;
        if (iST/60 >= time1){
            process = 100;
        }else {
            process = iST / time1;
        }
        information.setProcess(process);
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(informationService.insert(information))
                .build();
    }
}
