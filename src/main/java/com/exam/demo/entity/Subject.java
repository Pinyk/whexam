package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * 部门类对应实体
 */
@Data
@ApiModel(value = "subject")
public class Subject {
    private int id;
    private String name;

}
