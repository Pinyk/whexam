package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * 学习类型类对应实体
 */
@Data
@ApiModel(value = "datatype")
public class Datatype {
    @TableId(type = IdType.AUTO)
    private int id;

    private String name;

}
