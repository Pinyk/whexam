package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel(value = "ExamMaterialVo", description = "试题库管理材料题统一返回对象")
public class ExamMaterialVo implements Serializable{

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "科目")
    private String subject;

    @ApiModelProperty(value = "分数")
    private Double score;

}
