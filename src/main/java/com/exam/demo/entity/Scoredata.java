package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "scoreData")
public class Scoredata {

    @ApiModelProperty(value = "试题分数主键")
    private int id;

    @ApiModelProperty(value = "试卷ID")
    private int testpaperId;

    @ApiModelProperty(value = "用户ID")
    private int userId;

    @ApiModelProperty(value = "题目类型")
    private int type;

    @ApiModelProperty(value = "题目ID")
    private int problemId;

    @ApiModelProperty(value = "用户答案")
    private String answer;

    @ApiModelProperty(value = "用户得分")
    private double score;
}
