package com.exam.demo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.File;

@Data
@ApiModel(value = "SelectSubmitParam", description = "选择题新增实体类")
public class SelectSubmitParam {

    @ApiModelProperty(name = "题目内容")
    @TableId()
    private String context;

    @ApiModelProperty(name = "科目")
    private String subject;

    @ApiModelProperty(name = "A选项")
    private String a;

    @ApiModelProperty(name = "B选项")
    private String b;

    @ApiModelProperty(name = "C选项")
    private String c;

    @ApiModelProperty(name = "D选项")
    private String d;

    @ApiModelProperty(name = "答案")
    private String answer;

    @ApiModelProperty(name = "分数")
    private Double score;

    @ApiModelProperty(name = "图片")
    private File picture;

}
