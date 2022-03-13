package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "`user` ")
public class User {

    @ApiModelProperty(value = "用户主键")
    private int id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户性别")
    private String gender;

    @ApiModelProperty(value = "微信唯一标识")
    private String openid;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "用户头像")
    private String image;

    @ApiModelProperty(value = "用户昵称")
    private String wxname;

    @ApiModelProperty(value = "用户电话")
    private String tele;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户学习时长")
    private double time;

    @ApiModelProperty(value = "用户所属部门")
    private Integer departmentId;

    @ApiModelProperty(value = "用户地址")
    private String position;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "员工编号")
    private String nums;

    @ApiModelProperty(value = "身份证号")
    private String identity;

    public User(String gender, String openid, int roleId, String image, String wxname, double time, String password) {
        this.gender = gender;
        this.openid = openid;
        this.roleId = roleId;
        this.image = image;
        this.wxname = wxname;
        this.time = time;
        this.password = password;
    }

}
