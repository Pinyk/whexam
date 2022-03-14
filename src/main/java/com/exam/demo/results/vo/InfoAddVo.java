package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "学习资料", description = "组合查询时，学习资料统一返回对象")
public class InfoAddVo {

    @ApiModelProperty(value = "用户所属部门")
    private Integer departmentId;

    @ApiModelProperty(value = "资料名编号")
    private Integer subjectId;

    @ApiModelProperty(value = "科目编号")
    private Integer typeId;

    @ApiModelProperty(value = "资料时长")
    private String time;

    public InfoAddVo(Integer departmentId, Integer subjectId, Integer typeId, String time) {
        this.departmentId = departmentId;
        this.subjectId = subjectId;
        this.typeId = typeId;
        this.time = time;
    }
}