package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("exam_fb")
@ApiModel(value = "examFb")
public class ExamFillBlank {

    @ApiModelProperty(value = "填空题主键")
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