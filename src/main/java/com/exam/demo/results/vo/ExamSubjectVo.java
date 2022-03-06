package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "ExamSubjectVo", description = "组合查询时，试题库管理判断题统一返回对象")
public class ExamSubjectVo implements Serializable{

    @ApiModelProperty(value = "编号")
    private int id;

    @ApiModelProperty(value = "题目")
    private String context;

    @ApiModelProperty(value = "科目")
    private String subject;

    @ApiModelProperty(value = "分数")
    private double score;

}
