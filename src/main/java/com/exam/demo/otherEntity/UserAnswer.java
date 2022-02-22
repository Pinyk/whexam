package com.exam.demo.otherEntity;

import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.ExamSubject;
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

    @ApiModelProperty(value = "判断题目")
    private List<ExamJudge> examJudges;

    @ApiModelProperty(value = "选择题目")
    private List<ExamSelect> examSelects;

    @ApiModelProperty(value = "主观题目")
    private List<ExamSubject> examSubjects;
}
