package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("exam_fb")
@ApiModel(value = "examFb")
public class ExamFillBlank {

    @ApiModelProperty(value = "填空题主键")
    @TableId(type = IdType.AUTO)
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

    @ApiModelProperty(value = "是否为材料题中的题, 0:不是 1:是")
    private Integer materialQuestion;
}