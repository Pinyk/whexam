package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("exam_material")
@ApiModel(value = "材料题实体类", description = "材料题表对应的实体类")
public class ExamMaterial {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "难度")
    private Integer difficulty;

    @ApiModelProperty(value = "科目Id")
    private Integer subjectId;

}
