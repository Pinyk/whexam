package com.exam.demo.otherEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "selectQuestion")
public class SelectQuestion {
    @ApiModelProperty(value = "选择题主键")
    private int id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "选项内容")
    private List<String> selections;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "所属科目")
    private String subject;

    @ApiModelProperty(value = "难度")
    private int difficulty;

    @ApiModelProperty(value = "分数")
    private double score;

    @ApiModelProperty(value = "选择题目类型")
    private int type;

    @ApiModelProperty(value = "图片URL")
    private String imgUrl;
}
