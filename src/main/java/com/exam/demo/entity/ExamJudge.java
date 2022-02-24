package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "examJudge")
public class ExamJudge {

    @ApiModelProperty(value = "判断题主键")
    private int id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "答案")
    private int answer;

    @ApiModelProperty(value = "所属科目ID")
    private int subjectId;

    @ApiModelProperty(value = "难度")
    private int defficulty;

    @ApiModelProperty(value = "分数")
    private double score;
}
