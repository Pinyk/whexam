package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "score")
public class Score {

    @ApiModelProperty(value = "成绩主键")
    private int id;

    @ApiModelProperty(value = "试卷ID")
    private int testPaperId;

    @ApiModelProperty(value = "用户ID")
    private int userId;

    @ApiModelProperty(value = "得分")
    private double scoreNum;
}
