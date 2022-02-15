package com.exam.demo.rtEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "rtTestpaper")
public class RtTestpaper {

    @ApiModelProperty(value = "试卷主键")
    private int id;

    @ApiModelProperty(value = "试卷对应科目名称")
    private String subjectName;

    @ApiModelProperty(value = "试卷名称")
    private String name;

    @ApiModelProperty(value = "试卷总分")
    private double totalscore;

    @ApiModelProperty(value = "试卷及格分")
    private double passscore;

    @ApiModelProperty(value = "考试开始时间")
    private Date startTime;

    @ApiModelProperty(value = "考试结束时间")
    private Date deadTime;

    @ApiModelProperty(value = "考试总时长")
    private int time;

    @ApiModelProperty(value = "试卷发布者名称")
    private String userName;

    @ApiModelProperty(value = "试卷所属部门名称")
    private String departmentName;

    @ApiModelProperty(value = "试题是否打乱")
    private int shuffle;
}
