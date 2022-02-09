package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "exam")
public class Exam {

    @ApiModelProperty(value = "考试试卷主键")
    private int id;

    @ApiModelProperty(value = "试卷ID")
    private int testpaperId;

    @ApiModelProperty(value = "题目类型")
    private int type;

    @ApiModelProperty(value = "题目ID")
    private int problemId;

    @ApiModelProperty(value = "题目分数")
    private double score;
}
