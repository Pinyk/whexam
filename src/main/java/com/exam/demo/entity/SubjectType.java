package com.exam.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 科目类型对应实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "subject_type")
public class SubjectType {
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "类型名字")
    private String name;

}
