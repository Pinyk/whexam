package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学习资料时长
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "information")
@TableName("information")
public class Information {

    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "用户")
    private Integer userId;

    @ApiModelProperty(value = "资料学时编号")
    private Integer dataId;

    @ApiModelProperty(value = "用户学时")
    private double studyTime;
}
