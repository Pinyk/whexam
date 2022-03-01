package com.exam.demo.params.postparams;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "TestManageParam", description = "考试管理模块的前端请求参数接收实体类")
public class TestManageParam {

    private Integer testPaperId;

    private String testPaperName;

    private Integer departmentId;

    private String subject;

    private Integer currentPage;

    private Integer pageSize;

}
