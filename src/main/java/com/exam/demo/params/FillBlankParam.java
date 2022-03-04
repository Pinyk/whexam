package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "FillBlankParam", description = "试题库管理模块，填空题接受前端请求参数的实体类")
public class FillBlankParam {

    @ApiModelProperty(value = "题目编号",dataType = "int", example = "2")
    private Integer id;

    @ApiModelProperty(value = "题目名称", dataType = "String")
    private String context;

    @ApiModelProperty(value = "科目", dataType = "String")
    private String subject;

    @ApiModelProperty(value = "当前页码", name = "当前页码{currentPage}", example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "每页容量", name = "每页容量{pageSize}", example = "6")
    private Integer pageSize;
}
