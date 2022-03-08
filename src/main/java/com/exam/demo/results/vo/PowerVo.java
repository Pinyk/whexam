package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@ApiModel(value="PowerVo",description = "权限管理查询返回的内容")
public class PowerVo {
    @ApiModelProperty(value="姓名")
    private  String name;
    @ApiModelProperty(value="员工编号")
    private  String nums;
    @ApiModelProperty(value="部门")
    private  String department;
    @ApiModelProperty(value="权限")
    private  Integer manager=2;

    public PowerVo(String name, String nums, String department, Integer manager) {
        this.name = name;
        this.nums = nums;
        this.department = department;
        this.manager = manager;
    }

    public PowerVo() {
    }
}
