package com.exam.demo.params.postparams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SelectParam", description = "试题库管理模块，单选多选接受前端请求参数的实体类")
public class SelectParam implements Serializable {

    @ApiModelProperty(value = "题目编号", name = "题目编号{id}", example = "1")
    private Integer id;

    @ApiModelProperty(value = "题目名称", name = "题目名称{name}")
    private String name;

    @ApiModelProperty(value = "科目", name = "科目{subject}")
    private String subject;

    @ApiModelProperty(value = "当前页码", name = "当前页码{currentPage}")
    private Integer currentPage;

    @ApiModelProperty(value = "每页容量", name = "每页容量{pageSize}")
    private Integer pageSize;

}
