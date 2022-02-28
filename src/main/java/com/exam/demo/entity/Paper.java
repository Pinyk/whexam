package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 学习类对应实体
 */
@Data
@ApiModel(value = "paper")
public class Paper {
    @TableId(type = IdType.AUTO)
    private int id;
    private String title;
    private String context;
    private String type;
    private int user_id;
    private Date date;
    private String url;


}
