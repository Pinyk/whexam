package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "FillBlankAnswer", description = "答案提交——填空题答案接收对象")
public class FillBlankAnswer {

    private int id;

    private String userAnswer;

}
