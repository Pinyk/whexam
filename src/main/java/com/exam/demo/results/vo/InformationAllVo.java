package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "学习资料", description = "组合查询时，学习资料统一返回对象")
public class InformationAllVo {

    @ApiModelProperty(value = "用户主键")
    private int id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户所属部门")
    private String department;

    @ApiModelProperty(value = "员工编号")
    private String nums;

    @ApiModelProperty(value = "身份证号")
    private String identity;

    @ApiModelProperty(value = "用户学习时长")
    private double totalTime;

    @ApiModelProperty(value = "详情")
    private InformationInVo informationInVo;
}
