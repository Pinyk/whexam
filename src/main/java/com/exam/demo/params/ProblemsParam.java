package com.exam.demo.params;

import com.exam.demo.results.vo.ExamFillBlankVo;
import com.exam.demo.results.vo.ExamJudgeVo;
import com.exam.demo.results.vo.ExamSelectVo;
import com.exam.demo.results.vo.ExamSubjectVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "接收前端传入材料题中的四种子题")
public class ProblemsParam {

    @ApiModelProperty(value = "材料题中的选择题")
    private List<ExamSelectVo> selectProblems;

    @ApiModelProperty(value = "材料题中的填空题")
    private List<ExamFillBlankVo> fillBlankProblems;

    @ApiModelProperty(value = "材料题中的判断题")
    private List<ExamJudgeVo> judgeProblems;

    @ApiModelProperty(value = "材料题中的问答题")
    private List<ExamSubjectVo> subjectProblems;

}
