package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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

    @ApiModelProperty(value = "用户")
    private Integer userId;

    @ApiModelProperty(value = "用户所属部门")
    private Integer departmentId;

    @ApiModelProperty(value = "用户学习时长")
    private String totalTime;

    @ApiModelProperty(value = "资料名编号")
    private Integer subjectId;

    @ApiModelProperty(value = "科目编号")
    private Integer typeId;

    @ApiModelProperty(value = "资料学时编号")
    private Integer dataId;

    @ApiModelProperty(value = "用户学时")
    private String studyTime;

    @ApiModelProperty(value = "资料学时占比")
    private Integer process;
}
