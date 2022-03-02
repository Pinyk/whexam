package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "examSubject")
public class ExamSubject {

    @ApiModelProperty(value = "主观题主键")
    private int id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "所属科目ID")
    private int subjectId;

    @ApiModelProperty(value = "难度")
    private int difficulty;

    @ApiModelProperty(value = "分数")
    private double score;

    @ApiModelProperty(value = "图片URL")
    private String imgUrl;

}