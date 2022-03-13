package com.exam.demo.otherEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "selectQuestionVo")
public class UserSelectQuestionVo {

    @ApiModelProperty(value = "选择题主键")
    private Integer id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "选项内容")
    private List<String> selections;

    @ApiModelProperty(value = "答案")
    private String answer;
//
//    @ApiModelProperty(value = "所属科目")
//    private String subject;
//
//    @ApiModelProperty(value = "难度")
//    private Integer difficulty;

    @ApiModelProperty(value = "分数")
    private Double score;

    @ApiModelProperty(value = "用户答案")
    private String userAnswer;

    @ApiModelProperty(value = "用户分数")
    private double userScore;

//    @ApiModelProperty(value = "选择题目类型")
//    private Integer type;

    @ApiModelProperty(value = "图片URL")
    private String imgUrl;
//
//    @ApiModelProperty(value = "是否是材料题中的")
//    private Integer material_question;
}
