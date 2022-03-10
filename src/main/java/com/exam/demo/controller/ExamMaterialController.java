package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.ExamMaterial;
import com.exam.demo.params.MaterialParam;
import com.exam.demo.params.submit.MaterialSubmitParam;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.ExamMaterialVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_ERROR;
import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("examMaterial")
@Api(value = "/examMaterial", tags = {"材料题操作接口"})
public class ExamMaterialController {

    @Autowired
    ExamMaterialService examMaterialService;

    @PostMapping("searchMaterial")
    @ApiOperation(value = "LBX", notes = "材料题的组合查询")
    public WebResult<PageVo<ExamMaterialVo>> searchMaterial(
            @RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return WebResult.<PageVo<ExamMaterialVo>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<PageVo<ExamMaterialVo>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examMaterialService.searchMaterial(jsonObject.getInteger("id"),
                        jsonObject.getString("context"), jsonObject.getInteger("subjectId"),
                        jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize")))
                .build();
    }

    @GetMapping("previewById")
    @ApiOperation(value = "LBX", notes = "材料题详情预览")
    public WebResult<Map<String, Object>> previewById(@ApiParam("材料题id") @RequestParam Integer id) {
        if (id == null) {
            return WebResult.<Map<String, Object>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<Map<String, Object>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examMaterialService.previewById(id))
                .build();
    }


    @PostMapping("saveExamMaterial")
    @Transactional
    @ApiOperation(notes = "LBX", value = "新增材料题")
    public WebResult<Map<String, Object>> saveExamMaterial(@RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return WebResult.<Map<String, Object>>builder()
                    .code(404)
                    .message(REQUEST_STATUS_ERROR)
                    .build();
        }
        return WebResult.<Map<String, Object>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(examMaterialService.saveExamMaterial(jsonObject))
                .build();
    }

}
