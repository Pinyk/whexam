package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "userTestPaperScore")
public class UserTestPaperScore {

    @ApiModelProperty(value = "试卷名称")
    private String testPaperName;

    @ApiModelProperty(value = "试卷总分")
    private double totalScore;

    @ApiModelProperty(value = "试卷及格分")
    private double passScore;

    @ApiModelProperty(value = "开始考试时间")
    private Date startTime;

    @ApiModelProperty(value = "考试总时长")
    private int time;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户得分")
    private double scoreNum;
}