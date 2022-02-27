package com.exam.demo.otherEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: gaoyk
 * @Date: 2022/2/24 17:10
 */
@Data
@ApiModel(value = "userPojo")
public class UserPojo {
    @ApiModelProperty(value = "用户主键")
    private int id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户性别")
    private String gender;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "用户昵称")
    private String wxname;

    @ApiModelProperty(value = "用户电话")
    private String tele;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户学习时长")
    private double time;

    @ApiModelProperty(value = "用户所属部门")
    private String department;

    @ApiModelProperty(value = "用户职位")
    private String position;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "员工编号")
    private String nums;

    @ApiModelProperty(value = "身份证号")
    private String identity;
}
