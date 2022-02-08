package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * 学习类型类对应实体
 */
@Data
@ApiModel(value = "datatype")
public class Datatype {
    private int id;
    private String name;

}
