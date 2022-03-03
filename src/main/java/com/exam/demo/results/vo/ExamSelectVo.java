package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "试题库管理选择题", description = "组合查询时，试题库管理选择题统一返回对象")
public class ExamSelectVo implements Serializable {

    @ApiModelProperty(value = "选择题主键")
    private Integer id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "选项内容")
    private String selection;

    @ApiModelProperty(value = "所属科目")
    private String subject;

    @ApiModelProperty(value = "分数")
    private Double score;

}
