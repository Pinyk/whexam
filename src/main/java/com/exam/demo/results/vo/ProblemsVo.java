package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

import java.io.Serializable;

@Data
@ApiModel(value = "ProblemsVo", description = "材料题模块，返回的问题集合")
public class ProblemsVo implements Serializable {

    @ApiModelProperty(value = "材料题中的选择题")
    private List<ExamSelectVo> selectProblem;

    @ApiModelProperty(value = "材料题中的填空题")
    private List<ExamFillBlankVo> fillBlankProblem;

    @ApiModelProperty(value = "材料题中的判断题")
    private List<ExamJudgeVo> judgeProblem;

    @ApiModelProperty(value = "材料题中的问答题")
    private List<ExamSubjectVo> subjectProblem;

}
