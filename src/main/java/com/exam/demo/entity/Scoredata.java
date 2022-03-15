package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "scoreData")
public class Scoredata {

    @ApiModelProperty(value = "试题分数主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "试卷ID")
    private Integer testpaperId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "题目类型")
    private Integer type;

    @ApiModelProperty(value = "题目ID")
    private Integer problemId;

    @ApiModelProperty(value = "用户答案")
    private String answer;

    @ApiModelProperty(value = "用户得分")
    private Double score;
}
