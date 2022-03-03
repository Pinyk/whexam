package com.exam.demo.controller;

import com.exam.demo.entity.ExamMaterial;
import com.exam.demo.params.MaterialParam;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.ExamMaterialVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examMaterial")
@Api(value = "/examMaterial", tags = {"材料题操作接口"})
public class ExamMaterialController {

    @Autowired
    ExamMaterialService examMaterialService;

    @PostMapping("findMaterialProblemByIdAndContext")
    @ApiOperation(value = "LBX", notes = "材料题的组合查询")
    public WebResult<ExamMaterialVo> findMaterialProblemByIdAndContext(@ApiParam(value = "试题库管理模块，材料题接收前端请求参数的实体类")
                                                             @RequestBody MaterialParam materialParam) {
        return WebResult.<ExamMaterialVo>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examMaterialService.findMaterialProblemByIdAndContext(materialParam.getId(), materialParam.getContext()))
                .build();
    }

}
