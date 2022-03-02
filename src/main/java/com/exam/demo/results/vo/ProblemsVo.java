package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ProblemsVo", description = "材料题模块，返回的问题集合")
public class ProblemsVo {

    @ApiModelProperty(value = "材料题中的选择题")
    private String selectProblem;

    @ApiModelProperty(value = "材料题中的填空题")
    private String fillBlankProblem;

    @ApiModelProperty(value = "材料题中的判断题")
    private String judgeProblem;

    @ApiModelProperty(value = "材料题中的问答题")
    private String subjectProblem;

}