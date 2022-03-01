package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "TestManageParam", description = "考试管理模块的前端请求参数接收实体类")
public class TestManageParam implements Serializable {

    private Integer testPaperId;

    private String testPaperName;

    private Integer departmentId;

    private String subject;

    private Integer currentPage;

    private Integer pageSize;

}
