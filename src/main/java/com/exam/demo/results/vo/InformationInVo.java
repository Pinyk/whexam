package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "学习资料", description = "组合查询时，学习资料统一返回对象")
public class InformationInVo {

    @ApiModelProperty(value = "资料名")
    private String subject;

    @ApiModelProperty(value = "科目")
    private String type;

    @ApiModelProperty(value = "资料学时")
    private String time;

    @ApiModelProperty(value = "用户学时")
    private String studyTime;

    @ApiModelProperty(value = "资料学时占比")
    private String process;
}