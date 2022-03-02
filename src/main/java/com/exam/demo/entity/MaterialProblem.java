package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("material_problem")
@ApiModel(value = "MaterialProblem", description = "材料题表与其它四种类型题目的中间表")
public class MaterialProblem {

    @ApiModelProperty(value = "材料表主键Id")
    private Integer materialId;

    @ApiModelProperty(value = "其它四种类型题目的主键Id")
    private Integer problemId;

    @ApiModelProperty(value = "其它四种类型题目的类型")
    private Integer problemType;

}
