package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "ExamFillBlankVo", description = "试题库管理填空题统一返回对象")
public class ExamFillBlankVo implements Serializable {

    @ApiModelProperty(value = "填空题主键")
    private int id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "所属科目")
    private String subject;

    @ApiModelProperty(value = "分数")
    private double score;

}
