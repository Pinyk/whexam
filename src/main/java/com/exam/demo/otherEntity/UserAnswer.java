package com.exam.demo.otherEntity;

import com.exam.demo.entity.*;
import com.exam.demo.params.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "userAnswer")
public class UserAnswer {

    @ApiModelProperty(value = "试卷Id")
    private Integer testPaperId;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "单选题目")
    private List<SelectionAnswer> singleSelections;

    @ApiModelProperty(value = "多选题目")
    private List<SelectionAnswer> multipleSelections;

    @ApiModelProperty(value = "填空题目")
    private List<FillBlankAnswer> examFillBlank;

    @ApiModelProperty(value = "判断题目")
    private List<JudgeAnswer> examJudge;

    @ApiModelProperty(value = "主观题目")
    private List<SubjectAnswer> examSubject;

    @ApiModelProperty(value = "材料题目")
    private List<MaterialAnswer> examMaterial;

}
