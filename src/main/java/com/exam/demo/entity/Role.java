package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
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

