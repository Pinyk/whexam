package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: gaoyk
 * @Date: 2022/1/19 13:39
 * 角色类对应实体
 */
@Data
@ApiModel(value = "role")
public class Role {

    @ApiModelProperty(value = "角色主键")
    private int id;

    @ApiModelProperty(value = "角色名")
    private String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }
}
