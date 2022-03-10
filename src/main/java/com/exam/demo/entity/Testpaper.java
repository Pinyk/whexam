package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "testPaper")
public class Testpaper {

    @ApiModelProperty(value = "试卷主键")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "试卷对应科目")
    private int subjectId;


    @ApiModelProperty(value = "试卷名称")
    @TableField("`name`")
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
    @TableField("`time`")
    private int time;

    @ApiModelProperty(value = "试卷发布者ID")
    private int userId;

    @ApiModelProperty(value = "试卷所属部门ID")
    private int departmentId;


    @ApiModelProperty(value = "试题是否打乱")
    @TableField("`repeat`")
    private String repeat;
}
