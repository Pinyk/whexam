package com.exam.demo.params;

import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.ExamSubject;
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

    @ApiModelProperty(value = "材料题中的单选题")
    private List<SelectionAnswer> singleSelections;

    @ApiModelProperty(value = "材料中的多选题")
    private List<SelectionAnswer> multipleSelections;

    @ApiModelProperty(value = "材料题中的填空题")
    private List<FillBlankAnswer> examFillBlank;

    @ApiModelProperty(value = "材料题中的判断题")
    private List<JudgeAnswer> examJudge;

    @ApiModelProperty(value = "材料题中的问答题")
    private List<SubjectAnswer> examSubject;

}
