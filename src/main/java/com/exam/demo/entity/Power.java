package com.exam.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class Power {
    @ApiModelProperty(value="姓名")
    private  String name;
    @ApiModelProperty(value="员工编号")
    private  String nums;
    @ApiModelProperty(value="部门")
    private  String department;
    @ApiModelProperty(value="职位")
    private  String rolename;
    @ApiModelProperty(value="管理员权限")
    private  String manager;

    public Power(String name, String nums, String department, String rolename, String manager) {
        this.name = name;
        this.nums = nums;
        this.department = department;
        this.rolename = rolename;
        this.manager = manager;
    }

    public Power() {
    }
}
