package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "JudgeParam", description = "试题库管理模块，判断题接受前端请求参数的实体类")
public class JudgeParam implements Serializable {

    @ApiModelProperty(value = "题目编号")
    private Integer id;

    @ApiModelProperty(value = "题目名称")
    private String context;

    @ApiModelProperty(value = "科目")
    private String subject;

    @ApiModelProperty(value = "当前页码", name = "当前页码{currentPage}")
    private Integer currentPage;

    @ApiModelProperty(value = "每页容量", name = "每页容量{pageSize}")
    private Integer pageSize;

}
