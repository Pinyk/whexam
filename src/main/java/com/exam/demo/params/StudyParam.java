package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "StudyParam", description = "学习资料接受前端请求参数的实体类")
public class StudyParam {

    @ApiModelProperty(value = "资料名字",dataType = "String")
    private String name;

    @ApiModelProperty(value = "备注",dataType = "String")
    private String beizhu;

    @ApiModelProperty(value = "所属科目")
    private Integer subjectId;

    @ApiModelProperty(value = "科目类型")
    private Integer typeId;

    @ApiModelProperty(value="当前页码")
    private Integer currentPage;

    @ApiModelProperty(value="页码大小")
    private Integer pageSize;
}
