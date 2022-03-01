package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 学习类对应实体
 */
@Data
@ApiModel(value = "paper")
public class Paper {
    @ApiModelProperty(value = "论文主键")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "论文标题")
    private String title;

    @ApiModelProperty(value = "论文内容")
    private String context;

    @ApiModelProperty(value = "论文类型")
    private String type;

    @ApiModelProperty(value = "发表论文的人id")
    private int user_id;

    @ApiModelProperty(value = "上传日期")
    private Date date;

    @ApiModelProperty(value = "论文url")
    private String url;


}
