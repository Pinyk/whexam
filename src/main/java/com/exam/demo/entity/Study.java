package com.exam.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Options;

import java.sql.Date;

/**
 * 学习类对应实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "user")
@TableName("data")
public class Study {
    @ApiModelProperty(value = "资料主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "资料名字")
    private String name;

    @ApiModelProperty(value = "资料类型id")
    private int datatypeid;

    @ApiModelProperty(value = "资料url")
    private String url;

    @ApiModelProperty(value = "资料所属学科类型id")
    private int subjectid;

    @ApiModelProperty(value = "资料所属部门id")
    private int departmentid;

    @ApiModelProperty(value = "资料学时")
    private String time;

    @ApiModelProperty(value = "备注")
    private String beizhu;
}
