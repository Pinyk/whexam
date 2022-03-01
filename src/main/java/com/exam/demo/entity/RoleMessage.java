package com.exam.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleMessage {
    @ApiModelProperty(value="姓名")
    private  String name;
    @ApiModelProperty(value="性别")
    private  int gender;
    @ApiModelProperty(value="微信名")
    private  String wxname;
    @ApiModelProperty(value="电话号码")
    private  String tele;
    @ApiModelProperty(value="电子邮箱")
    private  String email;
    @ApiModelProperty(value="部门")
    private  String department;
    @ApiModelProperty(value="职位")
    private  String position;
    @ApiModelProperty(value="地点")
    private  String address;
    @ApiModelProperty(value="学习时长")
    private  String time;
    public RoleMessage() {
    }

    public RoleMessage(String name, int gender, String wxname, String tele, String email, String department, String position, String address, String time) {
        this.name = name;
        this.gender = gender;
        this.wxname = wxname;
        this.tele = tele;
        this.email = email;
        this.department = department;
        this.position = position;
        this.address = address;
        this.time = time;
    }
}
