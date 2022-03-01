package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 学习类型类对应实体
 */
@Data
@ApiModel(value = "datatype")
public class Datatype {
    @ApiModelProperty(value = "资料类型主键")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "资料类型名称")
    private String name;

}
