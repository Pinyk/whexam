package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel(value = "ExamMaterialVo", description = "试题库管理材料题统一返回对象")
public class ExamMaterialVo implements Serializable {

    @ApiModelProperty(value = "主键id")
    Integer id;

    @ApiModelProperty(value = "题目内容")
    String context;

    @ApiModelProperty(value = "材料题包含的问题", notes = "这个属性后面需要更改类型，根据实际需求")
    ProblemsVo problems;

    @ApiModelProperty(value = "难度")
    Integer difficulty;

    @ApiModelProperty(value = "科目")
    String subject;

}
