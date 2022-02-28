package com.exam.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.ibatis.annotations.Options;

import java.sql.Date;

/**
 * 学习类对应实体
 */
@Data
@ApiModel(value = "study")
@TableName("data")
public class Study {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer datatype_id;
    private String url;
    private int subject_id;
    private int department_id;
    private String time;
    private String beizhu;
}
