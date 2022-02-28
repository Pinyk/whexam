package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "queryQuestion")
public class QueryQuestion {
// 编号（id）、名称、所属部门
    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "题目难度")
    private Integer difficulty;

    public QueryQuestion() {
    }

    public QueryQuestion(String context, Integer difficulty) {
        this.context = context;
        this.difficulty = difficulty;
    }
}
