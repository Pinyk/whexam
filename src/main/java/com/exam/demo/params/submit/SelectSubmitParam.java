package com.exam.demo.params.submit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(value = "SelectSubmitParam", description = "选择题新增实体类")
public class SelectSubmitParam {

    @ApiModelProperty(name = "题目内容")
    private String context;

    @ApiModelProperty(name = "科目", notes = "前端需要传入科目Id")
    private Integer SubjectId;

    @ApiModelProperty(name = "A选项")
    private String selectionA;

    @ApiModelProperty(name = "B选项")
    private String selectionB;

    @ApiModelProperty(name = "C选项")
    private String selectionC;

    @ApiModelProperty(name = "D选项")
    private String selectionD;

    /**
     * 答案传ABCD
     */
    @ApiModelProperty(name = "答案")
    private String answer;

    @ApiModelProperty(name = "分数")
    private Double score;

    @ApiModelProperty(name = "图片")
    private MultipartFile picture;

}
