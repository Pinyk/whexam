package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 部门类对应实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "subject")
public class Subject {
    @ApiModelProperty(value = "学科主键")
    private int id;

    @ApiModelProperty(value = "学科名字")
    private String name;

}
