package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "department")
public class Department {

    @ApiModelProperty(value = "部门主键")
    private int id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门人数")
    private int nums;

}
