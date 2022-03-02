package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "MaterialParam", description = "试题库管理模块，材料题接收前端请求参数的实体类")
public class MaterialParam implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "题目内容")
    private String context;

}
