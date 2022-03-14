package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "InformationParam", description = "学习资料接受前端请求参数的实体类")
public class InformationInParam {

    @ApiModelProperty(value = "用户")
    private int userId;

    @ApiModelProperty(value="当前页码")
    private Integer currentPage;

    @ApiModelProperty(value="页码大小")
    private Integer pageSize;
}
