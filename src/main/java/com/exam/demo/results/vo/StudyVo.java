package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "学习资料", description = "组合查询时，学习资料统一返回对象")
public class StudyVo implements Serializable {

    @ApiModelProperty(value = "资料主键")
    private Integer id;

    @ApiModelProperty(value = "资料名字")
    private String name;

    @ApiModelProperty(value = "所属科目")
    private String subject;

    @ApiModelProperty(value = "科目类型")
    private String subject_type;

    @ApiModelProperty(value = "资料学时")
    private String time;

    @ApiModelProperty(value = "资料类型")
    private String datatype;

    @ApiModelProperty(value = "备注")
    private String beizhu;

    @ApiModelProperty(value = "url")
    private String url;
}
