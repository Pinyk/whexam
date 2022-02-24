package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: gaoyk
 * @Date: 2022/2/24 17:44
 */

@Data
@ApiModel(value = "position")
public class Position {
    @ApiModelProperty(value = "职位主键")
    private int id;

    @ApiModelProperty(value = "职位名称")
    private String name;
}
