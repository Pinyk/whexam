package com.exam.demo.controller;

import com.exam.demo.params.MaterialParam;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.ExamMaterialVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController("examMaterial")
@Api(value = "/examMaterial", tags = {"材料题操作接口"})
public class ExamMaterialController {

    @PostMapping
    @ApiOperation(value = "LBX", notes = "根据材料题id查询所有")
    public WebResult<ExamMaterialVo> findMaterialProblemById(@ApiParam(value = "试题库管理模块，材料题接收前端请求参数的实体类")
                                                             @RequestBody MaterialParam materialParam) {
        return WebResult.<ExamMaterialVo>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(null)
                .build();
    }
}
